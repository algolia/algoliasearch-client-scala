package algolia.definitions

import algolia.http.{GET, HttpPayload}
import algolia.responses.Indexes
import algolia.{AlgoliaClient, Executable}

import scala.concurrent.Future


class IndexesDefinition extends Definition {

  override private[algolia] def build() = HttpPayload(GET, Seq("1", "indexes"))

}

trait IndexesDsl {

  implicit object IndexesDefinitionExecutable extends Executable[IndexesDefinition, Indexes] {
    override def apply(client: AlgoliaClient, query: IndexesDefinition): Future[Indexes] = {
      client request[Indexes] query.build()
    }
  }

}

