package algolia.definitions

import algolia.Index
import org.json4s.JsonAST.JObject

import org.json4s.JsonDSL._


case class SearchDefinition(index: Index,
                            query: Option[String] = None,
                            hitsPerPage: Option[Int] = None) extends Definition[JObject] {

  def into(index: String): SearchDefinition = this

  def hitsPerPage(h: Int): SearchDefinition = copy(index, hitsPerPage = Some(h))

  def query(q: String): SearchDefinition = copy(index, query = Some(q))

  override private[algolia] def build(): JObject = {
    val params = Seq() ++
      query.map(q => s"query=$q") ++
      hitsPerPage.map(h => s"hitsPerPage=$h")

    "params" -> params.mkString("&")
  }
}
