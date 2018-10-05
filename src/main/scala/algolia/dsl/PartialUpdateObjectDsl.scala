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

package algolia.dsl

import algolia.definitions._
import algolia.responses.Task
import algolia.{AlgoliaClient, Executable}
import org.json4s.Formats

import scala.concurrent.{ExecutionContext, Future}

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

  case object addUnique {

    def value(value: String): PartialUpdateObjectOperationDefinition =
      PartialUpdateObjectOperationDefinition(AddUnique, value = Some(value))

  }

  case object update {

    def attribute(attribute: String): PartialUpdateObjectDefinition =
      PartialUpdateObjectDefinition(attribute = Some(attribute))

    def key(keyName: String) = UpdateKeyDefinition(keyName)

  }

  case object partialUpdate {

    def from(index: String): PartialUpdateOneObjectDefinition = {
      PartialUpdateOneObjectDefinition(index = index)
    }

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

  implicit object PartialUpdateOneObjectDefinitionExecutable
      extends Executable[PartialUpdateOneObjectDefinition, Task] {
    override def apply(client: AlgoliaClient, query: PartialUpdateOneObjectDefinition)(
        implicit executor: ExecutionContext): Future[Task] = {
      client.request[Task](query.build())
    }
  }

}
