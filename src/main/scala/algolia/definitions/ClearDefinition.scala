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

import algolia.AlgoliaDsl.Of
import algolia.http.{HttpPayload, POST}
import algolia.responses.Task
import algolia.{AlgoliaClient, Executable}
import org.json4s.Formats

import scala.concurrent.{ExecutionContext, Future}

case class ClearIndexDefinition(index: String) extends Definition {

  override private[algolia] def build(): HttpPayload =
    HttpPayload(POST, Seq("1", "indexes", index, "clear"), isSearch = false)

}

trait ClearDsl {

  implicit val formats: Formats

  case object clear {

    def index(index: String): ClearIndexDefinition = ClearIndexDefinition(index)

    def synonyms(of: Of): ClearSynonymsDefinition = ClearSynonymsDefinition()

  }

  implicit object ClearIndexDefinitionExecutable extends Executable[ClearIndexDefinition, Task] {
    override def apply(client: AlgoliaClient, query: ClearIndexDefinition)(implicit executor: ExecutionContext): Future[Task] = {
      client request[Task] query.build()
    }
  }

}
