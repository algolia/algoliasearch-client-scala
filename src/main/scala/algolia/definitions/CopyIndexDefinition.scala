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
import algolia.inputs.IndexOperation
import algolia.objects.RequestOptions
import algolia.responses.Task
import algolia.{AlgoliaClient, Executable}
import org.json4s.Formats
import org.json4s.native.Serialization._

import scala.concurrent.{ExecutionContext, Future}

case class CopyIndexDefinition(
    source: String,
    destination: Option[String] = None,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  type T = CopyIndexDefinition

  def to(destination: String): CopyIndexDefinition =
    copy(source, Some(destination))

  override def options(requestOptions: RequestOptions): CopyIndexDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    val operation = IndexOperation("copy", destination)

    HttpPayload(
      POST,
      Seq("1", "indexes", source, "operation"),
      body = Some(write(operation)),
      isSearch = false,
      requestOptions = requestOptions
    )
  }
}

trait CopyIndexDsl {

  implicit val formats: Formats

  case object copy {

    def index(index: String): CopyIndexDefinition = CopyIndexDefinition(index)

  }

  implicit object CopyIndexDefinitionExecutable extends Executable[CopyIndexDefinition, Task] {
    override def apply(client: AlgoliaClient, query: CopyIndexDefinition)(
        implicit executor: ExecutionContext): Future[Task] = {
      client.request[Task](query.build())
    }
  }

}
