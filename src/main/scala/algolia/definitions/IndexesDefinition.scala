package algolia.definitions

import algolia.http.{GET, HttpPayload}


class IndexesDefinition extends Definition {

  override private[algolia] def build() = HttpPayload(GET, Seq("1", "indexes"))

}
