package com.algolia.requester

import com.algolia.errors.AlgoliaError
import com.algolia.transport._
import org.apache.http.client.methods._
import org.apache.http.client.utils.URIBuilder
import org.apache.http.impl.nio.client.{CloseableHttpAsyncClient, HttpAsyncClientBuilder}
import org.json4s._
import org.json4s.native.JsonMethods.parse

import scala.concurrent.{ExecutionContext, Future}
import scala.io.Source
import scala.util.{Failure, Success, Try}

class ApacheAsyncRequester(
    val builder: HttpAsyncClientBuilder,
)(
    implicit val ex: ExecutionContext,
) extends Requester[Future] {

  val client: CloseableHttpAsyncClient = builder.build()

  implicit val formats: DefaultFormats.type = DefaultFormats

  override def request[T: Manifest, U: Manifest](
      method: Method,
      host: String,
      path: String,
      body: T,
  ): Future[Either[AlgoliaError, U]] = {
    val uri = new URIBuilder()
      .setScheme("https")
      .setHost(host)
      .setPath(path)
      .build()
    val req = method match {
      case Get    => new HttpGet(uri)
      case Post   => new HttpPost(uri)
      case Put    => new HttpPut(uri)
      case Delete => new HttpDelete(uri)
    }

    Future(client.execute(req, null).get())
      .map(res => {
        val code = res.getStatusLine.getStatusCode
        val body = Try(res.getEntity.getContent)
        body match {
          case Failure(exception) => Left(AlgoliaError(0, exception.getMessage))
          case Success(stream) =>
            code match {
              case x if 200 <= x && x < 300 => Right(parse(stream).extract[U])
              case _ =>
                val raw = Source.fromInputStream(stream).mkString
                println(raw)
                Left(parse(raw).extract[AlgoliaError])
            }
        }
      })
  }
}

object ApacheAsyncRequester {
  def apply()(implicit ec: ExecutionContext): ApacheAsyncRequester =
    new ApacheAsyncRequester(HttpAsyncClientBuilder.create())

  def apply(builder: HttpAsyncClientBuilder)(implicit ec: ExecutionContext): ApacheAsyncRequester =
    new ApacheAsyncRequester(builder)
}
