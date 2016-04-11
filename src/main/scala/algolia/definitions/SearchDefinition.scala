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

import algolia.AlgoliaDsl.In
import algolia._
import algolia.http.{HttpPayload, POST}
import algolia.objects.Query
import algolia.responses.SearchResult
import org.json4s.Formats
import org.json4s.native.Serialization.write

import scala.concurrent.{ExecutionContext, Future}

case class SearchDefinition(index: String,
                            query: Option[Query] = None)(implicit val formats: Formats) extends Definition {

  def query(q: Query): SearchDefinition = copy(query = Some(q))

  override private[algolia] def build(): HttpPayload = {
    val body = Map("params" -> query.map(_.toParam))

    HttpPayload(
      POST,
      Seq("1", "indexes", index, "query"),
      body = Some(write(body)),
      isSearch = true
    )
  }
}


trait SearchDsl {

  implicit val formats: Formats

  case object search {

    def into(index: String) = SearchDefinition(index)

    def synonyms(i: In): SearchSynonymsDefinition = SearchSynonymsDefinition()

  }

  implicit object SearchDefinitionExecutable extends Executable[SearchDefinition, SearchResult] {
    override def apply(client: AlgoliaClient, query: SearchDefinition)(implicit executor: ExecutionContext): Future[SearchResult] = {
      client request[SearchResult] query.build()
    }
  }

}
