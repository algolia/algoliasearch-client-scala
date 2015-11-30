package algolia.definitions

import algolia.http.{HttpPayload, POST}
import algolia.responses.Task
import algolia.{AlgoliaClient, Executable}

import scala.concurrent.Future

case class ClearIndexDefinition(index: String) extends Definition {

  override private[algolia] def build(): HttpPayload =
    HttpPayload(POST, Seq("1", "indexes", index, "clear"))

}

trait ClearIndexDsl {

  implicit object ClearIndexDefinitionExecutable extends Executable[ClearIndexDefinition, Task] {
    override def apply(client: AlgoliaClient, query: ClearIndexDefinition): Future[Task] = {
      client request[Task] query.build()
    }
  }

}