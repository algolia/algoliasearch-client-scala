package com.algolia.transport

import java.io._
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.stream.Collectors

import com.algolia.errors.AlgoliaError
import com.algolia.requester.Requester
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.write
import org.slf4j.LoggerFactory

import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

case class Transport(
    applicationId: String,
    apiKey: String,
    hosts: Seq[StatefulHost],
    readTimeout: Duration,
    writeTimeout: Duration
) {
  import Transport.defaultFormats

  private val logger = LoggerFactory.getLogger(classOf[Transport])

  private val retryStrategy: RetryStrategy = RetryStrategy(hosts, readTimeout, writeTimeout)

  private val defaultHeaders = Map(
    "X-Algolia-Application-Id" -> applicationId,
    "X-Algolia-API-Key" -> apiKey,
    "User-Agent" -> s"Algolia for Scala (2.0.0-alpha); JVM (${System.getProperty("java.version")})",
    "Accept" -> "application/json",
    "Accept-Encoding" -> "gzip"
  )

  def request[T: Manifest, U: Manifest](
      method: Method,
      path: String,
      body: Option[T],
      kind: Call
  )(
      implicit
      requester: Requester,
      ex: ExecutionContext
  ): Future[Either[AlgoliaError, U]] = {

    val headers = defaultHeaders

    def requestSingleHost(host: Host): Future[(Outcome, Either[AlgoliaError, U])] = {
      Transport.logRequestParamsOnDebug(method, host, path, headers)
      requester
        .request(method, headers, host.hostname, path, body)
        .map(res => handleResponse[U](host, res))
        .recover(exception => (Outcome.Retry, Left(AlgoliaError(0, exception.getMessage))))
    }

    val empty: Future[(Outcome, Either[AlgoliaError, U])] =
      Future.successful((Outcome.Retry, Left(AlgoliaError(0, ""))))

    retryStrategy
      .getTryableHosts(kind)
      .foldLeft(empty) {
        case (previousFuture, currentHost) =>
          previousFuture.flatMap {
            case (Outcome.Retry, _) => requestSingleHost(currentHost)
            case (outcome, res)     => Future.successful((outcome, res))
          }
      }
      .map(_._2)
  }

  private def handleResponse[U: Manifest](host: Host, res: HttpResponse): (Outcome, Either[AlgoliaError, U]) = {
    val either = res.body match {
      case Failure(exception) => Left(AlgoliaError(0, exception.getMessage))
      case Success(stream) =>
        val parsed = parse(Transport.logResponseOnDebug(stream))
        res.code match {
          case code if 200 <= code && code < 300 =>
            Right(parsed.extract[U])
          case _ => Left(parsed.extract[AlgoliaError])
        }
    }
    val error = either match {
      case Left(error) => Some(error)
      case Right(_)    => None
    }
    val outcome = retryStrategy.decide(host, res.code, error)
    (outcome, either)
  }

}

object Transport {

  implicit private val defaultFormats: Formats = Serialization.formats(NoTypeHints)

  def urlEncode(s: String): String =
    URLEncoder.encode(s, StandardCharsets.UTF_8.name())

  type ResourcesCloser = () => Unit

  def nopResourcesCloser: ResourcesCloser = () => ()

  private val logger = LoggerFactory.getLogger(classOf[Transport])

  def encodeBody[T: Manifest](body: T): (java.io.Reader, ResourcesCloser) = {
    val bodyStr = write(body)
    logRequestOnDebug(bodyStr)
    val reader = new StringReader(bodyStr)
    (reader.asInstanceOf[java.io.Reader], () => { reader.close() })
  }

  private def logRequestParamsOnDebug(
      method: Method,
      host: Host,
      path: String,
      headers: Map[String, String]
  ): Unit = {
    if (logger.isDebugEnabled) {
      logger.debug(s"[${method}] ${host.hostname + path}")
      headers.foreachEntry((k, v) => {
        val obfuscated = if (k.equalsIgnoreCase("X-Algolia-API-Key")) {
          val (first, last) = v.splitAt(v.length - 5)
          "*" * first.length + last
        } else {
          v
        }
        logger.debug(s"Header: ${k} -> ${obfuscated}")
      })
    }
  }

  private def logRequestOnDebug(request: String): Unit = {
    if (logger.isDebugEnabled) {
      logger.debug(s"Request: ${request}")
    }
  }

  private def logResponseOnDebug(response: InputStream): InputStream = {
    if (logger.isDebugEnabled) {
      val reader = new InputStreamReader(response).asInstanceOf[java.io.Reader]
      val buffer = new BufferedReader(reader)
      val bodyResString = buffer.lines().collect(Collectors.joining("\n"))
      logger.debug(s"Response: ${bodyResString}")
      new ByteArrayInputStream(bodyResString.getBytes)
    } else {
      response
    }
  }
}
