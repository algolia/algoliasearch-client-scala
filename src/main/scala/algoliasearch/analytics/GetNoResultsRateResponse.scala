/** Analytics API The Analytics API lets you review your search, and click and conversion analytics. > **Note**: The API
  * key in the `X-Algolia-API-Key` header requires the [`analytics`
  * ACL](https://www.algolia.com/doc/guides/security/api-keys/#access-control-list-acl).
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.analytics

/** GetNoResultsRateResponse
  *
  * @param rate
  *   [Click-through rate
  *   (CTR)](https://www.algolia.com/doc/guides/search-analytics/concepts/metrics/#click-through-rate).
  * @param count
  *   Number of occurrences.
  * @param noResultCount
  *   Number of occurrences.
  * @param dates
  *   Overall count of searches without results plus a daily breakdown.
  */
case class GetNoResultsRateResponse(
    rate: Double,
    count: Int,
    noResultCount: Int,
    dates: Seq[NoResultsRateEvent]
)