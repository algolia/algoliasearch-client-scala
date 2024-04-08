/** Ingestion API The Ingestion API lets you connect third-party services and platforms with Algolia and schedule tasks
  * to ingest your data. The Ingestion API powers the no-code [data
  * connectors](https://dashboard.algolia.com/connectors). ## Base URLs The base URLs for requests to the Ingestion API
  * are: - `https://data.us.algolia.com` - `https://data.eu.algolia.com` Use the URL that matches your [analytics
  * region](https://dashboard.algolia.com/account/infrastructure/analytics). **All requests must use HTTPS.** ##
  * Authentication To authenticate your API requests, add these headers: - `x-algolia-application-id`. Your Algolia
  * application ID. - `x-algolia-api-key`. An API key with the necessary permissions to make the request. The required
  * access control list (ACL) to make a request is listed in each endpoint's reference. You can find your application ID
  * and API key in the [Algolia dashboard](https://dashboard.algolia.com/account). ## Request format Request bodies must
  * be JSON objects. ## Response status and errors Response bodies are JSON objects. Deleting a user token returns an
  * empty response body with rate-limiting information as headers. Successful responses return a `2xx` status. Client
  * errors return a `4xx` status. Server errors are indicated by a `5xx` status. Error responses have a `message`
  * property with more information. The Insights API doesn't validate if the event parameters such as `indexName`,
  * `objectIDs`, or `userToken`, correspond to anything in the Search API. It justs checks if they're formatted
  * correctly. Check the [Events](https://dashboard.algolia.com/events/health) health section, whether your events can
  * be used for Algolia features such as Analytics, or Dynamic Re-Ranking. ## Version The current version of the
  * Insights API is version 1, as indicated by the `/1/` in each endpoint's URL.
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.ingestion

import algoliasearch.ingestion.RecordType._

/** DestinationIndexName
  *
  * @param indexName
  *   Algolia index name.
  * @param attributesToExclude
  *   Attributes from your source to exclude from Algolia records. Not all your data attributes will be useful for
  *   searching. Keeping your Algolia records small increases indexing and search performance. - Exclude nested
  *   attributes with `.` notation. For example, `foo.bar` indexes the `foo` attribute and all its children **except**
  *   the `bar` attribute. - Exclude attributes from arrays with `[i]`, where `i` is the index of the array element. For
  *   example, `foo.[0].bar` only excludes the `bar` attribute from the first element of the `foo` array, but indexes
  *   the complete `foo` attribute for all other elements. Use `*` as wildcard: `foo.[*].bar` excludes `bar` from all
  *   elements of the `foo` array.
  */
case class DestinationIndexName(
    indexName: String,
    recordType: Option[RecordType] = scala.None,
    attributesToExclude: Option[Seq[String]] = scala.None
) extends DestinationInputTrait
