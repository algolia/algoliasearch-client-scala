/*
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
import java.util.concurrent.TimeoutException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

import algolia.http.HttpPayload
import algolia.objects.Query

import scala.concurrent.{ExecutionContext, Future}

class AlgoliaClient(applicationId: String, apiKey: String) {

  if (applicationId == null || applicationId.isEmpty) {
    throw new AlgoliaClientException(s"'applicationId' is probably too short: '$applicationId'")
  }

  if (apiKey == null || apiKey.isEmpty) {
    throw new AlgoliaClientException(s"'apiKey' is probably too short: '$apiKey'")
  }

  final private val ALGOLIANET_COM_HOST = "algolianet.com"
  final private val ALGOLIANET_HOST = "algolia.net"

  lazy val indexingHosts: Seq[String] =
    s"https://$applicationId.$ALGOLIANET_HOST" +:
    random.shuffle(Seq(
      s"https://$applicationId-1.$ALGOLIANET_COM_HOST",
      s"https://$applicationId-2.$ALGOLIANET_COM_HOST",
      s"https://$applicationId-3.$ALGOLIANET_COM_HOST"
    ))

  lazy val queryHosts: Seq[String] =
    s"https://$applicationId-dsn.$ALGOLIANET_HOST" +:
    random.shuffle(Seq(
      s"https://$applicationId-1.$ALGOLIANET_COM_HOST",
      s"https://$applicationId-2.$ALGOLIANET_COM_HOST",
      s"https://$applicationId-3.$ALGOLIANET_COM_HOST"
    ))

  val httpClient: DispatchHttpClient = DispatchHttpClient
  val random: AlgoliaRandom = AlgoliaRandom
  val userAgent = s"Algolia for Scala ${BuildInfo.scalaVersion} API ${BuildInfo.version}"

  val headers: Map[String, String] = Map(
    "Accept-Encoding" -> "gzip",
    "X-Algolia-Application-Id" -> applicationId,
    "X-Algolia-API-Key" -> apiKey,
    "User-Agent" -> userAgent,
    "Content-Type" -> "application/json; charset=UTF-8",
    "Accept" -> "application/json"
  )

  private val HMAC_SHA256 = "HmacSHA256"

  def execute[QUERY, RESULT](query: QUERY)(implicit executable: Executable[QUERY, RESULT], executor: ExecutionContext): Future[RESULT] = executable(this, query)

  def generateSecuredApiKey(privateApiKey: String, query: Query, userToken: Option[String] = None): String = {
    val queryStr = query.copy(userToken = userToken).toParam
    val key = hmac(privateApiKey, queryStr)

    new String(Base64.getEncoder.encode(s"$key$queryStr".getBytes(Charset.forName("UTF8"))))
  }

  private def hmac(key: String, msg: String): String = {
    val algorithm = Mac.getInstance(HMAC_SHA256)
    algorithm.init(new SecretKeySpec(key.getBytes(), HMAC_SHA256))

    algorithm
      .doFinal(msg.getBytes())
      .map("%02x".format(_))
      .mkString
  }

  private[algolia] def request[T: Manifest](payload: HttpPayload)(implicit executor: ExecutionContext): Future[T] = {
    val hosts = if (payload.isSearch) queryHosts else indexingHosts

    hosts.foldLeft(Future.failed[T](new TimeoutException())) { (future, host) =>
      future.recoverWith {
        case e: APIClientException => Future.failed(e) //No retry if 4XX
        case _ => httpClient request[T](host, headers, payload)
      }
    }
  }
}

case class AlgoliaClientException(message: String) extends Exception(message)
