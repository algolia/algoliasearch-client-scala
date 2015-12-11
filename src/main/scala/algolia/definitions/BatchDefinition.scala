package algolia.definitions

import algolia.http.{HttpPayload, POST}
import algolia.inputs._
import algolia.responses.TasksMultipleIndex
import algolia.{AlgoliaClient, Executable}
import org.json4s.Formats
import org.json4s.native.Serialization._

import scala.concurrent.{ExecutionContext, Future}

case class BatchDefinition(definitions: Iterable[Definition])(implicit val formats: Formats) extends Definition with BatchOperationUtils {

  override private[algolia] def build(): HttpPayload = {
    val operations = definitions.map {
      case IndexingDefinition(index, None, Some(obj)) =>
        hasObjectId(obj) match {
          case (true, o) => UpdateObjectOperation(o, Some(index))
          case (false, o) => AddObjectOperation(o, Some(index))
        }

      case IndexingDefinition(index, Some(objectId), Some(obj)) =>
        UpdateObjectOperation(addObjectId(obj, objectId), Some(index))

      case ClearIndexDefinition(index) =>
        ClearIndexOperation(index)

      case DeleteObjectDefinition(Some(index), Some(oid)) =>
        DeleteObjectOperation(index, ObjectId(oid))

//      case IndexingBatchDefinition(index, defs) =>
//        defs.map {
//          case IndexingDefinition(_, None, Some(obj)) =>
//            hasObjectId(obj) match {
//              case (true, o) => UpdateObjectOperation(o)
//              case (false, o) => AddObjectOperation(o)
//            }
//
//          case IndexingDefinition(_, Some(objectId), Some(obj)) =>
//            UpdateObjectOperation(addObjectId(obj, objectId))
//        }

    }.toSeq

    HttpPayload(
      POST,
      Seq("1", "indexes", "*", "batch"),
      body = Some(write(BatchOperations(operations))),
      isSearch = false
    )
  }
}


trait BatchDefinitionDsl {

  implicit val formats: Formats

  def batch(batches: Iterable[Definition]): BatchDefinition = {
    BatchDefinition(batches)
  }

  def batch(batches: Definition*): BatchDefinition = {
    BatchDefinition(batches)
  }

  implicit object BatchDefinitionExecutable extends Executable[BatchDefinition, TasksMultipleIndex] {
    override def apply(client: AlgoliaClient, query: BatchDefinition)(implicit executor: ExecutionContext): Future[TasksMultipleIndex] = {
      client request[TasksMultipleIndex] query.build()
    }
  }

}