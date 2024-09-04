/** Insights API The Insights API lets you collect events related to your search and discovery experience. Events
  * represent user interactions with your app or website. They unlock powerful features, such as recommendations,
  * personalization, smarter search results, and analytics that help you optimize your user experience. ## Client
  * libraries Use Algolia's API clients, libraries, and integrations to collect events from your UI and send them to the
  * Insights API. See: [Algolia's
  * ecosystem](https://www.algolia.com/doc/guides/getting-started/how-algolia-works/in-depth/ecosystem/) ## Base URLs
  * The base URLs for making requests to the Insights API are: - `https://insights.us.algolia.io` -
  * `https://insights.de.algolia.io` - `https//insights.algolia.io` (routes requests to the closest of the above
  * servers, based on your geographical location) **All requests must use HTTPS.** ## Authentication To authenticate
  * your API requests, add these headers: - `x-algolia-application-id`. Your Algolia application ID. -
  * `x-algolia-api-key`. An API key with the necessary permissions to make the request. The required access control list
  * (ACL) to make a request is listed in each endpoint's reference. You can find your application ID and API key in the
  * [Algolia dashboard](https://dashboard.algolia.com/account). ## Request format Request bodies must be JSON objects.
  * ## Response status and errors Response bodies are JSON objects. Deleting a user token returns an empty response body
  * with rate-limiting information as headers. Successful responses return a `2xx` status. Client errors return a `4xx`
  * status. Server errors are indicated by a `5xx` status. Error responses have a `message` property with more
  * information. The Insights API doesn't validate if the event parameters such as `indexName`, `objectIDs`, or
  * `userToken`, correspond to anything in the Search API. It justs checks if they're formatted correctly. Check the
  * [Events](https://dashboard.algolia.com/events/health) health section, whether your events can be used for Algolia
  * features such as Analytics, or Dynamic Re-Ranking. ## Version The current version of the Insights API is version 1,
  * as indicated by the `/1/` in each endpoint's URL.
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.insights

import algoliasearch.insights.ConversionEvent._
import algoliasearch.insights.PurchaseEvent._

/** Use this event to track when users make a purchase after a previous Algolia request. If you're building your
  * category pages with Algolia, you'll also use this event.
  *
  * @param eventName
  *   Event name, up to 64 ASCII characters. Consider naming events consistently—for example, by adopting Segment's
  *   [object-action](https://segment.com/academy/collecting-data/naming-conventions-for-clean-data/#the-object-action-framework)
  *   framework.
  * @param index
  *   Index name (case-sensitive) to which the event's items belong.
  * @param objectIDs
  *   Object IDs of the records that are part of the event.
  * @param userToken
  *   Anonymous or pseudonymous user identifier. Don't use personally identifiable information in user tokens. For more
  *   information, see [User token](https://www.algolia.com/doc/guides/sending-events/concepts/usertoken/).
  * @param authenticatedUserToken
  *   Identifier for authenticated users. When the user signs in, you can get an identifier from your system and send it
  *   as `authenticatedUserToken`. This lets you keep using the `userToken` from before the user signed in, while
  *   providing a reliable way to identify users across sessions. Don't use personally identifiable information in user
  *   tokens. For more information, see [User
  *   token](https://www.algolia.com/doc/guides/sending-events/concepts/usertoken/).
  * @param currency
  *   Three-letter [currency code](https://www.iso.org/iso-4217-currency-codes.html).
  * @param objectData
  *   Extra information about the records involved in a purchase or add-to-cart events. If provided, it must be the same
  *   length as `objectIDs`.
  * @param timestamp
  *   Timestamp of the event, measured in milliseconds since the Unix epoch. By default, the Insights API uses the time
  *   it receives an event as its timestamp.
  */
case class PurchasedObjectIDsAfterSearch(
    eventName: String,
    eventType: ConversionEvent,
    eventSubtype: PurchaseEvent,
    index: String,
    objectIDs: Seq[String],
    userToken: String,
    authenticatedUserToken: Option[String] = scala.None,
    currency: Option[String] = scala.None,
    objectData: Seq[ObjectDataAfterSearch],
    timestamp: Option[Long] = scala.None,
    value: Option[Value] = scala.None
) extends EventsItemsTrait
