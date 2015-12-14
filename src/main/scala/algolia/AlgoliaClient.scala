/*
 * Copyright (c) 2015 Algolia
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

import java.util.concurrent.TimeoutException

import algolia.AlgoliaDsl._
import algolia.definitions.SearchDefinition
import algolia.http.HttpPayload
import algolia.responses.{Indexes, Search, Task}

import scala.concurrent.{ExecutionContext, Future}

class AlgoliaClient(applicationId: String, apiKey: String) {

  final private val ALGOLIANET_COM_HOST = "algolianet.com"
  final private val ALGOLIANET_HOST = "algolia.net"

  val httpClient: DispatchHttpClient = DispatchHttpClient
  val random: AlgoliaRandom = AlgoliaRandom

  lazy val indexingHosts: Seq[String] = random.shuffle(Seq(
    s"https://$applicationId-1.$ALGOLIANET_COM_HOST",
    s"https://$applicationId-2.$ALGOLIANET_COM_HOST",
    s"https://$applicationId-3.$ALGOLIANET_COM_HOST"
  )) :+ s"https://$applicationId.$ALGOLIANET_HOST"

  lazy val queryHosts: Seq[String] = random.shuffle(Seq(
    s"https://$applicationId-1.$ALGOLIANET_COM_HOST",
    s"https://$applicationId-2.$ALGOLIANET_COM_HOST",
    s"https://$applicationId-3.$ALGOLIANET_COM_HOST"
  )) :+ s"https://$applicationId-dsn.$ALGOLIANET_HOST"

  val userAgent = s"Algolia Scala ${util.Properties.versionNumberString}"

  val headers: Map[String, String] = Map(
    "Accept-Encoding" -> "gzip",
    "X-Algolia-Application-Id" -> applicationId,
    "X-Algolia-API-Key" -> apiKey,
    "User-Agent" -> userAgent,
    "Content-type" -> "application/json",
    "Accept" -> "application/json"
  )

  def search(query: SearchDefinition)(implicit executor: ExecutionContext): Future[Search] = request[Search](query.build())

  def indexes()(implicit executor: ExecutionContext): Future[Indexes] = execute {
    AlgoliaDsl.indexes
  }

  def clear(ind: String)(implicit executor: ExecutionContext): Future[Task] = execute {
    AlgoliaDsl.clear index ind
  }

  def delete(ind: String)(implicit executor: ExecutionContext): Future[Task] = execute {
    AlgoliaDsl.delete index ind
  }

  def execute[QUERY, RESULT](query: QUERY)(implicit executable: Executable[QUERY, RESULT], executor: ExecutionContext): Future[RESULT] = executable(this, query)

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
