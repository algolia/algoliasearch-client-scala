/** Ingestion API API powering the Data Ingestion connectors of Algolia.
  *
  * The version of the OpenAPI document: 1.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.ingestion

/** Authentication input to connect to a Google service (e.g. BigQuery).
  *
  * @param clientEmail
  *   Email address of the Service Account.
  * @param privateKey
  *   Private key of the Service Account.
  */
case class AuthGoogleServiceAccountPartial(
    clientEmail: Option[String] = scala.None,
    privateKey: Option[String] = scala.None
) extends AuthInputPartialTrait
