/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Algolia
 * http://www.algolia.com/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package algolia

import java.nio.charset.Charset
import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

import algolia.http.HttpPayload
import algolia.objects.Query

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

/**
  * The AlgoliaClient to query Algolia
  *
  * @param applicationId The APP_ID of your Algolia account
  * @param apiKey The API KEY of your Algolia account
  * @param customHeader Custom headers to add to every requests
  */
class AlgoliaClient(applicationId: String,
                    apiKey: String,
                    customHeader: Map[String, String] = Map.empty,
                    configuration: AlgoliaClientConfiguration = AlgoliaClientConfiguration.default,
                    private[algolia] val utils: AlgoliaUtils = AlgoliaUtils) {

  if (applicationId == null || applicationId.isEmpty) {
    throw new AlgoliaClientException(s"'applicationId' is probably too short: '$applicationId'")
  }

  if (apiKey == null || apiKey.isEmpty) {
    throw new AlgoliaClientException(s"'apiKey' is probably too short: '$apiKey'")
  }

  final private val ALGOLIANET_COM_HOST = "algolianet.com"
  final private val ALGOLIANET_HOST = "algolia.net"

  val httpClient: AlgoliaHttpClient = AlgoliaHttpClient(configuration)

  val indexingHosts: Seq[String] =
    s"https://$applicationId.$ALGOLIANET_HOST" +:
      utils.shuffle(
      Seq(
        s"https://$applicationId-1.$ALGOLIANET_COM_HOST",
        s"https://$applicationId-2.$ALGOLIANET_COM_HOST",
        s"https://$applicationId-3.$ALGOLIANET_COM_HOST"
      ))

  val queryHosts: Seq[String] =
    s"https://$applicationId-dsn.$ALGOLIANET_HOST" +:
      utils.shuffle(
      Seq(
        s"https://$applicationId-1.$ALGOLIANET_COM_HOST",
        s"https://$applicationId-2.$ALGOLIANET_COM_HOST",
        s"https://$applicationId-3.$ALGOLIANET_COM_HOST"
      ))

  val userAgent =
    s"Algolia for Scala (${BuildInfo.version}); JVM (${System.getProperty("java.version")}); Scala (${BuildInfo.scalaVersion})"

  val headers: Map[String, String] = customHeader ++ Map(
    "Accept-Encoding" -> "gzip",
    "X-Algolia-Application-Id" -> applicationId,
    "X-Algolia-API-Key" -> apiKey,
    "User-Agent" -> userAgent,
    "Content-Type" -> "application/json; charset=UTF-8",
    "Accept" -> "application/json"
  )

  private val HMAC_SHA256 = "HmacSHA256"
  private[algolia] lazy val hostsStatuses =
    HostsStatuses(configuration, utils, queryHosts, indexingHosts)

  def execute[QUERY, RESULT](query: QUERY)(implicit executable: Executable[QUERY, RESULT],
                                           executor: ExecutionContext): Future[RESULT] =
    executable(this, query)

  def generateSecuredApiKey(privateApiKey: String,
                            query: Query,
                            userToken: Option[String] = None): String = {
    val queryStr = query.copy(userToken = userToken).toParam
    val key = hmac(privateApiKey, queryStr)

    new String(Base64.getEncoder.encode(s"$key$queryStr".getBytes(Charset.forName("UTF8"))))
  }

  private def hmac(key: String, msg: String): String = {
    val algorithm = Mac.getInstance(HMAC_SHA256)
    algorithm.init(new SecretKeySpec(key.getBytes(), HMAC_SHA256))

    algorithm.doFinal(msg.getBytes()).map("%02x".format(_)).mkString
  }

  private val failedStart: Future[Nothing] = Future.failed(StartException())
  private[algolia] def request[T: Manifest](payload: HttpPayload)(
      implicit executor: ExecutionContext): Future[T] = {
    val hosts = if (payload.isSearch) {
      hostsStatuses.queryHostsThatAreUp()
    } else {
      hostsStatuses.indexingHostsThatAreUp()
    }

    def makeRequest(host: String): Future[T] =
      httpClient.request[T](host, headers, payload).andThen {
        case Success(_) =>
          hostsStatuses.markHostAsUp(host)
        case Failure(_: `4XXAPIException`) =>
          hostsStatuses.markHostAsUp(host)
        case Failure(_) =>
          hostsStatuses.markHostAsDown(host)
      }

    val result = hosts.foldLeft[Future[T]](failedStart) { (future, host) =>
      future.recoverWith {
        case f: `4XXAPIException` =>
          Future.failed(f) //No retry if 4XX
        case _ =>
          makeRequest(host)
      }
    }

    result.recoverWith {
      case e: `4XXAPIException` =>
        Future.failed(new AlgoliaClientException(e.getMessage, e))
      case e =>
        Future.failed(new AlgoliaClientException("Failed on last retry", e))
    }
  }
}

private[algolia] case class StartException() extends Exception

class AlgoliaClientException(message: String, exception: Throwable)
    extends Exception(message, exception) {

  def this(message: String) = {
    this(message, null)
  }

  def this(exception: Throwable) = {
    this(null, exception)
  }
}
