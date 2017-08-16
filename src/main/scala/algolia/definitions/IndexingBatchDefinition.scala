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

import algolia.http.{HttpPayload, POST}
import algolia.inputs.{AddObjectOperation, BatchOperations, UpdateObjectOperation}
import algolia.objects.RequestOptions
import algolia.responses.TasksSingleIndex
import algolia.{AlgoliaClient, Executable}
import org.json4s.Formats
import org.json4s.native.Serialization._

import scala.concurrent.{ExecutionContext, Future}

case class IndexingBatchDefinition(
    index: String,
    definitions: Traversable[Definition] = Traversable(),
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition
    with BatchOperationUtils {

  type T = IndexingBatchDefinition

  override def options(requestOptions: RequestOptions): IndexingBatchDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    val operations = definitions.map {
      case IndexingDefinition(_, None, Some(obj), _) =>
        hasObjectId(obj) match {
          case (true, o) => UpdateObjectOperation(o)
          case (false, o) => AddObjectOperation(o)
        }

      case IndexingDefinition(_, Some(objectId), Some(obj), _) =>
        UpdateObjectOperation(addObjectId(obj, objectId))
    }

    HttpPayload(
      POST,
      Seq("1", "indexes", index, "batch"),
      body = Some(write(BatchOperations(operations))),
      isSearch = false,
      requestOptions = requestOptions
    )
  }
}

trait IndexingBatchDsl {

  implicit object IndexingBatchDefinitionExecutable
      extends Executable[IndexingBatchDefinition, TasksSingleIndex] {
    override def apply(client: AlgoliaClient, batch: IndexingBatchDefinition)(
        implicit executor: ExecutionContext): Future[TasksSingleIndex] = {
      client.request[TasksSingleIndex](batch.build())
    }
  }

}
