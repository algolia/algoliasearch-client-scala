package algolia.definitions

trait Definition[RESULT] {
  private[algolia] def build(): RESULT
}
