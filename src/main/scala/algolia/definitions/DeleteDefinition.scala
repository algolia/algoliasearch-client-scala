/*
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

import algolia.http.HttpPayload
import algolia.responses.Task
import algolia.{AlgoliaClient, Executable, _}
import org.json4s.Formats

import scala.concurrent.{ExecutionContext, Future}

case class DeleteObjectDefinition(index: Option[String] = None, oid: Option[String] = None)(implicit val formats: Formats) extends Definition {

  def from(ind: String): DeleteObjectDefinition = copy(index = Some(ind))

  def index(ind: String): DeleteObjectDefinition = copy(index = Some(ind))

  def objectId(objectId: String): DeleteObjectDefinition = copy(oid = Some(objectId))

  def objectIds(objectIds: Traversable[String]): BatchDefinition =
    BatchDefinition(objectIds.map { oid =>
      DeleteObjectDefinition(index, Some(oid))
    })

  override private[algolia] def build(): HttpPayload =
    HttpPayload(http.DELETE, Seq("1", "indexes") ++ index ++ oid, isSearch = false)
}

case class DeleteIndexDefinition(index: String) extends Definition {

  override private[algolia] def build(): HttpPayload =
    HttpPayload(http.DELETE, Seq("1", "indexes", index), isSearch = false)

}

trait DeleteDsl {

  implicit val formats: Formats

  case object delete {

    //Index
    def index(index: String): DeleteIndexDefinition = DeleteIndexDefinition(index)

    //Object
    def objectId(objectId: String) = DeleteObjectDefinition(oid = Some(objectId))

    //Object
    def from(index: String) = DeleteObjectDefinition(index = Some(index))

    def key(keyName: String) = DeleteApiKeyDefinition(keyName)

    def synonym(synId: String) = DeleteSynonymDefinition(synId = synId)

  }

  implicit object DeleteObjectDefinitionExecutable extends Executable[DeleteObjectDefinition, Task] {
    override def apply(client: AlgoliaClient, query: DeleteObjectDefinition)(implicit executor: ExecutionContext): Future[Task] = {
      client request[Task] query.build()
    }
  }

  implicit object DeleteIndexDefinitionExecutable extends Executable[DeleteIndexDefinition, Task] {
    override def apply(client: AlgoliaClient, query: DeleteIndexDefinition)(implicit executor: ExecutionContext): Future[Task] = {
      client request[Task] query.build()
    }
  }

}
