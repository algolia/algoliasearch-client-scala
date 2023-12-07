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

import algoliasearch.search.AdvancedSyntaxFeatures._
import algoliasearch.search.AlternativesAsExact._
import algoliasearch.search.ExactOnSingleWordQuery._
import algoliasearch.search.Mode._
import algoliasearch.search.QueryType._
import algoliasearch.search.RemoveWordsIfNoResults._

import org.json4s._

/** BrowseParams
  */
sealed trait BrowseParams

trait BrowseParamsTrait extends BrowseParams

object BrowseParams {}

object BrowseParamsSerializer extends Serializer[BrowseParams] {
  override def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), BrowseParams] = {

    case (TypeInfo(clazz, _), json) if clazz == classOf[BrowseParams] =>
      json match {
        case value: JObject if value.obj.contains("params") => Extraction.extract[SearchParamsString](value)
        case value: JObject                                 => Extraction.extract[BrowseParamsObject](value)
        case _ => throw new MappingException("Can't convert " + json + " to BrowseParams")
      }
  }

  override def serialize(implicit format: Formats): PartialFunction[Any, JValue] = { case value: BrowseParams =>
    value match {
      case value: SearchParamsString => Extraction.decompose(value)(format - this)
      case value: BrowseParamsObject => Extraction.decompose(value)(format - this)
    }
  }
}
