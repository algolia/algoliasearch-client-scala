package algolia.definitions

import algolia.http.{HttpPayload, POST}
import algolia.inputs.IndexOperation
import algolia.responses.Task
import algolia.{AlgoliaClient, Executable}
import org.json4s.native.Serialization._

import scala.concurrent.{ExecutionContext, Future}

case class MoveIndexDefinition(source: String, destination: Option[String] = None) extends Definition {

  def to(destination: String) = copy(source, Some(destination))

  implicit val formats = org.json4s.DefaultFormats

  override private[algolia] def build(): HttpPayload = {
    val operation = IndexOperation("move", destination)

    HttpPayload(POST, Seq("1", "indexes", source, "operation"), body = Some(write(operation)))
  }
}

trait MoveIndexDsl {

  case object move {

    def index(index: String): MoveIndexDefinition = MoveIndexDefinition(index)

  }

  implicit object MoveIndexDefinitionExecutable extends Executable[MoveIndexDefinition, Task] {
    override def apply(client: AlgoliaClient, query: MoveIndexDefinition)(implicit executor: ExecutionContext): Future[Task] = {
      client request[Task] query.build()
    }
  }

}