package algolia.definitions

import algolia._
import algolia.http.HttpPayload
import algolia.responses.Indexing
import org.json4s.native.Serialization.write

import scala.concurrent.Future

case class IndexingDefinition(index: String,
                              objectId: Option[String] = None,
                              obj: Option[AnyRef] = None,
                              objects: Option[Seq[AnyRef]] = None,
                              objectsWithIds: Option[Map[String, AnyRef]] = None) extends Definition {


  def objectId(objectId: String): IndexingDefinition =
    copy(index, objectId = Some(objectId), obj = obj)

  def document(objectId: String, obj: AnyRef): IndexingDefinition =
    copy(index, objectId = Some(objectId), obj = Some(obj))

  def document(obj: AnyRef): IndexingDefinition =
    copy(index, objectId = objectId, obj = Some(obj))

//  def documents(objects: Seq[AnyRef]): IndexingDefinition = ???

  //    copy(index, objects = Some(objects))

//  def documents(objectsWithIds: Map[String, AnyRef]): IndexingDefinition = ???

  //    copy(index, objectsWithIds = Some(objectsWithIds))

  implicit val formats = org.json4s.DefaultFormats

  override private[algolia] def build(): HttpPayload = {
    val body: Option[String] = obj.map(o => write(o))
    val verb = objectId match {
      case Some(_) => http.PUT
      case None => http.POST
    }

    HttpPayload(verb, Seq("1", "indexes", index) ++ objectId, body = body)
  }
}

trait IndexingDsl {

  case object index {

    def into(index: String): IndexingDefinition = IndexingDefinition(index)

    def in(index: String): IndexingDefinition = into(index)

  }


  implicit object IndexingDefinitionExecutable extends Executable[IndexingDefinition, Indexing] {
    override def apply(client: AlgoliaClient, query: IndexingDefinition): Future[Indexing] = {
      client request[Indexing] query.build()
    }
  }

}
