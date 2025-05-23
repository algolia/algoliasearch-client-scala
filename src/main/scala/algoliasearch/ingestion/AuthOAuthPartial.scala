/** Ingestion API The Ingestion API lets you connect third-party services and platforms with Algolia and schedule tasks
  * to ingest your data. The Ingestion API powers the no-code [data
  * connectors](https://dashboard.algolia.com/connectors). ## Base URLs The base URLs for requests to the Ingestion API
  * are: - `https://data.us.algolia.com` - `https://data.eu.algolia.com` Use the URL that matches your [analytics
  * region](https://dashboard.algolia.com/account/infrastructure/analytics). **All requests must use HTTPS.** ##
  * Authentication To authenticate your API requests, add these headers: - `x-algolia-application-id`. Your Algolia
  * application ID. - `x-algolia-api-key`. An API key with the necessary permissions to make the request. The required
  * access control list (ACL) to make a request is listed in each endpoint's reference. You can find your application ID
  * and API key in the [Algolia dashboard](https://dashboard.algolia.com/account). ## Request format Request bodies must
  * be JSON objects. ## Response status and errors Response bodies are JSON objects. Successful responses return a `2xx`
  * status. Client errors return a `4xx` status. Server errors are indicated by a `5xx` status. Error responses have a
  * `message` property with more information. ## Version The current version of the Ingestion API is version 1, as
  * indicated by the `/1/` in each endpoint's URL.
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.ingestion

import org.json4s._

/** Credentials for authenticating with OAuth 2.0.
  *
  * @param url
  *   URL for the OAuth endpoint.
  * @param clientId
  *   Client ID.
  * @param clientSecret
  *   Client secret. This field is `null` in the API response.
  * @param scope
  *   OAuth scope.
  */
case class AuthOAuthPartial(
    url: Option[String] = scala.None,
    clientId /* client_id */: Option[String] = scala.None,
    clientSecret /* client_secret */: Option[String] = scala.None,
    scope: Option[String] = scala.None
) extends AuthInputPartialTrait

class AuthOAuthPartialSerializer extends Serializer[AuthOAuthPartial] {

  private val renamedFields = Map[String, String](
    "client_id" -> "clientId",
    "client_secret" -> "clientSecret"
  )
  override def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), AuthOAuthPartial] = {
    case (TypeInfo(clazz, _), json) if clazz == classOf[AuthOAuthPartial] =>
      json match {
        case jobject: JObject =>
          // Rename fields from JSON to Scala
          val renamedObject = JObject(
            jobject.obj.map { field =>
              renamedFields.get(field._1).map(JField(_, field._2)).getOrElse(field)
            }
          )
          val formats = format - this
          val mf = manifest[AuthOAuthPartial]
          Extraction.extract[AuthOAuthPartial](renamedObject)(formats, mf)

        case _ => throw new IllegalArgumentException(s"Can't deserialize $json as AuthOAuthPartial")
      }
  }

  override def serialize(implicit format: Formats): PartialFunction[Any, JValue] = { case value: AuthOAuthPartial =>
    val formats = format - this // remove current serializer from formats to avoid stackoverflow
    val baseObj = Extraction.decompose(value)(formats)
    baseObj transformField {
      case JField(name, value) if renamedFields.exists(_._2 == name) => (renamedFields.find(_._2 == name).get._1, value)
    }
  }
}
