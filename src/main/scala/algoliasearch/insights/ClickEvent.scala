/** Insights API The Insights API lets you collect events related to your search and discovery experience. Events
  * represent user interactions with your app or website. They unlock powerful features, such as recommendations,
  * personalization, smarter search results, and analytics that help you optimize your user experience. # Client
  * libraries Use Algolia's API clients, libraries, and integrations to collect events from your UI and send them to the
  * Insights API. See: [Algolia's
  * ecosystem](https://www.algolia.com/doc/guides/getting-started/how-algolia-works/in-depth/ecosystem/) # Base URLs The
  * base URLs for making requests to the Insights API are: - `https://insights.us.algolia.io`
  * (`https://insights.algolia.io` is an alias) - `https://insights.de.algolia.io` **All requests must use HTTPS.** #
  * Authentication To authenticate your API requests, add these headers: <dl>
  * <dt><code>x-algolia-application-id</code></dt> <dd>Your Algolia application ID.</dd>
  * <dt><code>x-algolia-api-key</code></dt> <dd> An API key with the necessary permissions to make the request. The
  * required access control list (ACL) to make a request is listed in each endpoint's reference. </dd> </dl> You can
  * find your application ID and API key in the [Algolia dashboard](https://dashboard.algolia.com/account). # Request
  * format Request bodies must be JSON objects. # Response status and errors Response bodies are JSON objects. Deleting
  * a user token returns an empty response body with rate-limiting information as headers. Successful responses return a
  * `2xx` status. Client errors return a `4xx` status. Server errors are indicated by a `5xx` status. Error responses
  * have a `message` property with more information. The Insights API doesn't validate if the event parameters such as
  * `indexName`, `objectIDs`, or `userToken`, correspond to anything in the Search API. It justs checks if they're
  * formatted correctly. Check the [Events](https://dashboard.algolia.com/events/health) health section, whether your
  * events can be used for Algolia features such as Analytics, or Dynamic Re-Ranking. # Version The current version of
  * the Insights API is version 1, as indicated by the `/1/` in each endpoint's URL.
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.insights

import org.json4s._

sealed trait ClickEvent

/** ClickEvent enumeration
  */
object ClickEvent {
  case object Click extends ClickEvent {
    override def toString = "click"
  }
  val values: Seq[ClickEvent] = Seq(Click)

  def withName(name: String): ClickEvent = ClickEvent.values
    .find(_.toString == name)
    .getOrElse(throw new MappingException(s"Unknown ClickEvent value: $name"))
}

class ClickEventSerializer
    extends CustomSerializer[ClickEvent](_ =>
      (
        {
          case JString(value) => ClickEvent.withName(value)
          case JNull          => null
        },
        { case value: ClickEvent =>
          JString(value.toString)
        }
      )
    )
