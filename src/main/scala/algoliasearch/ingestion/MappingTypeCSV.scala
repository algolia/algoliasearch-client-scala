/** Ingestion API The Ingestion API lets you connect third-party services and platforms with Algolia and schedule tasks
  * to ingest your data. The Ingestion API powers the no-code [data
  * connectors](https://dashboard.algolia.com/connectors). ## Base URLs The base URLs for requests to the Ingestion API
  * are: - `https://data.us.algolia.com` - `https://data.eu.algolia.com` Use the URL that matches your [analytics
  * region](https://dashboard.algolia.com/account/infrastructure/analytics). **All requests must use HTTPS.** ##
  * Authentication To authenticate your API requests, add these headers: - `x-algolia-application-id`. Your Algolia
  * application ID. - `x-algolia-api-key`. An API key with the necessary permissions to make the request. The required
  * access control list (ACL) to make a request is listed in each endpoint's reference. You can find your application ID
  * and API key in the [Algolia dashboard](https://dashboard.algolia.com/account). ## Request format Request bodies must
  * be JSON objects. ## Response status and errors Response bodies are JSON objects. Successful responses return a `2xx`
  * status. Client errors return a `4xx` status. Server errors are indicated by a `5xx` status. Error responses have a
  * `message` property with more information. ## Version The current version of the Ingestion API is version 1, as
  * indicated by the `/1/` in each endpoint's URL.
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.ingestion

import org.json4s._

sealed trait MappingTypeCSV

/** MappingTypeCSV enumeration
  */
object MappingTypeCSV {
  case object String extends MappingTypeCSV {
    override def toString = "string"
  }
  case object Integer extends MappingTypeCSV {
    override def toString = "integer"
  }
  case object Float extends MappingTypeCSV {
    override def toString = "float"
  }
  case object Boolean extends MappingTypeCSV {
    override def toString = "boolean"
  }
  case object Json extends MappingTypeCSV {
    override def toString = "json"
  }
  val values: Seq[MappingTypeCSV] = Seq(String, Integer, Float, Boolean, Json)

  def withName(name: String): MappingTypeCSV = MappingTypeCSV.values
    .find(_.toString == name)
    .getOrElse(throw new MappingException(s"Unknown MappingTypeCSV value: $name"))
}

class MappingTypeCSVSerializer
    extends CustomSerializer[MappingTypeCSV](_ =>
      (
        {
          case JString(value) => MappingTypeCSV.withName(value)
          case JNull          => null
        },
        { case value: MappingTypeCSV =>
          JString(value.toString)
        }
      )
    )
