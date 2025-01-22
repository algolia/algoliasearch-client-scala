/** Composition API Composition API.
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.composition

import org.json4s.MonadicJValue.jvalueToMonadic
import org.json4s.{Extraction, Formats, JField, JObject, JValue, Serializer, TypeInfo}

/** Search result. A hit is a record from your index, augmented with special attributes for highlighting, snippeting,
  * and ranking.
  *
  * @param objectID
  *   Unique record identifier.
  * @param highlightResult
  *   Surround words that match the query with HTML tags for highlighting.
  * @param snippetResult
  *   Snippets that show the context around a matching search query.
  */
case class Hit(
    objectID: String,
    highlightResult: Option[Map[String, HighlightResult]] = scala.None,
    snippetResult: Option[Map[String, SnippetResult]] = scala.None,
    rankingInfo: Option[HitRankingInfo] = scala.None,
    distinctSeqID: Option[Int] = scala.None,
    additionalProperties: Option[List[JField]] = None
)

class HitSerializer extends Serializer[Hit] {

  override def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), Hit] = {
    case (TypeInfo(clazz, _), json) if clazz == classOf[Hit] =>
      json match {
        case jobject: JObject =>
          val formats = format - this
          val mf = manifest[Hit]
          val obj = Extraction.extract[Hit](jobject)(formats, mf)

          val fields = Set("objectID", "highlightResult", "snippetResult", "rankingInfo", "distinctSeqID")
          val additionalProperties = jobject removeField {
            case (name, _) if fields.contains(name) => true
            case _                                  => false
          }
          additionalProperties.values match {
            case JObject(fieldsList) => obj copy (additionalProperties = Some(fieldsList))
            case _                   => obj
          }
        case _ => throw new IllegalArgumentException(s"Can't deserialize $json as Hit")
      }
  }

  override def serialize(implicit format: Formats): PartialFunction[Any, JValue] = { case value: Hit =>
    val formats = format - this // remove current serializer from formats to avoid stackoverflow
    value.additionalProperties match {
      case Some(fields) => Extraction.decompose(value.copy(additionalProperties = None))(formats) merge JObject(fields)
      case None         => Extraction.decompose(value)(formats)
    }
  }
}
