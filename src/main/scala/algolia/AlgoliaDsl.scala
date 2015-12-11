package algolia

import algolia.definitions._

trait AlgoliaDsl
  extends BatchDefinitionDsl
  with ClearIndexDsl
  with CopyIndexDsl
  with DeleteDsl
  with GetObjectDsl
  with IndexingDsl
  with IndexingBatchDsl
  with ListIndexesDsl
  with MoveIndexDsl
  with SearchDsl

object AlgoliaDsl extends AlgoliaDsl {

  implicit val formats = org.json4s.DefaultFormats

}

