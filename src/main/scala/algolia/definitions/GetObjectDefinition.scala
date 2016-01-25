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
import algolia.responses.Get
import algolia.{AlgoliaClient, Executable, _}
import org.json4s.JsonAST.JObject

import scala.concurrent.{ExecutionContext, Future}

case class GetObjectDefinition(index: Option[String] = None, oid: Option[String] = None) extends Definition {

  def from(ind: String): GetObjectDefinition = copy(index = Some(ind))

  def objectId(objectId: String): GetObjectDefinition = copy(oid = Some(objectId))

  override private[algolia] def build(): HttpPayload =
    HttpPayload(http.GET, Seq("1", "indexes") ++ index ++ oid)
}

trait GetObjectDsl {

  case object get {

    def objectId(objectId: String) = GetObjectDefinition(oid = Some(objectId))

    def from(index: String) = GetObjectDefinition(index = Some(index))

  }

  implicit object GetObjectDefinitionExecutable extends Executable[GetObjectDefinition, Get] {

    override def apply(client: AlgoliaClient, query: GetObjectDefinition)(implicit executor: ExecutionContext): Future[Get] = {
      (client request[JObject] query.build()).map(Get(_))
    }
  }

}
