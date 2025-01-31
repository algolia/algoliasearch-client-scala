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

/** SearchHits
  *
  * @param hits
  *   Search results (hits). Hits are records from your index that match the search criteria, augmented with additional
  *   attributes, such as, for highlighting.
  * @param query
  *   Search query.
  * @param params
  *   URL-encoded string of all search parameters.
  */
case class SearchHits(
    hits: Seq[Hit],
    query: String,
    params: String,
    additionalProperties: Option[List[JField]] = None
)

class SearchHitsSerializer extends Serializer[SearchHits] {

  override def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), SearchHits] = {
    case (TypeInfo(clazz, _), json) if clazz == classOf[SearchHits] =>
      json match {
        case jobject: JObject =>
          val formats = format - this
          val mf = manifest[SearchHits]
          val obj = Extraction.extract[SearchHits](jobject)(formats, mf)

          val fields = Set("hits", "query", "params")
          val additionalProperties = jobject removeField {
            case (name, _) if fields.contains(name) => true
            case _                                  => false
          }
          additionalProperties.values match {
            case JObject(fieldsList) => obj copy (additionalProperties = Some(fieldsList))
            case _                   => obj
          }
        case _ => throw new IllegalArgumentException(s"Can't deserialize $json as SearchHits")
      }
  }

  override def serialize(implicit format: Formats): PartialFunction[Any, JValue] = { case value: SearchHits =>
    val formats = format - this // remove current serializer from formats to avoid stackoverflow
    value.additionalProperties match {
      case Some(fields) => Extraction.decompose(value.copy(additionalProperties = None))(formats) merge JObject(fields)
      case None         => Extraction.decompose(value)(formats)
    }
  }
}
