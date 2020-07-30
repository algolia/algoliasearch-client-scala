package com.algolia.requester

import java.net.URI
import java.nio.charset.Charset

import com.algolia.transport.Transport.ResourcesCloser
import com.algolia.transport._
import org.apache.commons.io.input.ReaderInputStream
import org.apache.http.client.methods.{HttpUriRequest, RequestBuilder}
import org.apache.http.client.utils.URIBuilder
import org.apache.http.entity.{ContentType, InputStreamEntity}
import org.apache.http.impl.nio.client.{CloseableHttpAsyncClient, HttpAsyncClientBuilder}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

class ApacheRequester(builder: HttpAsyncClientBuilder) extends Requester {
  val client: CloseableHttpAsyncClient = {
    val c: CloseableHttpAsyncClient = builder.build()
    c.start()
    c
  }

  override def request[T: Manifest](
      method: Method,
      headers: Map[String, String],
      host: String,
      path: String,
      body: Option[T]
  )(implicit ex: ExecutionContext): Future[HttpResponse] = {
    val (req, closer) = ApacheRequester.buildRequest(method, headers, host, path, body)
    Future(client.execute(req, null).get())
      .map(res => {
        closer()
        val code = res.getStatusLine.getStatusCode
        Try(res.getEntity.getContent) match {
          case Failure(exception) => HttpResponse(0, Failure(exception))
          case Success(bodyRes)   => HttpResponse(code, Success(bodyRes))
        }
      })
//    future.onComplete(_ => closer())
//    future
  }
}

object ApacheRequester {
  def apply(): ApacheRequester =
    new ApacheRequester(HttpAsyncClientBuilder.create().useSystemProperties())

  def apply(builder: HttpAsyncClientBuilder): ApacheRequester =
    new ApacheRequester(builder)

  private def buildRequest[T: Manifest](
      method: Method,
      headers: Map[String, String],
      host: String,
      path: String,
      body: Option[T]
  ): (HttpUriRequest, ResourcesCloser) = {
    val req = RequestBuilder.create(method.toString)
    req.setUri(buildUri(host, path))
    headers.foreach({ case (k, v) => req.setHeader(k, v) })

    val closer = body match {
      case None => Transport.nopResourcesCloser
      case Some(body) =>
        val (stream, closer) = buildEntity(body)
        req.setEntity(stream)
        closer
    }

    (req.build(), closer)
  }

  private def buildUri(host: String, path: String): URI =
    new URIBuilder()
      .setScheme("https")
      .setHost(host)
      .setPath(path)
      .build()

  private def buildEntity[T: Manifest](body: T): (InputStreamEntity, ResourcesCloser) = {
    val (reader, innerCloser) = Transport.encodeBody(body)
    val stream = new ReaderInputStream(reader, Charset.defaultCharset())
    val entity = new InputStreamEntity(stream, ContentType.APPLICATION_JSON)

    def closer(): Unit = {
      stream.close()
      innerCloser()
    }

    (entity, closer)
  }
}
