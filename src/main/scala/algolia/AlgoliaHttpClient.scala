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

import java.util.concurrent.ExecutionException

import algolia.http._
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioDatagramChannel
import io.netty.resolver.dns.{DnsNameResolver, DnsNameResolverBuilder}
import org.asynchttpclient._
import org.json4s._
import org.json4s.native.JsonMethods._
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.{Failure, Success}

case class AlgoliaHttpClient(
    configuration: AlgoliaClientConfiguration = AlgoliaClientConfiguration.default) {

  val asyncClientConfig: DefaultAsyncHttpClientConfig =
    new DefaultAsyncHttpClientConfig.Builder()
      .setConnectTimeout(configuration.httpConnectTimeoutMs)
      .setReadTimeout(configuration.httpReadTimeoutMs)
      .setRequestTimeout(configuration.httpRequestTimeoutMs)
      .build

  val dnsNameResolver: DnsNameResolver = new DnsNameResolverBuilder(
    new NioEventLoopGroup(1).next()) //We only need 1 thread for DNS resolution
    .channelType(classOf[NioDatagramChannel])
    .queryTimeoutMillis(configuration.dnsTimeoutMs.toLong)
    .maxQueriesPerResolve(2)
    .build

  val logger: Logger = LoggerFactory.getLogger("algoliasearch")

  val _httpClient = new DefaultAsyncHttpClient(asyncClientConfig)

  implicit val formats: Formats = AlgoliaDsl.formats

  def close(): Unit = _httpClient.close()

  def request[T: Manifest](host: String, headers: Map[String, String], payload: HttpPayload)(
      implicit executor: ExecutionContext): Future[T] = {
    val request = payload(host, headers, dnsNameResolver)
    logger.debug(s"Trying $host")
    logger.debug(s"Query ${payload.toString(host)}")
    makeRequest(host, request, responseHandler)
  }

  def responseHandler[T: Manifest]: AsyncCompletionHandler[T] = new AsyncCompletionHandler[T] {

    override def onCompleted(response: Response): T = {
      response.getStatusCode / 100 match {
        case 2 =>
          val a = fromJson(response).extract[T]
          a
        case 4 =>
          throw `4XXAPIException`(response.getStatusCode,
                                  (fromJson(response) \ "message").extract[String])
        case _ =>
          logger.debug(s"Got HTTP code ${response.getStatusCode}, no retry")
          throw UnexpectedResponseException(response.getStatusCode)
      }
    }
  }

  def fromJson(r: Response): JValue =
    parse(StringInput(r.getResponseBody), useBigDecimalForDouble = true)

  def makeRequest[T](host: String, request: Request, handler: AsyncHandler[T])(
      implicit executor: ExecutionContext): Future[T] = {
    val javaFuture = _httpClient.executeRequest(request, handler)
    val promise = Promise[T]()
    val runnable = new java.lang.Runnable {
      def run(): Unit = {
        try {
          promise.complete(Success(javaFuture.get()))
        } catch {
          case e: ExecutionException =>
            logger.debug(s"Failing to query $host", e)
            promise.complete(Failure(e.getCause))
          case f: Throwable =>
            logger.debug(s"Failing to query $host", f)
            promise.complete(Failure(f))
        }
      }
    }
    val exec = new java.util.concurrent.Executor {
      def execute(runnable: Runnable): Unit = {
        executor.execute(runnable)
      }
    }

    javaFuture.addListener(runnable, exec)
    promise.future
  }

}

case class `4XXAPIException`(code: Int, message: String)
    extends Exception("Failure \"%s\", response status: %d".format(message, code))

case class UnexpectedResponseException(code: Int)
    extends Exception("Unexpected response status: %d".format(code))
