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
import algolia.inputs.PartialUpdateObject
import algolia.objects.{ApiKey, RequestOptions}
import algolia.responses.Task
import algolia.{AlgoliaClient, Executable}
import org.json4s.Formats
import org.json4s.native.Serialization.write

import scala.concurrent.{ExecutionContext, Future}

private[algolia] sealed trait Operation {
  def name: String
}

case object Increment extends Operation {
  override def name: String = "Increment"
}

case object Decrement extends Operation {
  override def name: String = "Decrement"
}

case object Add extends Operation {
  override def name: String = "Add"
}

case object AddUnique extends Operation {
  override def name: String = "AddUnique"
}

case object Remove extends Operation {
  override def name: String = "Remove"
}

case class PartialUpdateObjectOperationDefinition(
    operation: Operation,
    index: Option[String] = None,
    objectId: Option[String] = None,
    attribute: Option[String] = None,
    value: Option[Any] = None,
    createNotExists: Boolean = true,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  type T = PartialUpdateObjectOperationDefinition

  def ofObjectId(objectId: String): PartialUpdateObjectOperationDefinition =
    copy(objectId = Some(objectId))

  def inAttribute(attr: String): PartialUpdateObjectOperationDefinition =
    attribute(attr)

  def attribute(attribute: String): PartialUpdateObjectOperationDefinition =
    copy(attribute = Some(attribute))

  def by(value: Int): PartialUpdateObjectOperationDefinition =
    copy(value = Some(value))

  def value(value: String): PartialUpdateObjectOperationDefinition =
    copy(value = Some(value))

  def from(index: String): PartialUpdateObjectOperationDefinition =
    copy(index = Some(index))

  def createIfNotExists(createNotExists: Boolean = false): PartialUpdateObjectOperationDefinition =
    copy(createNotExists = createNotExists)

  override def options(requestOptions: RequestOptions): PartialUpdateObjectOperationDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    val body = Map(
      attribute.get -> PartialUpdateObject(operation.name, value)
    )

    val queryParameters = if (createNotExists) {
      None
    } else {
      Some(Map("createIfNotExists" -> "false"))
    }

    HttpPayload(
      POST,
      Seq("1", "indexes") ++ index ++ objectId :+ "partial",
      queryParameters = queryParameters,
      body = Some(write(body)),
      isSearch = false,
      requestOptions = requestOptions
    )
  }

}

case class PartialUpdateObjectDefinition(
    index: Option[String] = None,
    objectId: Option[String] = None,
    attribute: Option[String] = None,
    value: Option[Any] = None,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  type T = PartialUpdateObjectDefinition

  def ofObjectId(objectId: String): PartialUpdateObjectDefinition =
    copy(objectId = Some(objectId))

  def value(value: Any): PartialUpdateObjectDefinition =
    copy(value = Some(value))

  def from(index: String): PartialUpdateObjectDefinition =
    copy(index = Some(index))

  override def options(requestOptions: RequestOptions): PartialUpdateObjectDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    val body = Map(
      attribute.get -> value
    )

    HttpPayload(
      POST,
      Seq("1", "indexes") ++ index ++ objectId :+ "partial",
      body = Some(write(body)),
      isSearch = false,
      requestOptions = requestOptions
    )
  }

}

trait PartialUpdateObjectDsl {

  implicit val formats: Formats

  case object increment {

    def attribute(attribute: String): PartialUpdateObjectOperationDefinition =
      PartialUpdateObjectOperationDefinition(Increment, attribute = Some(attribute))

  }

  case object decrement {

    def attribute(attribute: String): PartialUpdateObjectOperationDefinition =
      PartialUpdateObjectOperationDefinition(Decrement, attribute = Some(attribute))

  }

  case object add {

    def value(value: String): PartialUpdateObjectOperationDefinition =
      PartialUpdateObjectOperationDefinition(Add, value = Some(value))

    def key(key: ApiKey) = AddApiKeyDefinition(key)

  }

  case object addUnique {

    def value(value: String): PartialUpdateObjectOperationDefinition =
      PartialUpdateObjectOperationDefinition(AddUnique, value = Some(value))

  }

  case object remove {

    def value(value: String): PartialUpdateObjectOperationDefinition =
      PartialUpdateObjectOperationDefinition(Remove, value = Some(value))

  }

  case object update {

    def attribute(attribute: String): PartialUpdateObjectDefinition =
      PartialUpdateObjectDefinition(attribute = Some(attribute))

    def key(keyName: String) = UpdateApiKeyDefinition(keyName)

  }

  implicit object PartialUpdateObjectOperationExecutable
      extends Executable[PartialUpdateObjectOperationDefinition, Task] {
    override def apply(client: AlgoliaClient, query: PartialUpdateObjectOperationDefinition)(
        implicit executor: ExecutionContext): Future[Task] = {
      client.request[Task](query.build())
    }
  }

  implicit object PartialUpdateObjectExecutable
      extends Executable[PartialUpdateObjectDefinition, Task] {
    override def apply(client: AlgoliaClient, query: PartialUpdateObjectDefinition)(
        implicit executor: ExecutionContext): Future[Task] = {
      client.request[Task](query.build())
    }
  }

}
