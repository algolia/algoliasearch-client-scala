package algolia.definitions

import algolia._
import algolia.http.HttpPayload
import algolia.responses.Search
import org.json4s.Formats
import org.json4s.native.Serialization.write

import scala.concurrent.{ExecutionContext, Future}

case class SearchDefinition(index: String,
                            query: Option[String] = None,
                            hitsPerPage: Option[Int] = None)(implicit val formats: Formats) extends Definition {

  def into(index: String): SearchDefinition = this

  def hitsPerPage(h: Int): SearchDefinition = copy(hitsPerPage = Some(h))

  def query(q: String): SearchDefinition = copy(query = Some(q))

  override private[algolia] def build(): HttpPayload = {
    val params = Seq() ++
      query.map(q => s"query=$q") ++
      hitsPerPage.map(h => s"hitsPerPage=$h")

    val body = Map("params" -> params.mkString("&"))

    HttpPayload(http.GET, Seq("1", "indexes", index, "query"), body = Some(write(body)))
  }
}


trait SearchDsl {

  implicit val formats: Formats

  case object search {

    def into(index: String): SearchDefinition = SearchDefinition(index)

    def in(index: String): SearchDefinition = into(index)
  }

  implicit object SearchDefinitionExecutable extends Executable[SearchDefinition, Search] {
    override def apply(client: AlgoliaClient, query: SearchDefinition)(implicit executor: ExecutionContext): Future[Search] = {
      client request[Search] query.build()
    }
  }

}