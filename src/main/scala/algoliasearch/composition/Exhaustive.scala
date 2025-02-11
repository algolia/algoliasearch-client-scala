/** Composition API (beta) The Algolia Composition API lets you run search requests on your Compositions. ## Client
  * libraries Use Algolia's API clients and libraries to reliably integrate Algolia's APIs with your apps. The official
  * API clients are covered by Algolia's [Service Level Agreement](https://www.algolia.com/policies/sla/). See:
  * [Algolia's ecosystem](https://www.algolia.com/doc/guides/getting-started/how-algolia-works/in-depth/ecosystem/) ##
  * Base URLs The base URLs for requests to the Composition API are: - `https://{APPLICATION_ID}.algolia.net` -
  * `https://{APPLICATION_ID}-dsn.algolia.net`. If your subscription includes a [Distributed Search
  * Network](https://dashboard.algolia.com/infra), this ensures that requests are sent to servers closest to users. Both
  * URLs provide high availability by distributing requests with load balancing. **All requests must use HTTPS.** ##
  * Retry strategy To guarantee high availability, implement a retry strategy for all API requests using the URLs of
  * your servers as fallbacks: - `https://{APPLICATION_ID}-1.algolianet.com` -
  * `https://{APPLICATION_ID}-2.algolianet.com` - `https://{APPLICATION_ID}-3.algolianet.com` These URLs use a different
  * DNS provider than the primary URLs. You should randomize this list to ensure an even load across the three servers.
  * All Algolia API clients implement this retry strategy. ## Authentication To authenticate your API requests, add
  * these headers: - `x-algolia-application-id`. Your Algolia application ID. - `x-algolia-api-key`. An API key with the
  * necessary permissions to make the request. The required access control list (ACL) to make a request is listed in
  * each endpoint's reference. You can find your application ID and API key in the [Algolia
  * dashboard](https://dashboard.algolia.com/account). ## Request format Depending on the endpoint, request bodies are
  * either JSON objects or arrays of JSON objects, ## Parameters Parameters are passed as query parameters for GET and
  * DELETE requests, and in the request body for POST and PUT requests. Query parameters must be
  * [URL-encoded](https://developer.mozilla.org/en-US/docs/Glossary/Percent-encoding). Non-ASCII characters must be
  * UTF-8 encoded. Plus characters (`+`) are interpreted as spaces. Arrays as query parameters must be one of: - A
  * comma-separated string: `attributesToRetrieve=title,description` - A URL-encoded JSON array:
  * `attributesToRetrieve=%5B%22title%22,%22description%22%D` ## Response status and errors The Composition API returns
  * JSON responses. Since JSON doesn't guarantee any specific ordering, don't rely on the order of attributes in the API
  * response. Successful responses return a `2xx` status. Client errors return a `4xx` status. Server errors are
  * indicated by a `5xx` status. Error responses have a `message` property with more information. ## Version The current
  * version of the Composition API is version 1, as indicated by the `/1/` in each endpoint's URL.
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.composition

/** Whether certain properties of the search response are calculated exhaustive (exact) or approximated.
  *
  * @param facetsCount
  *   Whether the facet count is exhaustive (`true`) or approximate (`false`). See the [related
  *   discussion](https://support.algolia.com/hc/en-us/articles/4406975248145-Why-are-my-facet-and-hit-counts-not-accurate-).
  * @param facetValues
  *   The value is `false` if not all facet values are retrieved.
  * @param nbHits
  *   Whether the `nbHits` is exhaustive (`true`) or approximate (`false`). When the query takes more than 50ms to be
  *   processed, the engine makes an approximation. This can happen when using complex filters on millions of records,
  *   when typo-tolerance was not exhaustive, or when enough hits have been retrieved (for example, after the engine
  *   finds 10,000 exact matches). `nbHits` is reported as non-exhaustive whenever an approximation is made, even if the
  *   approximation didn’t, in the end, impact the exhaustivity of the query.
  * @param rulesMatch
  *   Rules matching exhaustivity. The value is `false` if rules were enable for this query, and could not be fully
  *   processed due a timeout. This is generally caused by the number of alternatives (such as typos) which is too
  *   large.
  * @param typo
  *   Whether the typo search was exhaustive (`true`) or approximate (`false`). An approximation is done when the typo
  *   search query part takes more than 10% of the query budget (ie. 5ms by default) to be processed (this can happen
  *   when a lot of typo alternatives exist for the query). This field will not be included when typo-tolerance is
  *   entirely disabled.
  */
case class Exhaustive(
    facetsCount: Option[Boolean] = scala.None,
    facetValues: Option[Boolean] = scala.None,
    nbHits: Option[Boolean] = scala.None,
    rulesMatch: Option[Boolean] = scala.None,
    typo: Option[Boolean] = scala.None
)
