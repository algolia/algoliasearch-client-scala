package algolia

import algolia.definitions._
import algolia.responses.{Indexing, Indexes, Search}

import scala.concurrent.Future

trait AlgoliaDsl extends SearchDsl with IndexesDsl with IndexDsl {

  case object search {

    def into(index: Index): SearchDefinition = SearchDefinition(index)

    def into(index: String): SearchDefinition = into(Index(index))

    def in(index: String): SearchDefinition = into(index)

    def in(index: Index): SearchDefinition = into(index)

  }

  case object index {

    def into(index: Index): IndexingDefinition = IndexingDefinition(index)

    def into(index: String): IndexingDefinition = into(Index(index))

    def in(index: String): IndexingDefinition = into(index)

    def in(index: Index): IndexingDefinition = into(index)

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