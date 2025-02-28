/** Personalization API The Personalization API lets you access user profiles built from the personalization strategy.
  * ## Base URLs The base URLs for requests to the Personalization API are: - `https://personalization.us.algolia.com` -
  * `https://personalization.eu.algolia.com` Use the URL that matches your [analytics
  * region](https://dashboard.algolia.com/account/infrastructure/analytics). **All requests must use HTTPS.** ##
  * Authentication To authenticate your API requests, add these headers: - `x-algolia-application-id`. Your Algolia
  * application ID. - `x-algolia-api-key`. An API key with the necessary permissions to make the request. The required
  * access control list (ACL) to make a request is listed in each endpoint's reference. You can find your application ID
  * and API key in the [Algolia dashboard](https://dashboard.algolia.com/account). ## Request format Request bodies must
  * be JSON objects. ## Response status and errors The Personalization API returns JSON responses. Since JSON doesn't
  * guarantee any specific ordering, don't rely on the order of attributes in the API response. Successful responses
  * return a `2xx` status. Client errors return a `4xx` status. Server errors are indicated by a `5xx` status. Error
  * responses have a `message` property with more information. ## Version The current version of the Personalization API
  * is version 1, as indicated by the `/1/` in each endpoint's URL.
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.personalization

import org.json4s._

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
          additionalProperties match {
            case JObject(fieldsList) => obj copy (additionalProperties = Some(fieldsList))
            case _                   => obj
          }
        case _ => throw new IllegalArgumentException(s"Can't deserialize $json as ErrorBase")
      }
  }

  override def serialize(implicit format: Formats): PartialFunction[Any, JValue] = { case value: ErrorBase =>
    val formats = format - this // remove current serializer from formats to avoid stackoverflow
    val baseObj = Extraction.decompose(value.copy(additionalProperties = None))(formats)

    value.additionalProperties match {
      case Some(fields) => baseObj merge JObject(fields)
      case None         => baseObj
    }
  }
}
