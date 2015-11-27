package algolia.definitions

import algolia.{HttpPayload, Index}
import org.json4s.native.Serialization.write

case class SearchDefinition(index: Index,
                            query: Option[String] = None,
                            hitsPerPage: Option[Int] = None) extends Definition {

  def into(index: String): SearchDefinition = this

  def hitsPerPage(h: Int): SearchDefinition = copy(index, hitsPerPage = Some(h))

  def query(q: String): SearchDefinition = copy(index, query = Some(q))

  implicit val formats = org.json4s.DefaultFormats

  override private[algolia] def build(): HttpPayload = {
    val params = Seq() ++
      query.map(q => s"query=$q") ++
      hitsPerPage.map(h => s"hitsPerPage=$h")

    val body = Map("params" -> params.mkString("&"))

    HttpPayload(Seq("1", "indexes", index.name, "query"), body = Some(write(body)))
  }
}

