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

import algolia.http._
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioDatagramChannel
import io.netty.resolver.dns.DnsNameResolverBuilder
import org.asynchttpclient._
import org.json4s._
import org.json4s.native.JsonMethods._

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.Try

object default {
  val httpReadTimeout = 2000

  //httpSocketTimeout in HttpClient
  val httpConnectTimeout = 2000

  val httpRequestTimeout = 2000

  val dnsTimeout = httpConnectTimeout / 10
}

case class AlgoliaHttpClient(
    httpReadTimeout: Int = default.httpReadTimeout,
    httpConnectTimeout: Int = default.httpConnectTimeout,
    httpRequestTimeout: Int = default.httpRequestTimeout,
    dnsTimeout: Int = default.dnsTimeout) {

  val asyncClientConfig = new DefaultAsyncHttpClientConfig.Builder()
    .setConnectTimeout(httpConnectTimeout)
    .setReadTimeout(httpReadTimeout)
    .setRequestTimeout(httpRequestTimeout)
    .build

  val dnsNameResolver = new DnsNameResolverBuilder(
    new NioEventLoopGroup(1).next()) //We only need 1 thread for DNS resolution
    .channelType(classOf[NioDatagramChannel])
    .queryTimeoutMillis(dnsTimeout)
    .build

  val _httpClient = new DefaultAsyncHttpClient(asyncClientConfig)

  implicit val formats: Formats = AlgoliaDsl.formats

  def request[T: Manifest](
      host: String,
      headers: Map[String, String],
      payload: HttpPayload)(implicit executor: ExecutionContext): Future[T] = {
    val request = payload(host, headers, dnsNameResolver)
    makeRequest(request, responseHandler)
  }

  def responseHandler[T: Manifest] = new AsyncCompletionHandler[T] {
    override def onCompleted(response: Response) =
      response.getStatusCode / 100 match {
        case 2 => toJson(response).extract[T]
        case 4 =>
          throw APIClientException(
            response.getStatusCode,
            (toJson(response) \ "message").extract[String])
        case _ => throw UnexpectedResponse(response.getStatusCode)
      }
  }

  def toJson(r: Response) =
    parse(StringInput(r.getResponseBody), useBigDecimalForDouble = true)

  def makeRequest[T](request: Request, handler: AsyncHandler[T])(
      implicit executor: ExecutionContext): Future[T] = {
    val javaFuture = _httpClient.executeRequest(request, handler)
    val promise = Promise[T]()
    val runnable = new java.lang.Runnable {
      def run() {
        promise.complete(Try(javaFuture.get()))
      }
    }
    val exec = new java.util.concurrent.Executor {
      def execute(runnable: Runnable) {
        executor.execute(runnable)
      }
    }

    javaFuture.addListener(runnable, exec)
    promise.future
  }

}

case class APIClientException(code: Int, message: String)
    extends Exception(
      "Failure \"%s\", response status: %d".format(message, code))

case class UnexpectedResponse(code: Int)
    extends Exception("Unexpected response status: %d".format(code))
