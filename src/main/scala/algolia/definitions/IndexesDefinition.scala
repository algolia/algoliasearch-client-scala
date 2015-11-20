package algolia.definitions

class IndexesDefinition extends Definition[Seq[String]] {

  override private[algolia] def build() = Seq("1", "indexes")

}
