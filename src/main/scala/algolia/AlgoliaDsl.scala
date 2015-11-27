package algolia

import algolia.definitions._

trait AlgoliaDsl extends SearchDsl with IndexesDsl with IndexDsl {

  case object search {

    def into(index: Index): SearchDefinition = SearchDefinition(index)

    def into(index: String): SearchDefinition = into(Index(index))

    def in(index: String): SearchDefinition = into(index)

    def in(index: Index): SearchDefinition = into(index)

  }

  case object index {

    def into(index: Index): IndexDefinition = IndexDefinition(index)

    def into(index: String): IndexDefinition = into(Index(index))

    def in(index: String): IndexDefinition = into(index)

    def in(index: Index): IndexDefinition = into(index)

  }

  def indexes = new IndexesDefinition

  case object delete {

  }

  def clear(index: Index): Unit = ???

  def clear(index: String): Unit = clear(Index(index))

  case object get {

    def objectId(id: String) = ???

  }

  def get(id: String) = ???

  def get(ids: Seq[String]) = ???

  def /(id: String) = get(id)

  def /(ids: Seq[String]) = get(ids)


}

object AlgoliaDsl extends AlgoliaDsl
