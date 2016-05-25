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

import algolia.http._
import org.asynchttpclient.Response
import org.json4s._

import scala.concurrent.{ExecutionContext, Future}

object default {
  val httpReadTimeout = 2000

  //httpSocketTimeout in HttpClient
  val httpConnectTimeout = 2000

  val httpRequestTimeout = 2000
}

object DispatchHttpClient extends DispatchHttpClient(
  httpReadTimeout = default.httpReadTimeout,
  httpConnectTimeout = default.httpConnectTimeout,
  httpRequestTimeout = default.httpRequestTimeout
)

case class DispatchHttpClient(httpReadTimeout: Int = default.httpReadTimeout,
                              httpConnectTimeout: Int = default.httpConnectTimeout,
                              httpRequestTimeout: Int = default.httpRequestTimeout) {

  lazy val http = Http.configure { builder =>
    builder
      .setConnectTimeout(httpConnectTimeout)
      .setReadTimeout(httpReadTimeout)
      .setRequestTimeout(httpRequestTimeout)
  }
  implicit val formats: Formats = AlgoliaDsl.formats

  def request[T: Manifest](host: String, headers: Map[String, String], payload: HttpPayload)(implicit executor: ExecutionContext): Future[T] = {
    val path = payload.path.foldLeft(url(host).secure) {
      (url, p) => url / p
    }

    var request = (payload.verb match {
      case GET => path.GET
      case POST => path.POST
      case PUT => path.PUT
      case DELETE => path.DELETE
    }) <:< headers

    request = payload.queryParameters.map(request <<? _).getOrElse(request)
    request = payload.body.map(request << _).getOrElse(request)

    val start = System.currentTimeMillis()
    val responseManager: Response => T = { response =>
      println(s"querying $host took ${System.currentTimeMillis() - start}ms")
      response.getStatusCode / 100 match {
        case 2 => Json(response).extract[T]
        case 4 => throw APIClientException(response.getStatusCode, (Json(response) \ "message").extract[String])
        case _ => throw UnexpectedResponse(response.getStatusCode)
      }
    }

    http(request > responseManager)
  }

}

case class APIClientException(code: Int, message: String) extends Exception("Failure \"%s\", response status: %d".format(message, code))

case class UnexpectedResponse(code: Int) extends Exception("Unexpected response status: %d".format(code))
