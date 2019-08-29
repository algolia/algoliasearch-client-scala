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

package algolia.responses

import algolia.objects.{AbstractSynonym, Rule}
import org.json4s._

private[algolia] trait SearchHits[A] {
  val hits: Seq[A]
}

case class SearchResult(hits: Seq[JObject],
                        nbHits: Int,
                        processingTimeMS: Int,
                        hitsPerPage: Option[Int],
                        page: Option[Int],
                        nbPages: Option[Int],
                        offset: Option[Int],
                        length: Option[Int],
                        facets: Option[Map[String, Map[String, Int]]],
                        exhaustiveFacetsCount: Option[Boolean],
                        exhaustiveNbHits: Option[Boolean],
                        query: String,
                        queryAfterRemoval: Option[String],
                        params: String,
                        message: Option[String],
                        aroundLatLng: Option[String],
                        automaticRadius: Option[Int],
                        facets_stats: Option[Map[String, Float]],
                        // For getRankingInfo
                        serverUsed: Option[String],
                        parsedQuery: Option[String],
                        appliedRules: Option[Map[String, String]],
                        // For multiqueries
                        processed: Option[Boolean],
                        index: Option[String]) {

  implicit val formats: Formats = org.json4s.DefaultFormats

  def asHit[T <: Hit: Manifest]: Seq[T] = hits.map(_.extract[T])

  def as[T: Manifest]: Seq[T] = hits.map(_.extract[T])

  def asWithObjectID[T <: ObjectID: Manifest]: Seq[T] = hits.map(_.extract[T])

  def getObjectPosition[T <: ObjectID: Manifest](objectID: String): Option[Int] =
    hits
      .map(_.extract[T])
      .zipWithIndex
      .find(_._1.objectID.equals(objectID))
      .map(_._2)
}

case class SearchSynonymResult(hits: Seq[AbstractSynonym], nbHits: Int)
    extends SearchHits[AbstractSynonym]

case class SearchRuleResult(hits: Seq[Rule], nbHits: Int) extends SearchHits[Rule]

case class MultiQueriesResult(results: Seq[SearchResult])

trait ObjectID {

  val objectID: String

}

trait Hit extends ObjectID {

  val _highlightResult: Option[Map[String, HighlightResult]]

  val _snippetResult: Option[Map[String, SnippetResult]]

  val _rankingInfo: Option[RankingInfo]

  val _distinctSeqID: Option[Integer]

}

// TODO: IIRC fullyHighlighted may not be returned, so here we would need to
// change its type from Boolean to Option[Boolean].
case class HighlightResult(value: String,
                           matchLevel: String,
                           matchedWords: Iterable[String],
                           fullyHighlighted: Option[Boolean])

case class SnippetResult(value: String, matchLevel: String)

case class RankingInfo(nbTypos: Int,
                       firstMatchedWord: Int,
                       proximityDistance: Int,
                       userScore: Int,
                       geoDistance: Int,
                       geoPrecision: Int,
                       nbExactWords: Int,
                       words: Int,
                       filters: Int,
                       promoted: Option[Boolean],
                       matchedGeoLocation: Option[Map[String, Float]])
