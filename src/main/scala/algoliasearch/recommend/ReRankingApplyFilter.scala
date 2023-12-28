/** Recommend API The Recommend API lets you generate recommendations with several AI models. > **Note**: You should use
  * Algolia's [libraries and
  * tools](https://www.algolia.com/doc/guides/getting-started/how-algolia-works/in-depth/ecosystem/) to interact with
  * the Recommend API. Using the HTTP endpoints directly is not covered by the
  * [SLA](https://www.algolia.com/policies/sla/).
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.recommend

import org.json4s._

/** When [Dynamic Re-Ranking](https://www.algolia.com/doc/guides/algolia-ai/re-ranking/) is enabled, only records that
  * match these filters will be affected by Dynamic Re-Ranking.
  */
sealed trait ReRankingApplyFilter

object ReRankingApplyFilter {

  case class SeqOfMixedSearchFilters(value: Seq[MixedSearchFilters]) extends ReRankingApplyFilter
  case class StringValue(value: String) extends ReRankingApplyFilter

  def apply(value: Seq[MixedSearchFilters]): ReRankingApplyFilter = {
    ReRankingApplyFilter.SeqOfMixedSearchFilters(value)
  }
  def apply(value: String): ReRankingApplyFilter = {
    ReRankingApplyFilter.StringValue(value)
  }
}

object ReRankingApplyFilterSerializer extends Serializer[ReRankingApplyFilter] {
  override def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), ReRankingApplyFilter] = {

    case (TypeInfo(clazz, _), json) if clazz == classOf[ReRankingApplyFilter] =>
      json match {
        case JArray(value) if value.forall(_.isInstanceOf[JArray]) =>
          ReRankingApplyFilter.SeqOfMixedSearchFilters(value.map(_.extract))
        case JString(value) => ReRankingApplyFilter.StringValue(value)
        case _              => throw new MappingException("Can't convert " + json + " to ReRankingApplyFilter")
      }
  }

  override def serialize(implicit format: Formats): PartialFunction[Any, JValue] = { case value: ReRankingApplyFilter =>
    value match {
      case ReRankingApplyFilter.SeqOfMixedSearchFilters(value) => JArray(value.map(Extraction.decompose).toList)
      case ReRankingApplyFilter.StringValue(value)             => JString(value)
    }
  }
}