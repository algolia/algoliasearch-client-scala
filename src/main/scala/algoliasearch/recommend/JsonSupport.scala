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

object JsonSupport {
  private def enumSerializers: Seq[Serializer[_]] = Seq[Serializer[_]]() :+
    new AdvancedSyntaxFeaturesSerializer() :+
    new AlternativesAsExactSerializer() :+
    new AnchoringSerializer() :+
    new AroundRadiusAllSerializer() :+
    new EditTypeSerializer() :+
    new ExactOnSingleWordQuerySerializer() :+
    new MatchLevelSerializer() :+
    new ModeSerializer() :+
    new QueryTypeSerializer() :+
    new RecommendModelsSerializer() :+
    new RecommendationModelsSerializer() :+
    new RemoveWordsIfNoResultsSerializer() :+
    new SortRemainingBySerializer() :+
    new TaskStatusSerializer() :+
    new TrendingFacetsModelSerializer() :+
    new TrendingItemsModelSerializer() :+
    new TypoToleranceEnumSerializer()

  private def oneOfsSerializers: Seq[Serializer[_]] = Seq[Serializer[_]]() :+
    AroundPrecisionSerializer :+
    AroundRadiusSerializer :+
    AutomaticFacetFiltersSerializer :+
    ConsequenceQuerySerializer :+
    DistinctSerializer :+
    FacetFiltersSerializer :+
    HighlightResultSerializer :+
    IgnorePluralsSerializer :+
    MixedSearchFiltersSerializer :+
    NumericFiltersSerializer :+
    OptionalFiltersSerializer :+
    PromoteSerializer :+
    ReRankingApplyFilterSerializer :+
    RecommendationsRequestSerializer :+
    RemoveStopWordsSerializer :+
    SnippetResultSerializer :+
    TagFiltersSerializer :+
    TypoToleranceSerializer

  private def classMapSerializers: Seq[Serializer[_]] = Seq[Serializer[_]]() :+
    new BaseSearchResponseSerializer() :+
    new ErrorBaseSerializer() :+
    new RecommendHitSerializer()

  implicit val format: Formats = DefaultFormats ++ enumSerializers ++ oneOfsSerializers ++ classMapSerializers
  implicit val serialization: org.json4s.Serialization = org.json4s.native.Serialization
}
