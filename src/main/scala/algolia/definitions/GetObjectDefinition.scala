package algolia.definitions

import algolia.http.HttpPayload
import algolia.responses.Get
import algolia.{AlgoliaClient, Executable, _}
import org.json4s.JsonAST.JObject

import scala.concurrent.{ExecutionContext, Future}

case class GetObjectDefinition(index: Option[String] = None, oid: Option[String] = None) extends Definition {

  def get(objectId: String): GetObjectDefinition = copy(oid = Some(objectId))

  def /(objectId: String): GetObjectDefinition = copy(oid = Some(objectId))

  def from(ind: String): GetObjectDefinition = copy(index = Some(ind))

  def index(ind: String): GetObjectDefinition = copy(index = Some(ind))

  def objectId(objectId: String): GetObjectDefinition = copy(oid = Some(objectId))

  override private[algolia] def build(): HttpPayload =
    HttpPayload(http.GET, Seq("1", "indexes") ++ index ++ oid)
}

trait GetObjectDsl {

  case object get {

    def /(index: String) = from(index)

    def objectId(objectId: String) = GetObjectDefinition(oid = Some(objectId))

    def from(index: String) = GetObjectDefinition(index = Some(index))

    def apply(oid: String) = objectId(oid)

  }

  case object from {

    def index(index: String) = GetObjectDefinition(index = Some(index))

    def apply(ind: String) = index(ind)

  }

  implicit object GetObjectDefinitionExecutable extends Executable[GetObjectDefinition, Get] {

    override def apply(client: AlgoliaClient, query: GetObjectDefinition)(implicit executor: ExecutionContext): Future[Get] = {
      (client request[JObject] query.build()).map(Get(_))
    }
  }

}