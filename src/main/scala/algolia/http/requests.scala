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

package algolia.http

import java.net.URI
import java.nio.charset.Charset
import java.util

import org.asynchttpclient.{Request, RequestBuilder}
import org.asynchttpclient.request.body.generator.BodyGenerator

object url extends (String => Req) {
  def apply(url: String) = {
    Req(_.setUrl(RawUri(url).toString))
  }
}

case class Req(
                run: RequestBuilder => RequestBuilder,
                props: Req.Properties = Req.Properties()
              )
  extends MethodVerbs
    with UrlVerbs
    with ParamVerbs
    with HeaderVerbs
    with RequestBuilderVerbs {
  def subject = this

  def underlying(next: RequestBuilder => RequestBuilder) =
    Req(run andThen next, props)

  def underlying(nextReq: RequestBuilder => RequestBuilder,
                 nextProps: Req.Properties => Req.Properties) =
    Req(run andThen nextReq, nextProps(props))

  def toRequest: Request = toRequestBuilder.build

  def toRequestBuilder = run(new RequestBuilder)
}

object Req {

  trait BodyType

  final case class Properties(bodyType: BodyType = NoBody)

  case object NoBody extends BodyType

  case object StringBody extends BodyType

  case object ByteArrayBody extends BodyType

  case object EntityWriterBody extends BodyType

  case object FileBody extends BodyType

}

trait HeaderVerbs extends RequestVerbs {
  def <:<(hs: Traversable[(String, String)]) =
    (subject /: hs) {
      case (s, (key, value)) =>
        s.addHeader(key, value)
    }
}

trait MethodVerbs extends RequestVerbs {
  def GET = subject.setMethod("GET")

  def POST = subject.setMethod("POST")

  def PUT = subject.setMethod("PUT")

  def DELETE = subject.setMethod("DELETE")
}

trait ParamVerbs extends RequestVerbs {

  /** Adds `params` to the request body. Sets request method
    * to POST if it is currently GET. */
  def <<(params: Traversable[(String, String)]) = {
    (defaultMethod("POST") /: params) {
      case (s, (key, value)) =>
        s.addParameter(key, value)
    }
  }

  /** Set request body to a given string,
    * - set method to POST if currently GET,
    * - set HTTP Content-Type to "text/plain; charset=UTF-8" if unspecified. */
  def <<(body: String) = {
    defaultMethod("POST").setBody(body)
  }

  private def defaultMethod(method: String): Req = {
    if (subject.toRequest.getMethod.toUpperCase == "GET")
      subject.setMethod(method)
    else subject
  }

  /** Adds `params` as query parameters */
  def <<?(params: Traversable[(String, String)]) =
    (subject /: params) {
      case (s, (key, value)) =>
        s.addQueryParameter(key, value)
    }
}

case class RawUri(
                   scheme: Option[String],
                   userInfo: Option[String],
                   host: Option[String],
                   port: Option[Int],
                   path: Option[String],
                   query: Option[String],
                   fragment: Option[String]
                 ) {
  override def toString = toUri.toASCIIString

  def toUri =
    new URI(
      (scheme.map {
        _ + ":"
      } ::
        Some("//") ::
        userInfo.map {
          _ + "@"
        } ::
        host ::
        port.map {
          ":" + _
        } ::
        path ::
        query.map {
          "?" + _
        } ::
        fragment.map {
          "#" + _
        } ::
        Nil).flatten.mkString)
}

object RawUri {
  def apply(str: String): RawUri = RawUri(new URI(str))

  def apply(subject: URI): RawUri = RawUri(
    scheme = Option(subject.getScheme),
    userInfo = Option(subject.getRawUserInfo),
    host = Option(subject.getHost),
    port = Some(subject.getPort).filter(_ != -1),
    path = Option(subject.getRawPath),
    query = Option(subject.getRawQuery),
    fragment = Option(subject.getRawFragment)
  )
}

trait RequestVerbs {
  def subject: Req
}

trait UrlVerbs extends RequestVerbs {
  def /(segment: AnyVal): Req = segment match {
    case unit: Unit => subject
    case other => this / other.toString
  }

  def /?(segmentOpt: Option[String]): Req =
    segmentOpt.map(this / _) getOrElse subject

