package com.algolia.requester

import com.algolia.errors.AlgoliaError
import com.algolia.requester.Requester.Identity
import com.algolia.transport._
import org.apache.http.client.methods._
import org.apache.http.client.utils.URIBuilder
import org.apache.http.impl.client.{CloseableHttpClient, HttpClientBuilder}
import org.json4s._
import org.json4s.native.JsonMethods._

import scala.io.Source
import scala.util.{Failure, Success, Try}

class ApacheSyncRequester(
    val builder: HttpClientBuilder,
) extends Requester[Identity] {

  val client: CloseableHttpClient = builder.build()

  implicit val formats: DefaultFormats.type = DefaultFormats

  override def request[T: Manifest, U: Manifest](
      method: Method,
      host: String,
      path: String,
      body: T,
  ): Identity[Either[AlgoliaError, U]] = {
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

    val either = Try(client.execute(req)) match {
      case Failure(exception) => Left(AlgoliaError(0, exception.getMessage))
      case Success(value) => {
        val code = value.getStatusLine.getStatusCode
        val bodyRes = Try(value.getEntity.getContent)
        bodyRes match {
          case Failure(exception) => Left(AlgoliaError(0, exception.getMessage))
          case Success(stream) => {
            code match {
              case x if 200 <= x && x < 300 => Right(parse(stream).extract[U])
              case _ => {
                val raw = Source.fromInputStream(stream).mkString
                println(raw)
                Left(parse(raw).extract[AlgoliaError])
              }
            }
          }
        }
      }
    }
    either
  }
}

object ApacheSyncRequester {
  def apply(): ApacheSyncRequester =
    new ApacheSyncRequester(HttpClientBuilder.create())

  def apply(builder: HttpClientBuilder): ApacheSyncRequester =
    new ApacheSyncRequester(builder)
}
