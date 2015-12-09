package algolia.definitions

import org.json4s._

trait BatchOperationUtils {

  implicit val formats: Formats

  protected def hasObjectId(obj: AnyRef): (Boolean, JValue) = {
    val json = Extraction.decompose(obj)
    json \ "objectID" match {
      case JNothing => (false, json)
      case _ => (true, json)
    }
  }

  protected def addObjectId(obj: AnyRef, objectId: String): JValue = {
    val json: JValue = Extraction.decompose(obj)

    json \ "objectID" match {
      case JNothing => JObject(JField("objectID", JString(objectId))) merge json
      case _ => json
    }
  }

}
