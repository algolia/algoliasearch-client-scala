/** Search API The Algolia Search API lets you search, configure, and manage your indices and records. ## Client
  * libraries Use Algolia's API clients and libraries to reliably integrate Algolia's APIs with your apps. The official
  * API clients are covered by Algolia's [Service Level Agreement](https://www.algolia.com/policies/sla/). See:
  * [Algolia's ecosystem](https://www.algolia.com/doc/guides/getting-started/how-algolia-works/in-depth/ecosystem/) ##
  * Base URLs The base URLs for requests to the Search API are: - `https://{APPLICATION_ID}.algolia.net` -
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
  * `attributesToRetrieve=%5B%22title%22,%22description%22%D` ## Response status and errors The Search API returns JSON
  * responses. Since JSON doesn't guarantee any specific ordering, don't rely on the order of attributes in the API
  * response. Successful responses return a `2xx` status. Client errors return a `4xx` status. Server errors are
  * indicated by a `5xx` status. Error responses have a `message` property with more information. ## Version The current
  * version of the Search API is version 1, as indicated by the `/1/` in each endpoint's URL.
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.search

import org.json4s._

/** Filters to promote or demote records in the search results. Optional filters work like facet filters, but they don't
  * exclude records from the search results. Records that match the optional filter rank before records that don't
  * match. If you're using a negative filter `facet:-value`, matching records rank after records that don't match. -
  * Optional filters don't work on virtual replicas. - Optional filters are applied _after_ sort-by attributes. -
  * Optional filters are applied _before_ custom ranking attributes (in the default
  * [ranking](https://www.algolia.com/doc/guides/managing-results/relevance-overview/in-depth/ranking-criteria/)). -
  * Optional filters don't work with numeric attributes.
  */
sealed trait OptionalFilters

object OptionalFilters {

  case class SeqOfOptionalFilters(value: Seq[OptionalFilters]) extends OptionalFilters

  case class StringValue(value: String) extends OptionalFilters

  def apply(value: Seq[OptionalFilters]): OptionalFilters = {
    OptionalFilters.SeqOfOptionalFilters(value)
  }

  def apply(value: String): OptionalFilters = {
    OptionalFilters.StringValue(value)
  }

}

object OptionalFiltersSerializer extends Serializer[OptionalFilters] {
  override def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), OptionalFilters] = {

    case (TypeInfo(clazz, _), json) if clazz == classOf[OptionalFilters] =>
      json match {
        case value: JArray  => OptionalFilters.apply(Extraction.extract[Seq[OptionalFilters]](value))
        case JString(value) => OptionalFilters.StringValue(value)
        case _              => throw new MappingException("Can't convert " + json + " to OptionalFilters")
      }
  }

  override def serialize(implicit format: Formats): PartialFunction[Any, JValue] = { case value: OptionalFilters =>
    value match {
      case OptionalFilters.SeqOfOptionalFilters(value) => JArray(value.map(Extraction.decompose).toList)
      case OptionalFilters.StringValue(value)          => JString(value)
    }
  }
}
