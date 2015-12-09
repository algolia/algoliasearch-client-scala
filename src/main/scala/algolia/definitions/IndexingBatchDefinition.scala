package algolia.definitions

import algolia.http.{HttpPayload, POST}
import algolia.inputs.{AddObjectOperation, BatchOperations, UpdateObjectOperation}
import algolia.responses.TasksSingleIndex
import algolia.{AlgoliaClient, Executable}
import org.json4s.Formats
import org.json4s.native.Serialization._

import scala.concurrent.{ExecutionContext, Future}

case class IndexingBatchDefinition(index: String,
                                   definitions: Seq[Definition] = Seq())(implicit val formats: Formats) extends Definition with BatchOperationUtils {

  override private[algolia] def build(): HttpPayload = {
    val operations = definitions.map {
      case IndexingDefinition(_, None, Some(obj)) =>
        hasObjectId(obj) match {
          case (true, o) => UpdateObjectOperation(o)
          case (false, o) => AddObjectOperation(o)
        }

      case IndexingDefinition(_, Some(objectId), Some(obj)) =>
        UpdateObjectOperation(addObjectId(obj, objectId))
    }

    HttpPayload(
      POST,
      Seq("1", "indexes", index, "batch"),
      body = Some(write(BatchOperations(operations))),
      isSearch = false
    )
  }
}

trait IndexingBatchDsl {

  implicit object IndexingBatchDefinitionExecutable extends Executable[IndexingBatchDefinition, TasksSingleIndex] {
    override def apply(client: AlgoliaClient, batch: IndexingBatchDefinition)(implicit executor: ExecutionContext): Future[TasksSingleIndex] = {
      client request[TasksSingleIndex] batch.build()
    }
  }

}
