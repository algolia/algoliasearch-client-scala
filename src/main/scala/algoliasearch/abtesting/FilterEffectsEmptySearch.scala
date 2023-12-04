/** A/B Testing API API powering the A/B Testing feature of Algolia.
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.abtesting

/** Empty searches removed from the A/B test as a result of configuration settings.
  *
  * @param usersCount
  *   Number of users removed from the A/B test.
  * @param trackedSearchesCount
  *   Number of tracked searches removed from the A/B test.
  */
case class FilterEffectsEmptySearch(
    usersCount: Option[Int] = scala.None,
    trackedSearchesCount: Option[Int] = scala.None
)
