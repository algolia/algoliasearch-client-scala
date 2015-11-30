package algolia

import algolia.definitions._

trait AlgoliaDsl
  extends ClearIndexDsl
  with CopyIndexDsl
  with DeleteIndexDsl
  with GetObjectDsl
  with IndexingDsl
  with ListIndexesDsl
  with MoveIndexDsl
  with SearchDsl {

  //  def get(ids: Seq[String]) = ???
  //  def /(ids: Seq[String]) = get(ids)
}

object AlgoliaDsl extends AlgoliaDsl

