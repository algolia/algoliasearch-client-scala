package algolia.definitions

import algolia.HttpPayload

class IndexesDefinition extends Definition {

  override private[algolia] def build() = HttpPayload(Seq("1", "indexes"))

}
