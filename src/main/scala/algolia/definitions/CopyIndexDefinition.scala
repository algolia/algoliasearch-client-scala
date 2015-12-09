package algolia.definitions

import algolia.http.{HttpPayload, POST}
import algolia.inputs.IndexOperation
import algolia.responses.Task
import algolia.{AlgoliaClient, Executable}
import org.json4s.native.Serialization._

import scala.concurrent.{ExecutionContext, Future}

case class CopyIndexDefinition(source: String, destination: Option[String] = None) extends Definition {
  def to(destination: String) = copy(source, Some(destination))

  implicit val formats = org.json4s.DefaultFormats

  override private[algolia] def build(): HttpPayload = {
    val operation = IndexOperation("copy", destination)

    HttpPayload(
      POST,
      Seq("1", "indexes", source, "operation"),
      body = Some(write(operation)),
      isSearch = false
    )
  }
}

trait CopyIndexDsl {

  case object copy {

    def index(index: String): CopyIndexDefinition = CopyIndexDefinition(index)

  }

  implicit object CopyIndexDefinitionExecutable extends Executable[CopyIndexDefinition, Task] {
    override def apply(client: AlgoliaClient, query: CopyIndexDefinition)(implicit executor: ExecutionContext): Future[Task] = {
      client request[Task] query.build()
    }
  }

}