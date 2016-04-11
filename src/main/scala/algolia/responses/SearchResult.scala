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

package algolia.responses

import algolia.objects.AbstractSynonym
import org.json4s._

case class SearchResult(hits: Seq[JObject],
                        page: Int,
                        nbHits: Int,
                        nbPages: Int,
                        hitsPerPage: Int,
                        processingTimeMS: Int,
                        facets: Option[Map[String, Map[String, Int]]],
                        exhaustiveFacetsCount: Option[Boolean],
                        query: String,
                        params: String) {

  implicit val formats = org.json4s.DefaultFormats

  def asHit[T <: Hit : Manifest]: Seq[T] = hits.map(_.extract[T])

  def as[T: Manifest]: Seq[T] = hits.map(_.extract[T])

}

case class SearchSynonymResult(hits: Seq[AbstractSynonym],
                               nbHits: Int)

case class MultiQueriesResult(results: Seq[SearchResult])

trait Hit {

  val objectID: String

  val _highlightResult: Option[Map[String, HighlightResult]]

  val _snippetResult: Option[Map[String, SnippetResult]]

  val _rankingInfo: Option[RankingInfo]

}

case class HighlightResult(value: String, matchLevel: String)

case class SnippetResult(value: String)

case class RankingInfo(nbTypos: Int,
                       firstMatchedWord: Int,
                       proximityDistance: Int,
                       userScore: Int,
                       geoDistance: Int,
                       geoPrecision: Int,
                       nbExactWords: Int)
