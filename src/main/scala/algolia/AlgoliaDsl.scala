package algolia

import algolia.definitions._
import algolia.responses.{Indexes, Indexing, Search, Task}

import scala.concurrent.Future

trait AlgoliaDsl extends SearchDsl with IndexesDsl with IndexDsl with ClearIndexDsl {

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

  }

  def clear(index: String): ClearIndexDefinition = ClearIndexDefinition(index)

  case object get {

    def objectId(id: String) = ???

  }

  def get(id: String) = ???

  def get(ids: Seq[String]) = ???

  def /(id: String) = get(id)

  def /(ids: Seq[String]) = get(ids)


}

object AlgoliaDsl extends AlgoliaDsl

trait SearchDsl {

  implicit object SearchDefinitionExecutable extends Executable[SearchDefinition, Search] {
    override def apply(client: AlgoliaClient, query: SearchDefinition): Future[Search] = {
      client request[Search] query.build()
    }
  }

}

trait IndexesDsl {

  implicit object IndexesDefinitionExecutable extends Executable[IndexesDefinition, Indexes] {
    override def apply(client: AlgoliaClient, query: IndexesDefinition): Future[Indexes] = {
      client request[Indexes] query.build()
    }
  }

}

trait IndexDsl {

  implicit object IndexDefinitionExecutable extends Executable[IndexingDefinition, Indexing] {
    override def apply(client: AlgoliaClient, query: IndexingDefinition): Future[Indexing] = {
      client request[Indexing] query.build()
    }
  }

}

trait ClearIndexDsl {

  implicit object ClearIndexDefinitionExecutable extends Executable[ClearIndexDefinition, Task] {
    override def apply(client: AlgoliaClient, query: ClearIndexDefinition): Future[Task] = {
      client request[Task] query.build()
    }
  }

}