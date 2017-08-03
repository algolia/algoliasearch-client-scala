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
import algolia.inputs.{Request, Requests}
import algolia.objects.RequestOptions
import algolia.responses.{GetObject, Results}
import algolia.{AlgoliaClient, Executable, _}
import org.json4s.Formats
import org.json4s.JsonAST.JObject
import org.json4s.native.Serialization._

import scala.concurrent.{ExecutionContext, Future}

case class GetObjectDefinition(
    index: Option[String] = None,
    oid: Option[String] = None,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  type T = GetObjectDefinition

  def objectIds(oids: Seq[String]): GetObjectsDefinition =
    GetObjectsDefinition(index, oids)

  def from(ind: String): GetObjectDefinition = copy(index = Some(ind))

  def objectId(objectId: String): GetObjectDefinition =
    copy(oid = Some(objectId))

  override def options(requestOptions: RequestOptions): GetObjectDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload =
    HttpPayload(
      GET,
      Seq("1", "indexes") ++ index ++ oid,
      isSearch = true,
      requestOptions = requestOptions
    )
}

case class GetObjectsDefinition(
    index: Option[String],
    oids: Seq[String] = Seq(),
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  type T = GetObjectsDefinition

  override def options(requestOptions: RequestOptions): GetObjectsDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    val requests = oids.map { oid =>
      Request(index, oid)
    }

    HttpPayload(
      POST,
      Seq("1", "indexes", "*", "objects"),
      body = Some(write(Requests(requests))),
      isSearch = true,
      requestOptions = requestOptions
    )
  }

}

trait GetDsl {

  implicit val formats: Formats

  case object get {

    def objectId(objectId: String) = GetObjectDefinition(oid = Some(objectId))

    def from(index: String) = GetObjectDefinition(index = Some(index))

    def key(keyName: String) = GetApiKeyDefinition(keyName)

    def allKeys() = GetAllApiKeyDefinition()

    def allKeysFrom(indexName: String) =
      GetAllApiKeyDefinition(indexName = Some(indexName))

    def synonym(synId: String) = GetSynonymDefinition(synId = synId)

  }

  implicit object GetObjectDefinitionExecutable
      extends Executable[GetObjectDefinition, GetObject] {

    override def apply(client: AlgoliaClient, query: GetObjectDefinition)(
        implicit executor: ExecutionContext): Future[GetObject] = {
      client.request[JObject](query.build()).map(GetObject)
    }

  }

  implicit object GetObjectsDefinitionExecutable
      extends Executable[GetObjectsDefinition, Results] {

    override def apply(client: AlgoliaClient, query: GetObjectsDefinition)(
        implicit executor: ExecutionContext): Future[Results] = {
      client.request[Results](query.build())
    }

  }

}
