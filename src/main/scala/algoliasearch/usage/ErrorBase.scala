/** Usage API The Usage API gives you access to statistics about all requests made to your Algolia applications. # Base
  * URL The base URL for requests to the Usage API is: - `https://usage.algolia.com` **All requests must use HTTPS.** #
  * Authentication To authenticate your API requests, add these headers: <dl>
  * <dt><code>x-algolia-application-id</code></dt> <dd>Your Algolia application ID.</dd>
  * <dt><code>x-algolia-api-key</code></dt> <dd>The Usage API key.</dd> </dl> You can find your application ID and Usage
  * API key in the [Algolia dashboard](https://dashboard.algolia.com/account). # Response status and errors The Usage
  * API returns JSON responses. Since JSON doesn't guarantee any specific ordering, don't rely on the order of
  * attributes in the API response. Successful responses return a `2xx` status. Client errors return a `4xx` status.
  * Server errors are indicated by a `5xx` status. Error responses have a `message` property with more information. #
  * Version The current version of the Usage API is version 1, as indicated by the `/1/` in each endpoint's URL.
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.usage

import org.json4s.MonadicJValue.jvalueToMonadic
import org.json4s.{Extraction, Formats, JField, JObject, JValue, Serializer, TypeInfo}

/** Error.
  */
case class ErrorBase(
    message: Option[String] = scala.None,
    additionalProperties: Option[List[JField]] = None
)

class ErrorBaseSerializer extends Serializer[ErrorBase] {

  override def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), ErrorBase] = {
    case (TypeInfo(clazz, _), json) if clazz == classOf[ErrorBase] =>
      json match {
        case jobject: JObject =>
          val formats = format - this
          val mf = manifest[ErrorBase]
          val obj = Extraction.extract[ErrorBase](jobject)(formats, mf)

          val fields = Set("message")
          val additionalProperties = jobject removeField {
            case (name, _) if fields.contains(name) => true
            case _                                  => false
          }
          additionalProperties.values match {
            case JObject(fieldsList) => obj copy (additionalProperties = Some(fieldsList))
            case _                   => obj
          }
        case _ => throw new IllegalArgumentException(s"Can't deserialize $json as ErrorBase")
      }
  }

  override def serialize(implicit format: Formats): PartialFunction[Any, JValue] = { case value: ErrorBase =>
    val formats = format - this // remove current serializer from formats to avoid stackoverflow
    value.additionalProperties match {
      case Some(fields) => Extraction.decompose(value.copy(additionalProperties = None))(formats) merge JObject(fields)
      case None         => Extraction.decompose(value)(formats)
    }
  }
}
