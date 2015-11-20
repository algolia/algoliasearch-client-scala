package algolia.definitions

import algolia.Index

case class IndexDefinition(index: Index) extends Definition[Seq[String]] {

  def objectId(objectId: String): IndexDefinition = this

  def document[T](id: String, obj: T): IndexDefinition = this

  def document[T](obj: T): IndexDefinition = this

  def documents[T](objects: Seq[T]): IndexDefinition = this

  def documents[T](objectsWithIds: Map[String, T]): IndexDefinition = this

  override private[algolia] def build(): Seq[String] = ???
}