  def /(segment: String) = {
    val uri = RawUri(url)
    val encodedSegment = UriEncode.path(segment)
    val rawPath = uri.path.orElse(Some("/")).map {
      case u if u.endsWith("/") => u + encodedSegment
      case u => u + "/" + encodedSegment
    }
    subject.setUrl(uri.copy(path = rawPath, query = None).toString)
  }

  def url = subject.toRequest.getUrl

  def secure = {
    subject.setUrl(RawUri(url).copy(scheme = Some("https")).toString)
  }
}

object UriEncode {
  val segmentValid = (';' +: pchar).toSet
  private val validMarkers = (0 to segmentValid.max)
    .map({ i: Int =>
      segmentValid(i.toChar)
    })
    .toArray

  def pchar = unreserved ++ (
    ':' :: '@' :: '&' :: '=' :: '+' :: '$' :: ',' :: Nil
    )

  def unreserved = alphanum ++ mark

  def alphanum = alpha ++ digit

  // uri character sets
  def alpha = lowalpha ++ upalpha

  def lowalpha = 'a' to 'z'

  def upalpha = 'A' to 'Z'

  def digit = '0' to '9'

  def mark =
    '-' :: '_' :: '.' :: '!' :: '~' :: '*' ::
      '\'' :: '(' :: ')' :: Nil

  def path(pathSegment: String, encoding: String = "UTF-8") = {
    if (pathSegment.forall(isValidChar)) {
      pathSegment
    } else {
      val sb = new StringBuilder(pathSegment.length << 1)

      pathSegment foreach { ch =>
        if (isValidChar(ch)) {
          sb.append(ch)
        } else {
          ch.toString.getBytes(encoding) foreach { b =>
            val hi = (b >>> 4) & 0xf
            val lo = b & 0xf
            sb.append('%')
              .append((if (hi > 9) hi + '7' else hi + '0').toChar)
              .append((if (lo > 9) lo + '7' else lo + '0').toChar)
          }
        }
      }

      sb.toString
    }
  }

  private def isValidChar(ch: Char) =
    (ch < validMarkers.length) && validMarkers(ch)
}

trait RequestBuilderVerbs extends RequestVerbs {

  import scala.collection.JavaConverters._

  def addHeader(name: String, value: String) =
    subject.underlying {
      _.addHeader(name, value)
    }

  def addParameter(key: String, value: String) =
    subject.underlying {
      _.addFormParam(key, value)
    }

  def addQueryParameter(name: String, value: String) =
    subject.underlying {
      _.addQueryParam(name, value)
    }

  def setQueryParameters(params: Map[String, Seq[String]]) =
    subject.underlying {
      _.setQueryParams(params.mapValues(_.toList.asJava).asJava)
    }

  def setBody(data: Array[Byte]) =
    subject.underlying(
      rb => rb.setBody(data), p => p.copy(bodyType = Req.ByteArrayBody))

  def setBody(dataWriter: BodyGenerator, length: Long) =
    subject.underlying(rb => rb.setBody(dataWriter),
      p => p.copy(bodyType = Req.EntityWriterBody))

  def setBody(dataWriter: BodyGenerator) =
    subject.underlying(rb => rb.setBody(dataWriter),
      p => p.copy(bodyType = Req.EntityWriterBody))

  def setBody(data: String) =
    subject.underlying(
      rb => rb.setBody(data), p => p.copy(bodyType = Req.StringBody))

  def setBody(file: java.io.File) =
    subject.underlying(
      rb => rb.setBody(file), p => p.copy(bodyType = Req.FileBody))

  def setBodyEncoding(charset: Charset) =
    subject.underlying {
      _.setCharset(charset)
    }

  def setContentType(mediaType: String, charset: Charset) =
    subject.underlying {
      _.setHeader("Content-Type", mediaType + "; charset=" + charset)
        .setCharset(charset)
    }

  def setHeader(name: String, value: String) =
    subject.underlying {
      _.setHeader(name, value)
    }

  def setHeaders(headers: Map[String, Seq[String]]) =
    subject.underlying {
      _.setHeaders(
        headers.mapValues {
          _.asJava: util.Collection[String]
        }.asJava
      )
    }

  def setParameters(parameters: Map[String, Seq[String]]) =
    subject.underlying {
      _.setFormParams(
        parameters.mapValues {
          _.asJava: java.util.List[String]
        }.asJava
      )
    }

  def setMethod(method: String) =
    subject.underlying {
      _.setMethod(method)
    }

  def setUrl(url: String) =
    subject.underlying {
      _.setUrl(url)
    }
}
