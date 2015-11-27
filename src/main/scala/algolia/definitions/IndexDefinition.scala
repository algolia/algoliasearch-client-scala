package algolia.definitions

import algolia.{HttpPayload, Index}
import org.json4s.native.Serialization.write

case class IndexDefinition(index: Index,
                           objectId: Option[String] = None,
                           obj: Option[AnyRef] = None,
                           objects: Option[Seq[AnyRef]] = None,
                           objectsWithIds: Option[Map[String, AnyRef]] = None) extends Definition {


  def objectId(objectId: String): IndexDefinition =
    copy(index, objectId = Some(objectId), obj = obj)

  def document(objectId: String, obj: AnyRef): IndexDefinition =
    copy(index, objectId = Some(objectId), obj = Some(obj))

  def document(obj: AnyRef): IndexDefinition =
    copy(index, objectId = objectId, obj = Some(obj))

  def documents(objects: Seq[AnyRef]): IndexDefinition = ???

  //    copy(index, objects = Some(objects))

  def documents(objectsWithIds: Map[String, AnyRef]): IndexDefinition = ???

  //    copy(index, objectsWithIds = Some(objectsWithIds))

  implicit val formats = org.json4s.DefaultFormats

  override private[algolia] def build(): HttpPayload = {
    val body: Option[String] = obj.map(o => write(o))

    HttpPayload(Seq("1", "indexes", index.name) ++ objectId, body = body)
  }
}
