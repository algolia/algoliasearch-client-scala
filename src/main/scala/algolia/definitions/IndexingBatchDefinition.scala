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
import algolia.inputs._
import algolia.objects.RequestOptions
import org.json4s.JsonAST.JValue
import org.json4s.native.Serialization._
import org.json4s.{Extraction, Formats}

case class IndexingBatchDefinition(
    index: String,
    definitions: Iterable[Definition] = Iterable(),
    requestOptions: Option[RequestOptions] = None
)(implicit val formats: Formats)
    extends Definition
    with BatchOperationUtils {

  type T = IndexingBatchDefinition

  override def options(
      requestOptions: RequestOptions
  ): IndexingBatchDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    val operations = definitions.flatMap(transform)

    HttpPayload(
      POST,
      Seq("1", "indexes", index, "batch"),
      body = Some(write(BatchOperations(operations))),
      isSearch = false,
      requestOptions = requestOptions
    )
  }

  private def transform(
      definition: Definition
  ): Iterable[BatchOperation[JValue]] = {
    definition match {
      case IndexingDefinition(_, None, Some(obj), _) =>
        hasObjectId(obj) match {
          case (true, o)  => Iterable(UpdateObjectOperation(o))
          case (false, o) => Iterable(AddObjectOperation(o))
        }

      case IndexingDefinition(_, Some(objectId), Some(obj), _) =>
        Iterable(UpdateObjectOperation(addObjectId(obj, objectId)))

      case ClearIndexDefinition(_, _) =>
        Iterable(ClearIndexOperation())

      case DeleteObjectDefinition(_, Some(oid), _) =>
        Iterable(DeleteObjectOperation(objectID = oid))

      case SafeDeleteObjectDefinition(op, _) =>
        Iterable(DeleteObjectOperation(objectID = op.objectID))

      case DeleteIndexDefinition(_, _) =>
        Iterable(DeleteIndexOperation())

      case PartialUpdateObjectOperationDefinition(
          operation,
          index,
          Some(objectId),
          Some(attribute),
          value,
          true,
          _
          ) =>
        val body = Map(
          "objectID" -> objectId,
          attribute -> PartialUpdateObject(operation.name, value)
        )
        Iterable(
          PartialUpdateObjectOperation(Extraction.decompose(body))
        )

      case PartialUpdateObjectOperationDefinition(
          operation,
          index,
          Some(objectId),
          Some(attribute),
          value,
          false,
          _
          ) =>
        val body = Map(
          "objectID" -> objectId,
          attribute -> PartialUpdateObject(operation.name, value)
        )
        Iterable(
          PartialUpdateObjectNoCreateOperation(Extraction.decompose(body))
        )

      case PartialUpdateObjectDefinition(
          _,
          Some(objectId),
          Some(attribute),
          value,
          _
          ) =>
        val body = Map(
          "objectID" -> objectId,
          attribute -> value
        )
        Iterable(
          PartialUpdateObjectOperation(Extraction.decompose(body))
        )

      case IndexingBatchDefinition(_, defs, _) =>
        defs.flatMap(transform)

      case PartialUpdateOneObjectDefinition(
          _,
          Some(obj),
          createIfNotExists,
          _
          ) =>
        if (createIfNotExists) {
          Iterable(
            PartialUpdateObjectOperation(Extraction.decompose(obj))
          )
        } else {
          Iterable(
            PartialUpdateObjectNoCreateOperation(Extraction.decompose(obj))
          )
        }
    }
  }
}
