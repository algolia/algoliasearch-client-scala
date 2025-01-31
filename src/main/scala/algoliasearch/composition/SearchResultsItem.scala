/** Composition API Composition API.
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.composition

/** SearchResultsItem
  *
  * @param abTestID
  *   A/B test ID. This is only included in the response for indices that are part of an A/B test.
  * @param abTestVariantID
  *   Variant ID. This is only included in the response for indices that are part of an A/B test.
  * @param aroundLatLng
  *   Computed geographical location.
  * @param automaticRadius
  *   Distance from a central coordinate provided by `aroundLatLng`.
  * @param appliedRules
  *   Rules applied to the query.
  * @param exhaustiveFacetsCount
  *   See the `facetsCount` field of the `exhaustive` object in the response.
  * @param exhaustiveNbHits
  *   See the `nbHits` field of the `exhaustive` object in the response.
  * @param exhaustiveTypo
  *   See the `typo` field of the `exhaustive` object in the response.
  * @param facets
  *   Facet counts.
  * @param facetsStats
  *   Statistics for numerical facets.
  * @param index
  *   Index name used for the query.
  * @param indexUsed
  *   Index name used for the query. During A/B testing, the targeted index isn't always the index used by the query.
  * @param message
  *   Warnings about the query.
  * @param nbSortedHits
  *   Number of hits selected and sorted by the relevant sort algorithm.
  * @param parsedQuery
  *   Post-[normalization](https://www.algolia.com/doc/guides/managing-results/optimize-search-results/handling-natural-languages-nlp/#what-does-normalization-mean)
  *   query string that will be searched.
  * @param processingTimeMS
  *   Time the server took to process the request, in milliseconds.
  * @param processingTimingsMS
  *   Experimental. List of processing steps and their times, in milliseconds. You can use this list to investigate
  *   performance issues.
  * @param queryAfterRemoval
  *   Markup text indicating which parts of the original query have been removed to retrieve a non-empty result set.
  * @param serverTimeMS
  *   Time the server took to process the request, in milliseconds.
  * @param serverUsed
  *   Host name of the server that processed the request.
  * @param userData
  *   An object with custom data. You can store up to 32kB as custom data.
  * @param queryID
  *   Unique identifier for the query. This is used for [click
  *   analytics](https://www.algolia.com/doc/guides/analytics/click-analytics/).
  * @param automaticInsights
  *   Whether automatic events collection is enabled for the application.
  * @param page
  *   Page of search results to retrieve.
  * @param nbHits
  *   Number of results (hits).
  * @param nbPages
  *   Number of pages of results.
  * @param hitsPerPage
  *   Number of hits per page.
  * @param hits
  *   Search results (hits). Hits are records from your index that match the search criteria, augmented with additional
  *   attributes, such as, for highlighting.
  * @param query
  *   Search query.
  * @param params
  *   URL-encoded string of all search parameters.
  */
case class SearchResultsItem(
    abTestID: Option[Int] = scala.None,
    abTestVariantID: Option[Int] = scala.None,
    aroundLatLng: Option[String] = scala.None,
    automaticRadius: Option[String] = scala.None,
    exhaustive: Option[Exhaustive] = scala.None,
    appliedRules: Option[Seq[Any]] = scala.None,
    exhaustiveFacetsCount: Option[Boolean] = scala.None,
    exhaustiveNbHits: Option[Boolean] = scala.None,
    exhaustiveTypo: Option[Boolean] = scala.None,
    facets: Option[Map[String, Map[String, Int]]] = scala.None,
    facetsStats: Option[Map[String, FacetStats]] = scala.None,
    index: Option[String] = scala.None,
    indexUsed: Option[String] = scala.None,
    message: Option[String] = scala.None,
    nbSortedHits: Option[Int] = scala.None,
    parsedQuery: Option[String] = scala.None,
    processingTimeMS: Int,
    processingTimingsMS: Option[Any] = scala.None,
    queryAfterRemoval: Option[String] = scala.None,
    redirect: Option[Redirect] = scala.None,
    renderingContent: Option[RenderingContent] = scala.None,
    serverTimeMS: Option[Int] = scala.None,
    serverUsed: Option[String] = scala.None,
    userData: Option[Any] = scala.None,
    queryID: Option[String] = scala.None,
    automaticInsights: Option[Boolean] = scala.None,
    page: Int,
    nbHits: Int,
    nbPages: Int,
    hitsPerPage: Int,
    hits: Seq[Hit],
    query: String,
    params: String,
    compositions: Map[String, ResultsCompositionInfoResponse]
)
