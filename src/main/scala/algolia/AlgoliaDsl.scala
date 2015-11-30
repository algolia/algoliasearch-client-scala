package algolia

import algolia.definitions._

trait AlgoliaDsl
  extends SearchDsl
  with IndexesDsl
  with IndexingDsl
  with ClearIndexDsl
  with DeleteIndexDsl {

  case object search {

    def into(index: String): SearchDefinition = SearchDefinition(index)

    def in(index: String): SearchDefinition = into(index)

  }

  case object index {

    def into(index: String): IndexingDefinition = IndexingDefinition(index)

    def in(index: String): IndexingDefinition = into(index)

  }

  def indexes = new IndexesDefinition

  case object delete {

    def index(index: String): DeleteIndexDefinition = DeleteIndexDefinition(index)

  }

  case object clear {

    def index(index: String): ClearIndexDefinition = ClearIndexDefinition(index)

  }

  case object get {

    def objectId(id: String) = ???

  }

  def get(id: String) = ???

  def get(ids: Seq[String]) = ???

  def /(id: String) = get(id)

  def /(ids: Seq[String]) = get(ids)


}

object AlgoliaDsl extends AlgoliaDsl

