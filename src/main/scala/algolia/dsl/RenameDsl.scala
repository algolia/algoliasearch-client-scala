package algolia.dsl
import algolia.{AlgoliaClient, Executable}
import algolia.definitions.RenameIndexDefinition
import algolia.responses.Task
import org.json4s.Formats

import scala.concurrent.{ExecutionContext, Future}

trait RenameDsl {

  implicit val formats: Formats

  case object rename {
    def index(index: String): RenameIndexDefinition = RenameIndexDefinition(index)
  }

  implicit object RenameIndexDefinitionExecutable extends Executable[RenameIndexDefinition, Task] {
    override def apply(client: AlgoliaClient, query: RenameIndexDefinition)(
        implicit executor: ExecutionContext): Future[Task] = {
      client.request[Task](query.build())
    }
  }

}
