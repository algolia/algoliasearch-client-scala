package algolia.definitions

import algolia.http.{DELETE, HttpPayload}
import algolia.responses.Task
import algolia.{AlgoliaClient, Executable}

import scala.concurrent.Future

case class DeleteIndexDefinition(index: String) extends Definition {

  override private[algolia] def build(): HttpPayload =
    HttpPayload(DELETE, Seq("1", "indexes", index))

}

trait DeleteIndexDsl {

  case object delete {

    def index(index: String): DeleteIndexDefinition = DeleteIndexDefinition(index)

  }

  implicit object DeleteIndexDefinitionExecutable extends Executable[DeleteIndexDefinition, Task] {
    override def apply(client: AlgoliaClient, query: DeleteIndexDefinition): Future[Task] = {
      client request[Task] query.build()
    }
  }

}