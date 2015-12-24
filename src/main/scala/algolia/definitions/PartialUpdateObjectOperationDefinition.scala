/*
 * Copyright (c) 2015 Algolia
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

case class PartialUpdateObjectOperationDefinition(operation: Operation,
                                                  index: Option[String] = None,
                                                  objectId: Option[String] = None,
                                                  attribute: Option[String] = None,
                                                  value: Option[Any] = None)(implicit val formats: Formats) extends Definition {

  def ofObjectId(objectId: String): PartialUpdateObjectOperationDefinition = copy(objectId = Some(objectId))

  def attribute(attribute: String): PartialUpdateObjectOperationDefinition = copy(attribute = Some(attribute))

  def by(value: Int): PartialUpdateObjectOperationDefinition = copy(value = Some(value))

  def value(value: String): PartialUpdateObjectOperationDefinition = copy(value = Some(value))

  def from(index: String): PartialUpdateObjectOperationDefinition = copy(index = Some(index))

  override private[algolia] def build(): HttpPayload = {
    val body = Map(
      attribute.get -> PartialUpdateObject(operation.name, value)
    )

    HttpPayload(
      POST,
      Seq("1", "indexes") ++ index ++ objectId :+ "partial",
      body = Some(write(body)),
      isSearch = false
    )
  }

}

case class PartialUpdateObjectDefinition(index: Option[String] = None,
                                         objectId: Option[String] = None,
                                         attribute: Option[String] = None,
                                         value: Option[Any] = None)(implicit val formats: Formats) extends Definition {

  def ofObjectId(objectId: String): PartialUpdateObjectDefinition = copy(objectId = Some(objectId))

  def attribute(attribute: String): PartialUpdateObjectDefinition = copy(attribute = Some(attribute))

  def value(value: Any): PartialUpdateObjectDefinition = copy(value = Some(value))

  def from(index: String): PartialUpdateObjectDefinition = copy(index = Some(index))

  override private[algolia] def build(): HttpPayload = {
    val body = Map(
      attribute.get -> value
    )

    HttpPayload(
      POST,
      Seq("1", "indexes") ++ index ++ objectId :+ "partial",
      body = Some(write(body)),
      isSearch = false
    )
  }

}

trait PartialUpdateObjectDsl {

  implicit val formats: Formats

  case object increment {

    def ofObjectId(objectId: String): PartialUpdateObjectOperationDefinition = PartialUpdateObjectOperationDefinition(Increment, objectId = Some(objectId))

    def attribute(attribute: String): PartialUpdateObjectOperationDefinition = PartialUpdateObjectOperationDefinition(Increment, attribute = Some(attribute))

    def by(inc: Int): PartialUpdateObjectOperationDefinition = PartialUpdateObjectOperationDefinition(Increment, value = Some(inc))

    def from(index: String): PartialUpdateObjectOperationDefinition = PartialUpdateObjectOperationDefinition(Increment, index = Some(index))
  }

  case object decrement {

    def objectId(objectId: String): PartialUpdateObjectOperationDefinition = PartialUpdateObjectOperationDefinition(Decrement, objectId = Some(objectId))

    def attribute(attribute: String): PartialUpdateObjectOperationDefinition = PartialUpdateObjectOperationDefinition(Decrement, attribute = Some(attribute))

    def by(dec: Int): PartialUpdateObjectOperationDefinition = PartialUpdateObjectOperationDefinition(Decrement, value = Some(dec))

    def from(index: String): PartialUpdateObjectOperationDefinition = PartialUpdateObjectOperationDefinition(Decrement, index = Some(index))

  }

  case object add {

    def objectId(objectId: String): PartialUpdateObjectOperationDefinition = PartialUpdateObjectOperationDefinition(Add, objectId = Some(objectId))

    def inAttribute(attribute: String): PartialUpdateObjectOperationDefinition = PartialUpdateObjectOperationDefinition(Add, attribute = Some(attribute))

    def value(value: String): PartialUpdateObjectOperationDefinition = PartialUpdateObjectOperationDefinition(Add, value = Some(value))

    def from(index: String): PartialUpdateObjectOperationDefinition = PartialUpdateObjectOperationDefinition(Add, index = Some(index))

  }

  case object addUnique {

    def objectId(objectId: String): PartialUpdateObjectOperationDefinition = PartialUpdateObjectOperationDefinition(AddUnique, objectId = Some(objectId))

    def inAttribute(attribute: String): PartialUpdateObjectOperationDefinition = PartialUpdateObjectOperationDefinition(AddUnique, attribute = Some(attribute))

    def value(value: String): PartialUpdateObjectOperationDefinition = PartialUpdateObjectOperationDefinition(AddUnique, value = Some(value))

    def from(index: String): PartialUpdateObjectOperationDefinition = PartialUpdateObjectOperationDefinition(AddUnique, index = Some(index))

  }

  case object remove {

    def objectId(objectId: String): PartialUpdateObjectOperationDefinition = PartialUpdateObjectOperationDefinition(Remove, objectId = Some(objectId))

    def inAttribute(attribute: String): PartialUpdateObjectOperationDefinition = PartialUpdateObjectOperationDefinition(Remove, attribute = Some(attribute))

    def value(value: String): PartialUpdateObjectOperationDefinition = PartialUpdateObjectOperationDefinition(Remove, value = Some(value))

    def from(index: String): PartialUpdateObjectOperationDefinition = PartialUpdateObjectOperationDefinition(Remove, index = Some(index))

  }

  case object update {

    def objectId(objectId: String): PartialUpdateObjectDefinition = PartialUpdateObjectDefinition(objectId = Some(objectId))

    def attribute(attribute: String): PartialUpdateObjectDefinition = PartialUpdateObjectDefinition(attribute = Some(attribute))

    def value(value: Any): PartialUpdateObjectDefinition = PartialUpdateObjectDefinition(value = Some(value))

    def from(index: String): PartialUpdateObjectDefinition = PartialUpdateObjectDefinition(index = Some(index))

  }

  implicit object PartialUpdateObjectOperationExecutable extends Executable[PartialUpdateObjectOperationDefinition, Task] {
    override def apply(client: AlgoliaClient, query: PartialUpdateObjectOperationDefinition)(implicit executor: ExecutionContext): Future[Task] = {
      client request[Task] query.build()
    }
  }

  implicit object PartialUpdateObjectExecutable extends Executable[PartialUpdateObjectDefinition, Task] {
    override def apply(client: AlgoliaClient, query: PartialUpdateObjectDefinition)(implicit executor: ExecutionContext): Future[Task] = {
      client request[Task] query.build()
    }
  }

}
