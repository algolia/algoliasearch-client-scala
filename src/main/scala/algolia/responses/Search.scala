package algolia.responses

import org.json4s._

case class Search(hits: Seq[JValue],
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

case class Hit(objectID: String,
               _highlightResult: Option[Map[String, HighlightResult]],
               _snippetResult: Option[Map[String, SnippetResult]],
               _rankingInfo: Option[RankingInfo])

case class HighlightResult(value: String, matchLevel: String)

case class SnippetResult(value: String)

case class RankingInfo(nbTypos: Int,
                       firstMatchedWord: Int,
                       proximityDistance: Int,
                       userScore: Int,
                       geoDistance: Int,
                       geoPrecision: Int,
                       nbExactWords: Int)