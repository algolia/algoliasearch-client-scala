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

package algolia.definitions

import algolia.http.{GET, HttpPayload}
import algolia.objects.RequestOptions
import org.json4s.Formats

case class GetABTestsDefinition(offset: Int = 0, limit: Int = 10)(
    implicit val formats: Formats
) extends Definition {

  type T = GetABTestsDefinition

  override def options(requestOptions: RequestOptions): GetABTestsDefinition =
    this

  def offset(offset: Int): GetABTestsDefinition = copy(offset = offset)

  def limit(limit: Int): GetABTestsDefinition = copy(limit = limit)

  override private[algolia] def build(): HttpPayload =
    HttpPayload(
      verb = GET,
      path = Seq("2", "abtests"),
      queryParameters =
        Some(Map("offset" -> offset.toString, "limit" -> limit.toString)),
      isSearch = false,
      isAnalytics = true,
      requestOptions = None
    )

}