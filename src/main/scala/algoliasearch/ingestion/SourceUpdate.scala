/** Ingestion API API powering the Data Ingestion connectors of Algolia.
  *
  * The version of the OpenAPI document: 1.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.ingestion

/** SourceUpdate
  *
  * @param authenticationID
  *   The authentication UUID.
  */
case class SourceUpdate(
    name: Option[String] = scala.None,
    input: Option[SourceUpdateInput] = scala.None,
    authenticationID: Option[String] = scala.None
)
