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

/** TopCountry
  *
  * @param country
  *   Country.
  * @param count
  *   Number of occurrences.
  */
case class TopCountry(
    country: String,
    count: Int
)
