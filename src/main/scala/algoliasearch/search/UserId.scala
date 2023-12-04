/** Search API Use the Search REST API to manage your data (indices and records), implement search, and improve
  * relevance (with Rules, synonyms, and language dictionaries). Although Algolia provides a REST API, you should use
  * the official open source API [clients, libraries, and
  * tools](https://www.algolia.com/doc/guides/getting-started/how-algolia-works/in-depth/ecosystem/) instead. There's no
  * [SLA](https://www.algolia.com/policies/sla/) if you use the REST API directly.
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.search

/** Unique user ID.
  *
  * @param userID
  *   userID of the user.
  * @param clusterName
  *   Cluster to which the user is assigned.
  * @param nbRecords
  *   Number of records belonging to the user.
  * @param dataSize
  *   Data size used by the user.
  */
case class UserId(
    userID: String,
    clusterName: String,
    nbRecords: Int,
    dataSize: Int
)
