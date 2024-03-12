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

/** Filter the search by values of the special `_tags` attribute. **Prefer using the `filters` parameter, which supports
  * all filter types and combinations with boolean operators.** Different from regular facets, `_tags` can only be used
  * for filtering (including or excluding records). You won't get a facet count. The same combination and escaping rules
  * apply as for `facetFilters`.
  */
sealed trait TagFilters

object TagFilters {

  case class SeqOfMixedSearchFilters(value: Seq[MixedSearchFilters]) extends TagFilters
  case class StringValue(value: String) extends TagFilters

  def apply(value: Seq[MixedSearchFilters]): TagFilters = {
    TagFilters.SeqOfMixedSearchFilters(value)
  }
  def apply(value: String): TagFilters = {
    TagFilters.StringValue(value)
  }
}

object TagFiltersSerializer extends Serializer[TagFilters] {
  override def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), TagFilters] = {

    case (TypeInfo(clazz, _), json) if clazz == classOf[TagFilters] =>
      json match {
        case JArray(value) if value.forall(_.isInstanceOf[JArray]) =>
          TagFilters.SeqOfMixedSearchFilters(value.map(_.extract))
        case JString(value) => TagFilters.StringValue(value)
        case _              => throw new MappingException("Can't convert " + json + " to TagFilters")
      }
  }

  override def serialize(implicit format: Formats): PartialFunction[Any, JValue] = { case value: TagFilters =>
    value match {
      case TagFilters.SeqOfMixedSearchFilters(value) => JArray(value.map(Extraction.decompose).toList)
      case TagFilters.StringValue(value)             => JString(value)
    }
  }
}
