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

import algolia.http.{GET, HttpPayload, POST}
import algolia.objects.{Query, RequestOptions}
import org.json4s.Formats
import org.json4s.native.Serialization.write

case class BrowseIndexDefinition(
    source: String,
    query: Option[Query] = None,
    cursor: Option[String] = None,
    requestOptions: Option[RequestOptions] = None
)(implicit val formats: Formats)
    extends Definition {

  type T = BrowseIndexDefinition

  val post: BrowseIndexPostDefinition = BrowseIndexPostDefinition(source, query, cursor, requestOptions)

  def from(cursor: String): BrowseIndexDefinition = copy(cursor = Some(cursor))

  def query(query: Query): BrowseIndexDefinition = copy(query = Some(query))

  override def options(requestOptions: RequestOptions): BrowseIndexDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    val q = query.getOrElse(Query()).copy(cursor = cursor)

    HttpPayload(
      GET,
      Seq("1", "indexes", source, "browse"),
      queryParameters = Some(q.toQueryParam),
      isSearch = true,
      requestOptions = requestOptions
    )
  }
}

case class BrowseIndexPostDefinition(
  source: String,
  query: Option[Query] = None,
  cursor: Option[String] = None,
  requestOptions: Option[RequestOptions] = None
)(implicit val formats: Formats)
  extends Definition {

  type T = BrowseIndexPostDefinition

  def from(cursor: String): BrowseIndexPostDefinition = copy(cursor = Some(cursor))

  def query(query: Query): BrowseIndexPostDefinition = copy(query = Some(query))

  override def options(requestOptions: RequestOptions): BrowseIndexPostDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    val q = query.getOrElse(Query()).copy(cursor = cursor)
    val body = Map("params" -> q.toParam)

    HttpPayload(
      POST,
      Seq("1", "indexes", source, "browse"),
      body = Some(write(body)),
      isSearch = true,
      requestOptions = requestOptions
    )
  }
}
