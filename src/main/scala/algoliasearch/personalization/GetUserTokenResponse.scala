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
  * responses have a `message` property with more information. ## Rate limiting When making requests to the
  * Personalization API, you are limited to 40 API calls per second per application. The following headers provide
  * information about your current limit: - `x-ratelimit-limit`: The number of requests allowed every second. -
  * `x-ratelimit-remaining`: The number of requests remaining in the current second period. - `x-ratelimit-reset`: [Unix
  * timestamp](https://www.unixtimestamp.com/) of the next time period. ## Version The current version of the
  * Personalization API is version 1, as indicated by the `/1/` in each endpoint's URL.
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.personalization

/** GetUserTokenResponse
  *
  * @param userToken
  *   Unique pseudonymous or anonymous user identifier. This helps with analytics and click and conversion events. For
  *   more information, see [user token](https://www.algolia.com/doc/guides/sending-events/concepts/usertoken/).
  * @param lastEventAt
  *   Date and time of the last event from this user, in RFC 3339 format.
  * @param scores
  *   Scores for different facet values. Scores represent the user affinity for a user profile towards specific facet
  *   values, given the personalization strategy and past events.
  */
case class GetUserTokenResponse(
    userToken: String,
    lastEventAt: String,
    scores: Any
)
