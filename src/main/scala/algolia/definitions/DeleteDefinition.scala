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

import algolia._
import algolia.http.HttpPayload
import algolia.objects.{Query, RequestOptions}
import org.json4s.Formats
import org.json4s.native.Serialization.write

case class DeleteObjectDefinition(
    index: Option[String] = None,
    oid: Option[String] = None,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  type T = DeleteObjectDefinition

  def from(ind: String): DeleteObjectDefinition = copy(index = Some(ind))

  @deprecated("use objectFromIndex", "1.30.0")
  def index(ind: String): DeleteObjectDefinition = copy(index = Some(ind))

  @deprecated("use objectFromIndex", "1.30.0")
  def objectId(objectId: String): DeleteObjectDefinition =
    copy(oid = Some(objectId))

  def objectIds(objectIds: Iterable[String]): BatchDefinition =
    BatchDefinition(objectIds.map { oid =>
      DeleteObjectDefinition(index, Some(oid))
    })

  def by(query: Query): DeleteByDefinition =
    DeleteByDefinition(index, query, requestOptions)

  override def options(requestOptions: RequestOptions): DeleteObjectDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload =
    HttpPayload(http.DELETE,
                Seq("1", "indexes") ++ index ++ oid,
                isSearch = false,
                requestOptions = requestOptions)
}

case class DeleteIndexDefinition(index: String, requestOptions: Option[RequestOptions] = None)
    extends Definition {

  type T = DeleteIndexDefinition

  override def options(requestOptions: RequestOptions): DeleteIndexDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload =
    HttpPayload(http.DELETE,
                Seq("1", "indexes", index),
                isSearch = false,
                requestOptions = requestOptions)

}

case class DeleteByDefinition(
    index: Option[String],
    query: Query,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {
  type T = DeleteByDefinition

  override private[algolia] def build(): HttpPayload = {
    val body = Map("params" -> query.toParam)

    HttpPayload(http.POST,
                Seq("1", "indexes") ++ index ++ Some("deleteByQuery"),
                isSearch = false,
                body = Some(write(body)),
                requestOptions = requestOptions)
  }

  override def options(requestOptions: RequestOptions): DeleteByDefinition =
    copy(requestOptions = Some(requestOptions))
}
