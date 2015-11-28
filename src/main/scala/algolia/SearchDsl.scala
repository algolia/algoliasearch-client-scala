package algolia

import algolia.definitions.{IndexesDefinition, IndexingDefinition, SearchDefinition}
import algolia.responses.{Indexes, Indexing, Search}

import scala.concurrent.Future

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