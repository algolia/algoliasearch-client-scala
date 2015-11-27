package algolia

import algolia.definitions.{IndexDefinition, SearchDefinition, IndexesDefinition}
import algolia.responses.{Search, Indexes}

import scala.concurrent.Future

trait SearchDsl {

  implicit object SearchDefinitionExecutable extends Executable[SearchDefinition, Search] {
    override def apply(client: AlgoliaClient, query: SearchDefinition): Future[Search] = {
      client post[Search] query.build()
    }
  }

}

trait IndexesDsl {

  implicit object IndexesDefinitionExecutable extends Executable[IndexesDefinition, Indexes] {
    override def apply(client: AlgoliaClient, query: IndexesDefinition): Future[Indexes] = {
      client get[Indexes] query.build()
    }
  }

}

trait IndexDsl {

  implicit object IndexDefinitionExecutable extends Executable[IndexDefinition, Indexes] {
    override def apply(client: AlgoliaClient, query: IndexDefinition): Future[Indexes] = {
      client post[Indexes] query.build()
    }
  }

}