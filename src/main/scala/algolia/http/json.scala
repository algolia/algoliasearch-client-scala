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

import java.nio.charset.Charset

import org.asynchttpclient.Response
import org.json4s.native.JsonMethods._
import org.json4s.{StringInput, _}

object Json extends (Response => JValue) {
  def apply(r: Response) =
    (String andThen (s => parse(StringInput(s), useBigDecimalForDouble = true))) (r)
}

object Response {
  def apply[T](f: Response => T) = f
}

object String extends (Response => String) {
  /** @return response body as a string decoded as either the charset provided by
    *         Content-Type header of the response or ISO-8859-1 */
  def apply(r: Response) = r.getResponseBody

  /** @return a function that will return response body decoded in the provided charset */
  case class charset(set: Charset) extends (Response => String) {
    def apply(r: Response) = r.getResponseBody(set)
  }

  /** @return a function that will return response body as a utf8 decoded string */
  object utf8 extends charset(Charset.forName("utf8"))

}
