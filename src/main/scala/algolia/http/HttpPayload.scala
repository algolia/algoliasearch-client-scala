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

package algolia.http

import algolia.objects.RequestOptions
import io.netty.resolver.NameResolver
import org.asynchttpclient.{Request, RequestBuilder}

import java.net.InetAddress

sealed trait HttpVerb

case object GET extends HttpVerb {
  override def toString: String = "GET"
}

case object POST extends HttpVerb {
  override def toString: String = "POST"
}

case object PUT extends HttpVerb {
  override def toString: String = "PUT"
}

case object DELETE extends HttpVerb {
  override def toString: String = "DELETE"
}

private[algolia] case class HttpPayload(
    verb: HttpVerb,
    path: Seq[String],
    queryParameters: Option[Map[String, String]] = None,
    body: Option[String] = None,
    isSearch: Boolean,
    isAnalytics: Boolean = false,
    isInsights: Boolean = false,
    isPersonalization: Boolean = false,
    @deprecated("use personalization instead", "1.40.0")
    isRecommendation: Boolean = false,
    requestOptions: Option[RequestOptions]
) {

  def apply(
      host: String,
      headers: Map[String, String],
      dnsNameResolver: NameResolver[InetAddress]
  ): Request = {
    val uri = path.foldLeft(host)((url, p) => insertPath(url, p))

    var builder: RequestBuilder =
      new RequestBuilder().setMethod(verb.toString).setUrl(uri)

    headers.foreach { case (k, v) => builder = builder.addHeader(k, v) }

    // Needed to properly request the Analytics API that is behind GCP and
    // which always expects to have the Content-Length header of POST
    // requests set, even the ones whose body is empty.
    if (verb == POST && body.isEmpty) {
      builder.addHeader("Content-Length", "0")
    }

    queryParameters.foreach(
      _.foreach { case (k, v) => builder = builder.addQueryParam(k, v) }
    )

    requestOptions.foreach { r =>
      r.generateExtraHeaders().foreach {
        case (k, v) => builder = builder.addHeader(k, v)
      }
      r.generateExtraQueryParameters().foreach {
        case (k, v) => builder = builder.addQueryParam(k, v)
      }
    }

    body.foreach(b => builder = builder.setBody(b))

    builder.setNameResolver(dnsNameResolver).build()
  }

  def toString(host: String): String = {
    val _path = path.foldLeft("")(_ + "/" + _)
    val _query = queryParameters.fold("")(_.foldLeft("") {
      case (acc, (k, v)) => s"$acc&$k=$v"
    })
    val _body = body.map(b => s", '$b'").getOrElse("")

    s"$verb $host${_path}${_query}${_body}"
  }

  private def insertPath(url: String, path: String) =
    url.indexOf('?') match {
      case -1 => appendPath(url, path)
      case i  => appendPath(url.substring(0, i), path) + url.substring(i)
    }

  private def appendPath(url: String, path: String) =
    if (url.endsWith("/")) {
      url + path
    } else {
      url + "/" + path
    }

}
