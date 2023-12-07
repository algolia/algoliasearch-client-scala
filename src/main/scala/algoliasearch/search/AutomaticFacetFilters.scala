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

import org.json4s._

/** Names of facets to which automatic filtering must be applied; they must match the facet name of a facet value
  * placeholder in the query pattern.
  */
sealed trait AutomaticFacetFilters

trait AutomaticFacetFiltersEvidence

object AutomaticFacetFiltersEvidence {
  implicit object SeqOfAutomaticFacetFilterEvidence extends AutomaticFacetFiltersEvidence
  implicit object SeqOfStringEvidence extends AutomaticFacetFiltersEvidence
}

object AutomaticFacetFilters {

  case class SeqOfAutomaticFacetFilter(value: Seq[AutomaticFacetFilter]) extends AutomaticFacetFilters
  case class SeqOfString(value: Seq[String]) extends AutomaticFacetFilters

  def apply(
      value: Seq[AutomaticFacetFilter]
  )(implicit ev: AutomaticFacetFiltersEvidence.SeqOfAutomaticFacetFilterEvidence.type): AutomaticFacetFilters = {
    AutomaticFacetFilters.SeqOfAutomaticFacetFilter(value)
  }
  def apply(
      value: Seq[String]
  )(implicit ev: AutomaticFacetFiltersEvidence.SeqOfStringEvidence.type): AutomaticFacetFilters = {
    AutomaticFacetFilters.SeqOfString(value)
  }

}

object AutomaticFacetFiltersSerializer extends Serializer[AutomaticFacetFilters] {
  override def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), AutomaticFacetFilters] = {

    case (TypeInfo(clazz, _), json) if clazz == classOf[AutomaticFacetFilters] =>
      json match {
        case JArray(value) if value.forall(_.isInstanceOf[JArray]) =>
          AutomaticFacetFilters.SeqOfAutomaticFacetFilter(value.map(_.extract))
        case JArray(value) if value.forall(_.isInstanceOf[JArray]) =>
          AutomaticFacetFilters.SeqOfString(value.map(_.extract))
        case _ => throw new MappingException("Can't convert " + json + " to AutomaticFacetFilters")
      }
  }

  override def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case value: AutomaticFacetFilters =>
      value match {
        case AutomaticFacetFilters.SeqOfAutomaticFacetFilter(value) => JArray(value.map(Extraction.decompose).toList)
        case AutomaticFacetFilters.SeqOfString(value)               => JArray(value.map(Extraction.decompose).toList)
      }
  }
}
