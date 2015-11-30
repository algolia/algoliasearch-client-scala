package algolia.definitions

import algolia.http.{GET, HttpPayload}
import algolia.responses.Indexes
import algolia.{AlgoliaClient, Executable}

import scala.concurrent.Future


class ListIndexesDefinition extends Definition {

  override private[algolia] def build() = HttpPayload(GET, Seq("1", "indexes"))

}

trait ListIndexesDsl {

  implicit object IndexesDefinitionExecutable extends Executable[ListIndexesDefinition, Indexes] {
    override def apply(client: AlgoliaClient, query: ListIndexesDefinition): Future[Indexes] = {
      client request[Indexes] query.build()
    }
  }

}

