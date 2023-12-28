/** Ingestion API API powering the Data Ingestion connectors of Algolia.
  *
  * The version of the OpenAPI document: 1.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.ingestion

import org.json4s._

/** AuthInput
  */
sealed trait AuthInput

trait AuthInputTrait extends AuthInput

object AuthInput {}

object AuthInputSerializer extends Serializer[AuthInput] {
  override def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), AuthInput] = {

    case (TypeInfo(clazz, _), json) if clazz == classOf[AuthInput] =>
      json match {
        case value: JObject => Extraction.extract[AuthGoogleServiceAccount](value)
        case value: JObject => Extraction.extract[AuthBasic](value)
        case value: JObject => Extraction.extract[AuthAPIKey](value)
        case value: JObject => Extraction.extract[AuthOAuth](value)
        case value: JObject => Extraction.extract[AuthAlgolia](value)
        case _              => throw new MappingException("Can't convert " + json + " to AuthInput")
      }
  }

  override def serialize(implicit format: Formats): PartialFunction[Any, JValue] = { case value: AuthInput =>
    value match {
      case value: AuthGoogleServiceAccount => Extraction.decompose(value)(format - this)
      case value: AuthBasic                => Extraction.decompose(value)(format - this)
      case value: AuthAPIKey               => Extraction.decompose(value)(format - this)
      case value: AuthOAuth                => Extraction.decompose(value)(format - this)
      case value: AuthAlgolia              => Extraction.decompose(value)(format - this)
    }
  }
}