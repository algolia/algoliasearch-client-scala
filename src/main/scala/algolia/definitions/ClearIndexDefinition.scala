package algolia.definitions

import algolia.http.{HttpPayload, POST}

case class ClearIndexDefinition(index: String) extends Definition {

  override private[algolia] def build(): HttpPayload = HttpPayload(POST, Seq("1", "indexes", index, "clear"))

}
