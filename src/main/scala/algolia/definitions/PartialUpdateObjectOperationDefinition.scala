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
import algolia.objects.RequestOptions
import algolia.responses.ObjectID
import org.json4s.Formats
import org.json4s.native.Serialization.write

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

case object IncrementFrom extends Operation {
  override def name: String = "IncrementFrom"
}

case object IncrementSet extends Operation {
  override def name: String = "IncrementSet"
}

case class PartialUpdateObjectOperationDefinition(
    operation: Operation,
    index: Option[String] = None,
    objectId: Option[String] = None,
    attribute: Option[String] = None,
    value: Option[Any] = None,
    createIfNotExists: Boolean = true,
    requestOptions: Option[RequestOptions] = None
)(implicit val formats: Formats)
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

  def createIfNotExists(
      createIfNotExists: Boolean = false
  ): PartialUpdateObjectOperationDefinition =
    copy(createIfNotExists = createIfNotExists)

  override def options(
      requestOptions: RequestOptions
  ): PartialUpdateObjectOperationDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    val body = Map(
      attribute.get -> PartialUpdateObject(operation.name, value)
    )

    val queryParameters = if (createIfNotExists) {
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
    requestOptions: Option[RequestOptions] = None
)(implicit val formats: Formats)
    extends Definition {

  type T = PartialUpdateObjectDefinition

  def ofObjectId(objectId: String): PartialUpdateObjectDefinition =
    copy(objectId = Some(objectId))

  def value(value: Any): PartialUpdateObjectDefinition =
    copy(value = Some(value))

  def from(index: String): PartialUpdateObjectDefinition =
    copy(index = Some(index))

  override def options(
      requestOptions: RequestOptions
  ): PartialUpdateObjectDefinition =
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

case class PartialUpdateOneObjectDefinition(
    index: String,
    `object`: Option[ObjectID] = None,
    createIfNotExists: Boolean = true,
    requestOptions: Option[RequestOptions] = None
)(implicit val formats: Formats)
    extends Definition {
  override type T = PartialUpdateOneObjectDefinition

  override private[algolia] def build(): HttpPayload = {
    val queryParameters = if (createIfNotExists) {
      None
    } else {
      Some(Map("createIfNotExists" -> "false"))
    }

    HttpPayload(
      POST,
      Seq("1", "indexes", index) ++ `object`.map(_.objectID) :+ "partial",
      queryParameters = queryParameters,
      body = Some(write(`object`)),
      isSearch = false,
      requestOptions = requestOptions
    )
  }

  def createIfNotExists(
      createIfNotExists: Boolean = false
  ): PartialUpdateOneObjectDefinition =
    copy(createIfNotExists = createIfNotExists)

  override def options(
      requestOptions: RequestOptions
  ): PartialUpdateOneObjectDefinition =
    copy(requestOptions = Some(requestOptions))

  def `object`[T <: ObjectID](obj: T): PartialUpdateOneObjectDefinition =
    copy(`object` = Some(obj))

  def objects[T <: ObjectID](objects: Iterable[T]): BatchDefinition =
    BatchDefinition(
      objects.map(o =>
        PartialUpdateOneObjectDefinition(
          index,
          Some(o),
          createIfNotExists,
          requestOptions
        )
      ),
      requestOptions
    )

}
