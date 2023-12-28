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

/** ClickThroughRateEvent
  *
  * @param rate
  *   [Click-through rate
  *   (CTR)](https://www.algolia.com/doc/guides/search-analytics/concepts/metrics/#click-through-rate).
  * @param clickCount
  *   Number of click events.
  * @param trackedSearchCount
  *   Number of tracked searches. This is the number of search requests where the `clickAnalytics` parameter is `true`.
  * @param date
  *   Date of the event in the format YYYY-MM-DD.
  */
case class ClickThroughRateEvent(
    rate: Double,
    clickCount: Int,
    trackedSearchCount: Int,
    date: String
)