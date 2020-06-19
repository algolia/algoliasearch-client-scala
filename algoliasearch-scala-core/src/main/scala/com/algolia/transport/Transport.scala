package com.algolia.transport

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

import com.algolia.errors.AlgoliaError
import com.algolia.requester.{Functor, Requester}
import com.algolia.transport.Outcome.{Failure, Retry, Success}

import scala.concurrent.duration.Duration

case class Transport(
    applicationId: String,
    apiKey: String,
    hosts: Seq[StatefulHost],
    readTimeout: Duration,
    writeTimeout: Duration
) {
  private val retryStrategy: RetryStrategy = RetryStrategy(hosts, readTimeout, writeTimeout)

  def request[T: Manifest, U: Manifest, F[_]](
      method: Method,
      path: String,
      body: T,
      kind: Call
  )(
      implicit
      requester: Requester[F],
      functor: Functor[F]
  ): F[Either[AlgoliaError, U]] = {

    def request(host: Host): F[Either[AlgoliaError, HttpResponse]] =
      requester.request(method, host.hostname, path, body)

    retryStrategy
      .getTryableHosts(kind)
      .foldLeft((None: Option[F[Either[AlgoliaError, U]]], Retry: Outcome)) { (prev, h) =>
        {
          prev._2 match {
            case Success => (prev._1, Success)
            case Failure => (prev._1, Failure)
            case Retry => {
              val res = request(h)
              functor.map(res) {
                case Left(exception) => 0
                case Right(response) => response.
              }
              retryStrategy.decide(h, )

              (Some(res))
            }
          }
        }
      }
      ._1 match {
      case None    => functor(Left(AlgoliaError.NoHostToTry))
      case Some(f) => f
    }

  }
}

object Transport {
  def urlEncode(s: String): String =
    URLEncoder.encode(s, StandardCharsets.UTF_8.name())
}
