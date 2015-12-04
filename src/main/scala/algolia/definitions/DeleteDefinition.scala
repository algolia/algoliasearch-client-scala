package algolia.definitions

import algolia.http.HttpPayload
import algolia.responses.Task
import algolia.{AlgoliaClient, Executable, _}

import scala.concurrent.{ExecutionContext, Future}

case class DeleteObjectDefinition(index: Option[String] = None, oid: Option[String] = None) extends Definition {

  def /(objectId: String): DeleteObjectDefinition = copy(index = index, oid = Some(objectId))

  def from(ind: String): DeleteObjectDefinition = copy(index = Some(ind), oid = oid)

  def index(ind: String): DeleteObjectDefinition = copy(index = Some(ind), oid = oid)

  def objectId(objectId: String): DeleteObjectDefinition = copy(index = index, oid = Some(objectId))

  override private[algolia] def build(): HttpPayload =
    HttpPayload(http.DELETE, Seq("1", "indexes") ++ index ++ oid)
}

case class DeleteIndexDefinition(index: String) extends Definition {

  override private[algolia] def build(): HttpPayload =
    HttpPayload(http.DELETE, Seq("1", "indexes", index))

}

trait DeleteDsl {

  case object delete {

    //Index
    def index(index: String): DeleteIndexDefinition = DeleteIndexDefinition(index)


    //Object
    def /(index: String) = from(index)

    def objectId(objectId: String) = DeleteObjectDefinition(oid = Some(objectId))

    def from(index: String) = DeleteObjectDefinition(index = Some(index))

    def apply(oid: String) = objectId(oid)

  }

  implicit object DeleteObjectDefinitionExecutable extends Executable[DeleteObjectDefinition, Task] {
    override def apply(client: AlgoliaClient, query: DeleteObjectDefinition)(implicit executor: ExecutionContext): Future[Task] = {
      client request[Task] query.build()
    }
  }

  implicit object DeleteIndexDefinitionExecutable extends Executable[DeleteIndexDefinition, Task] {
    override def apply(client: AlgoliaClient, query: DeleteIndexDefinition)(implicit executor: ExecutionContext): Future[Task] = {
      client request[Task] query.build()
    }
  }

}