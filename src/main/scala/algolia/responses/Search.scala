package algolia.responses

import org.json4s.JObject

trait Search {

  def hits: Seq[JObject]

  def page: Int

  def nbHits: Int

  def nbPages: Int

  def hitsPerPage: Int

  def processingTimeMS: Int

  def facets: Option[Map[String, Map[String, Int]]]

  def exhaustiveFacetsCount: Option[Boolean]

  def query: String

  def params: String

}

case class SearchImpl(hits: Seq[JObject],
                      page: Int,
                      nbHits: Int,
                      nbPages: Int,
                      hitsPerPage: Int,
                      processingTimeMS: Int,
                      facets: Option[Map[String, Map[String, Int]]],
                      exhaustiveFacetsCount: Option[Boolean],
                      query: String,
                      params: String) extends Search

