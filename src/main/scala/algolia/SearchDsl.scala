package algolia

import algolia.definitions.{SearchDefinition, IndexesDefinition}
import algolia.responses.{Search, Indexes}

import scala.concurrent.Future

trait SearchDsl {

  implicit object SearchDefinitionExecutable extends Executable[SearchDefinition, Search] {
    override def apply(client: AlgoliaClient, query: SearchDefinition): Future[Search] = {
      client search query
    }
  }

}

trait IndexDsl {

  implicit object IndexesDefinitionExecutable extends Executable[IndexesDefinition, Indexes] {
    override def apply(client: AlgoliaClient, query: IndexesDefinition): Future[Indexes] = {
      client get[Indexes] query.build()
    }
  }

}