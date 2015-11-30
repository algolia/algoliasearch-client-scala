package algolia.responses

import org.json4s.JsonAST.JObject

case class Get(private val res: JObject) extends ObjectID {

  implicit val formats = org.json4s.DefaultFormats

  override val objectID: String = (res \ "objectID").extractOpt[String].get

  def as[T <: AnyRef : Manifest]: T = res.extract[T]
}
