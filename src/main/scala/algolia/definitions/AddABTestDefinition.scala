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

import java.time.ZoneOffset

import algolia.http.{HttpPayload, POST}
import algolia.inputs.ABTest
import algolia.objects.RequestOptions
import org.json4s.Formats
import org.json4s.native.Serialization._

case class AddABTestDefinition(abtest: ABTest)(implicit val formats: Formats)
    extends Definition {

  type T = AddABTestDefinition

  override def options(requestOptions: RequestOptions): AddABTestDefinition =
    this

  override private[algolia] def build(): HttpPayload = {
    val body = Map(
      "name" -> abtest.name,
      "variants" -> abtest.variants,
      "endAt" -> abtest.endAt.atOffset(ZoneOffset.UTC).toString
    )

    HttpPayload(
      POST,
      Seq("2", "abtests"),
      body = Some(write(body)),
      isSearch = false,
      isAnalytics = true,
      requestOptions = None
    )
  }
}
