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

/** CompositionsSearchResponse
  */
case class CompositionsSearchResponse(
    run: Seq[CompositionRunSearchResponse],
    additionalProperties: Option[List[JField]] = None
)

class CompositionsSearchResponseSerializer extends Serializer[CompositionsSearchResponse] {

  override def deserialize(implicit
      format: Formats
  ): PartialFunction[(TypeInfo, JValue), CompositionsSearchResponse] = {
    case (TypeInfo(clazz, _), json) if clazz == classOf[CompositionsSearchResponse] =>
      json match {
        case jobject: JObject =>
          val formats = format - this
          val mf = manifest[CompositionsSearchResponse]
          val obj = Extraction.extract[CompositionsSearchResponse](jobject)(formats, mf)

          val fields = Set("run")
          val additionalProperties = jobject removeField {
            case (name, _) if fields.contains(name) => true
            case _                                  => false
          }
          additionalProperties.values match {
            case JObject(fieldsList) => obj copy (additionalProperties = Some(fieldsList))
            case _                   => obj
          }
        case _ => throw new IllegalArgumentException(s"Can't deserialize $json as CompositionsSearchResponse")
      }
  }

  override def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case value: CompositionsSearchResponse =>
      val formats = format - this // remove current serializer from formats to avoid stackoverflow
      value.additionalProperties match {
        case Some(fields) =>
          Extraction.decompose(value.copy(additionalProperties = None))(formats) merge JObject(fields)
        case None => Extraction.decompose(value)(formats)
      }
  }
}