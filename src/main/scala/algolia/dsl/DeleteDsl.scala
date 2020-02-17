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
import algolia.inputs.SafeDeleteObjectOperation
import algolia.responses.Task
import algolia.{AlgoliaClient, Executable}
import org.json4s.Formats

import scala.concurrent.{ExecutionContext, Future}

trait DeleteDsl {

  implicit val formats: Formats

  case object delete {

    //Index
    def index(index: String): DeleteIndexDefinition =
      DeleteIndexDefinition(index)

    //Object
    @deprecated("use objectFromIndex", "1.30.0")
    def objectId(objectId: String) =
      DeleteObjectDefinition(oid = Some(objectId))

    def objectFromIndex(op: SafeDeleteObjectOperation) =
      SafeDeleteObjectDefinition(op)

    //Object(s)
    def from(index: String) = DeleteObjectDefinition(index = Some(index))

    def key(keyName: String) = DeleteKeyDefinition(keyName)

    def synonym(synId: String) = DeleteSynonymDefinition(synId = synId)

    def rule(ruleId: String) = DeleteRuleDefinition(objectId = ruleId)

    // AB test
    def abTest(id: Int) = DeleteABTestDefinition(id)

  }

  implicit object DeleteObjectDefinitionExecutable
      extends Executable[DeleteObjectDefinition, Task] {
    override def apply(client: AlgoliaClient, query: DeleteObjectDefinition)(
        implicit executor: ExecutionContext
    ): Future[Task] = {
      client.request[Task](query.build())
    }
  }

  implicit object DeleteIndexDefinitionExecutable
      extends Executable[DeleteIndexDefinition, Task] {
    override def apply(client: AlgoliaClient, query: DeleteIndexDefinition)(
        implicit executor: ExecutionContext
    ): Future[Task] = {
      client.request[Task](query.build())
    }
  }

  implicit object DeleteByDefinitionExecutable
      extends Executable[DeleteByDefinition, Task] {
    override def apply(client: AlgoliaClient, query: DeleteByDefinition)(
        implicit executor: ExecutionContext
    ): Future[Task] = {
      client.request[Task](query.build())
    }
  }

  implicit object SafeDeleteObjectDefinitionExecutable
      extends Executable[SafeDeleteObjectDefinition, Task] {
    override def apply(
        client: AlgoliaClient,
        query: SafeDeleteObjectDefinition
    )(implicit executor: ExecutionContext): Future[Task] = {
      client.request[Task](query.build())
    }
  }

}
