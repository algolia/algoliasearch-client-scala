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
import algolia.objects.RequestOptions
import algolia.responses.TaskIndexing
import org.json4s.Formats
import org.json4s.native.Serialization.write

import scala.concurrent.{ExecutionContext, Future}

case class IndexingDefinition(
    index: String,
    objectId: Option[String] = None,
    obj: Option[AnyRef] = None,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  type T = IndexingDefinition

  override def options(requestOptions: RequestOptions): IndexingDefinition =
    copy(requestOptions = Some(requestOptions))

  def objects(objectsWithIds: Map[String, AnyRef]): IndexingBatchDefinition =
    IndexingBatchDefinition(index, objectsWithIds.map {
      case (oid, o) =>
        IndexingDefinition(index, Some(oid), Some(o))
    })

  def objects(objects: Traversable[AnyRef]): IndexingBatchDefinition =
    IndexingBatchDefinition(index, objects.map { obj =>
      copy(index = index, obj = Some(obj))
    })

  def objectId(objectId: String): IndexingDefinition =
    copy(objectId = Some(objectId))

  def `object`(objectId: String, obj: AnyRef): IndexingDefinition =
    copy(objectId = Some(objectId), obj = Some(obj))

  def `object`(obj: AnyRef): IndexingDefinition =
    copy(obj = Some(obj))

  override private[algolia] def build(): HttpPayload = {
    val body: Option[String] = obj.map(o => write(o))
    val verb = objectId match {
      case Some(_) => http.PUT
      case None => http.POST
    }

    HttpPayload(verb,
                Seq("1", "indexes", index) ++ objectId,
                body = body,
                isSearch = false,
                requestOptions = requestOptions)
  }
}

trait IndexingDsl {

  implicit val formats: Formats

  case object index {

    def into(index: String): IndexingDefinition = IndexingDefinition(index)

  }

  implicit object IndexingDefinitionExecutable
      extends Executable[IndexingDefinition, TaskIndexing] {
    override def apply(client: AlgoliaClient, query: IndexingDefinition)(
        implicit executor: ExecutionContext): Future[TaskIndexing] = {
      client.request[TaskIndexing](query.build())
    }
  }

}
