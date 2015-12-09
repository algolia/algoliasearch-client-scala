package algolia.definitions

import algolia._
import algolia.http.HttpPayload
import algolia.responses.Indexing
import org.json4s.Formats
import org.json4s.native.Serialization.write

import scala.concurrent.{ExecutionContext, Future}

case class IndexingDefinition(index: String,
                              objectId: Option[String] = None,
                              obj: Option[AnyRef] = None)(implicit val formats: Formats) extends Definition {

  def documents(objectsWithIds: Map[String, AnyRef]): IndexingBatchDefinition =
    IndexingBatchDefinition(index, objectsWithIds.map { case (oid, o) =>
      IndexingDefinition(index, Some(oid), Some(o))
    }.toSeq)

  def documents(objects: Seq[AnyRef]): IndexingBatchDefinition =
    IndexingBatchDefinition(index, objects.map { obj => copy(obj = Some(obj)) })

  def objectId(objectId: String): IndexingDefinition =
    copy(objectId = Some(objectId))

  def document(objectId: String, obj: AnyRef): IndexingDefinition =
    copy(objectId = Some(objectId), obj = Some(obj))

  def document(obj: AnyRef): IndexingDefinition =
    copy(obj = Some(obj))

  override private[algolia] def build(): HttpPayload = {
    val body: Option[String] = obj.map(o => write(o))
    val verb = objectId match {
      case Some(_) => http.PUT
      case None => http.POST
    }

    HttpPayload(verb, Seq("1", "indexes", index) ++ objectId, body = body, isSearch = false)
  }
}

trait IndexingDsl {

  implicit val formats: Formats

  case object index {

    def into(index: String): IndexingDefinition = IndexingDefinition(index)

    def in(index: String): IndexingDefinition = into(index)

  }

  implicit object IndexingDefinitionExecutable extends Executable[IndexingDefinition, Indexing] {
    override def apply(client: AlgoliaClient, query: IndexingDefinition)(implicit executor: ExecutionContext): Future[Indexing] = {
      client request[Indexing] query.build()
    }
  }

}
