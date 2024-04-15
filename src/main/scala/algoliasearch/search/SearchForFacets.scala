/** Search API The Algolia Search API lets you search, configure, and mange your indices and records. ## Client
  * libraries Use Algolia's API clients and libraries to reliably integrate Algolia's APIs with your apps. The official
  * API clients are covered by Algolia's [Service Level Agreement](https://www.algolia.com/policies/sla/). See:
  * [Algolia's ecosystem](https://www.algolia.com/doc/guides/getting-started/how-algolia-works/in-depth/ecosystem/) ##
  * Base URLs The base URLs for requests to the Search API are: - `https://{APPLICATION_ID}.algolia.net` -
  * `https://{APPLICATION_ID}-dsn.algolia.net`. If your subscription includes a [Distributed Search
  * Network](https://dashboard.algolia.com/infra), this ensures that requests are sent to servers closest to users. Both
  * URLs provide high availability by distributing requests with load balancing. **All requests must use HTTPS.** ##
  * Retry strategy To guarantee a high availability, implement a retry strategy for all API requests using the URLs of
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

import algoliasearch.search.AdvancedSyntaxFeatures._
import algoliasearch.search.AlternativesAsExact._
import algoliasearch.search.ExactOnSingleWordQuery._
import algoliasearch.search.Mode._
import algoliasearch.search.QueryType._
import algoliasearch.search.RemoveWordsIfNoResults._
import algoliasearch.search.SearchTypeFacet._
import algoliasearch.search.SupportedLanguage._

/** SearchForFacets
  *
  * @param params
  *   Search parameters as a URL-encoded query string.
  * @param query
  *   Search query.
  * @param similarQuery
  *   Keywords to be used instead of the search query to conduct a more broader search. Using the `similarQuery`
  *   parameter changes other settings: - `queryType` is set to `prefixNone`. - `removeStopWords` is set to true. -
  *   `words` is set as the first ranking criterion. - All remaining words are treated as `optionalWords`. Since the
  *   `similarQuery` is supposed to do a broad search, they usually return many results. Combine it with `filters` to
  *   narrow down the list of results.
  * @param filters
  *   Filter expression to only include items that match the filter criteria in the response. You can use these filter
  *   expressions: - **Numeric filters.** `<facet> <op> <number>`, where `<op>` is one of `<`, `<=`, `=`, `!=`, `>`,
  *   `>=`. - **Ranges.** `<facet>:<lower> TO <upper>` where `<lower>` and `<upper>` are the lower and upper limits of
  *   the range (inclusive). - **Facet filters.** `<facet>:<value>` where `<facet>` is a facet attribute
  *   (case-sensitive) and `<value>` a facet value. - **Tag filters.** `_tags:<value>` or just `<value>`
  *   (case-sensitive). - **Boolean filters.** `<facet>: true | false`. You can combine filters with `AND`, `OR`, and
  *   `NOT` operators with the following restrictions: - You can only combine filters of the same type with `OR`. **Not
  *   supported:** `facet:value OR num > 3`. - You can't use `NOT` with combinations of filters. **Not supported:**
  *   `NOT(facet:value OR facet:value)` - You can't combine conjunctions (`AND`) with `OR`. **Not supported:**
  *   `facet:value OR (facet:value AND facet:value)` Use quotes around your filters, if the facet attribute name or
  *   facet value has spaces, keywords (`OR`, `AND`, `NOT`), or quotes. If a facet attribute is an array, the filter
  *   matches if it matches at least one element of the array. For more information, see
  *   [Filters](https://www.algolia.com/doc/guides/managing-results/refine-results/filtering/).
  * @param sumOrFiltersScores
  *   Whether to sum all filter scores. If true, all filter scores are summed. Otherwise, the maximum filter score is
  *   kept. For more information, see [filter
  *   scores](https://www.algolia.com/doc/guides/managing-results/refine-results/filtering/in-depth/filter-scoring/#accumulating-scores-with-sumorfiltersscores).
  * @param restrictSearchableAttributes
  *   Restricts a search to a subset of your searchable attributes. Attribute names are case-sensitive.
  * @param facets
  *   Facets for which to retrieve facet values that match the search criteria and the number of matching facet values.
  *   To retrieve all facets, use the wildcard character `*`. For more information, see
  *   [facets](https://www.algolia.com/doc/guides/managing-results/refine-results/faceting/#contextual-facet-values-and-counts).
  * @param facetingAfterDistinct
  *   Whether faceting should be applied after deduplication with `distinct`. This leads to accurate facet counts when
  *   using faceting in combination with `distinct`. It's usually better to use `afterDistinct` modifiers in the
  *   `attributesForFaceting` setting, as `facetingAfterDistinct` only computes correct facet counts if all records have
  *   the same facet values for the `attributeForDistinct`.
  * @param page
  *   Page of search results to retrieve.
  * @param offset
  *   Position of the first hit to retrieve.
  * @param length
  *   Number of hits to retrieve (used in combination with `offset`).
  * @param aroundLatLng
  *   Coordinates for the center of a circle, expressed as a comma-separated string of latitude and longitude. Only
  *   records included within circle around this central location are included in the results. The radius of the circle
  *   is determined by the `aroundRadius` and `minimumAroundRadius` settings. This parameter is ignored if you also
  *   specify `insidePolygon` or `insideBoundingBox`.
  * @param aroundLatLngViaIP
  *   Whether to obtain the coordinates from the request's IP address.
  * @param minimumAroundRadius
  *   Minimum radius (in meters) for a search around a location when `aroundRadius` isn't set.
  * @param insideBoundingBox
  *   Coordinates for a rectangular area in which to search. Each bounding box is defined by the two opposite points of
  *   its diagonal, and expressed as latitude and longitude pair: `[p1 lat, p1 long, p2 lat, p2 long]`. Provide multiple
  *   bounding boxes as nested arrays. For more information, see [rectangular
  *   area](https://www.algolia.com/doc/guides/managing-results/refine-results/geolocation/#filtering-inside-rectangular-or-polygonal-areas).
  * @param insidePolygon
  *   Coordinates of a polygon in which to search. Polygons are defined by 3 to 10,000 points. Each point is represented
  *   by its latitude and longitude. Provide multiple polygons as nested arrays. For more information, see [filtering
  *   inside
  *   polygons](https://www.algolia.com/doc/guides/managing-results/refine-results/geolocation/#filtering-inside-rectangular-or-polygonal-areas).
  *   This parameter is ignored if you also specify `insideBoundingBox`.
  * @param naturalLanguages
  *   ISO language codes that adjust settings that are useful for processing natural language queries (as opposed to
  *   keyword searches): - Sets `removeStopWords` and `ignorePlurals` to the list of provided languages. - Sets
  *   `removeWordsIfNoResults` to `allOptional`. - Adds a `natural_language` attribute to `ruleContexts` and
  *   `analyticsTags`.
  * @param ruleContexts
  *   Assigns a rule context to the search query. [Rule
  *   contexts](https://www.algolia.com/doc/guides/managing-results/rules/rules-overview/how-to/customize-search-results-by-platform/#whats-a-context)
  *   are strings that you can use to trigger matching rules.
  * @param personalizationImpact
  *   Impact that Personalization should have on this search. The higher this value is, the more Personalization
  *   determines the ranking compared to other factors. For more information, see [Understanding Personalization
  *   impact](https://www.algolia.com/doc/guides/personalization/personalizing-results/in-depth/configuring-personalization/#understanding-personalization-impact).
  * @param userToken
  *   Unique pseudonymous or anonymous user identifier. This helps with analytics and click and conversion events. For
  *   more information, see [user token](https://www.algolia.com/doc/guides/sending-events/concepts/usertoken/).
  * @param getRankingInfo
  *   Whether the search response should include detailed ranking information.
  * @param synonyms
  *   Whether to take into account an index's synonyms for this search.
  * @param clickAnalytics
  *   Whether to include a `queryID` attribute in the response. The query ID is a unique identifier for a search query
  *   and is required for tracking [click and conversion
  *   events](https://www.algolia.com/guides/sending-events/getting-started/).
  * @param analytics
  *   Whether this search will be included in Analytics.
  * @param analyticsTags
  *   Tags to apply to the query for [segmenting analytics
  *   data](https://www.algolia.com/doc/guides/search-analytics/guides/segments/).
  * @param percentileComputation
  *   Whether to include this search when calculating processing-time percentiles.
  * @param enableABTest
  *   Whether to enable A/B testing for this search.
  * @param attributesToRetrieve
  *   Attributes to include in the API response. To reduce the size of your response, you can retrieve only some of the
  *   attributes. Attribute names are case-sensitive. - `*` retrieves all attributes, except attributes included in the
  *   `customRanking` and `unretrievableAttributes` settings. - To retrieve all attributes except a specific one, prefix
  *   the attribute with a dash and combine it with the `*`: `[\"*\", \"-ATTRIBUTE\"]`. - The `objectID` attribute is
  *   always included.
  * @param ranking
  *   Determines the order in which Algolia returns your results. By default, each entry corresponds to a [ranking
  *   criteria](https://www.algolia.com/doc/guides/managing-results/relevance-overview/in-depth/ranking-criteria/). The
  *   tie-breaking algorithm sequentially applies each criterion in the order they're specified. If you configure a
  *   replica index for [sorting by an
  *   attribute](https://www.algolia.com/doc/guides/managing-results/refine-results/sorting/how-to/sort-by-attribute/),
  *   you put the sorting attribute at the top of the list. **Modifiers** - `asc(\"ATTRIBUTE\")`. Sort the index by the
  *   values of an attribute, in ascending order. - `desc(\"ATTRIBUTE\")`. Sort the index by the values of an attribute,
  *   in descending order. Before you modify the default setting, you should test your changes in the dashboard, and by
  *   [A/B testing](https://www.algolia.com/doc/guides/ab-testing/what-is-ab-testing/).
  * @param customRanking
  *   Attributes to use as [custom
  *   ranking](https://www.algolia.com/doc/guides/managing-results/must-do/custom-ranking/). Attribute names are
  *   case-sensitive. The custom ranking attributes decide which items are shown first if the other ranking criteria are
  *   equal. Records with missing values for your selected custom ranking attributes are always sorted last. Boolean
  *   attributes are sorted based on their alphabetical order. **Modifiers** - `asc(\"ATTRIBUTE\")`. Sort the index by
  *   the values of an attribute, in ascending order. - `desc(\"ATTRIBUTE\")`. Sort the index by the values of an
  *   attribute, in descending order. If you use two or more custom ranking attributes, [reduce the
  *   precision](https://www.algolia.com/doc/guides/managing-results/must-do/custom-ranking/how-to/controlling-custom-ranking-metrics-precision/)
  *   of your first attributes, or the other attributes will never be applied.
  * @param relevancyStrictness
  *   Relevancy threshold below which less relevant results aren't included in the results. You can only set
  *   `relevancyStrictness` on [virtual replica
  *   indices](https://www.algolia.com/doc/guides/managing-results/refine-results/sorting/in-depth/replicas/#what-are-virtual-replicas).
  *   Use this setting to strike a balance between the relevance and number of returned results.
  * @param attributesToHighlight
  *   Attributes to highlight. By default, all searchable attributes are highlighted. Use `*` to highlight all
  *   attributes or use an empty array `[]` to turn off highlighting. Attribute names are case-sensitive. With
  *   highlighting, strings that match the search query are surrounded by HTML tags defined by `highlightPreTag` and
  *   `highlightPostTag`. You can use this to visually highlight matching parts of a search query in your UI. For more
  *   information, see [Highlighting and
  *   snippeting](https://www.algolia.com/doc/guides/building-search-ui/ui-and-ux-patterns/highlighting-snippeting/js/).
  * @param attributesToSnippet
  *   Attributes for which to enable snippets. Attribute names are case-sensitive. Snippets provide additional context
  *   to matched words. If you enable snippets, they include 10 words, including the matched word. The matched word will
  *   also be wrapped by HTML tags for highlighting. You can adjust the number of words with the following notation:
  *   `ATTRIBUTE:NUMBER`, where `NUMBER` is the number of words to be extracted.
  * @param highlightPreTag
  *   HTML tag to insert before the highlighted parts in all highlighted results and snippets.
  * @param highlightPostTag
  *   HTML tag to insert after the highlighted parts in all highlighted results and snippets.
  * @param snippetEllipsisText
  *   String used as an ellipsis indicator when a snippet is truncated.
  * @param restrictHighlightAndSnippetArrays
  *   Whether to restrict highlighting and snippeting to items that at least partially matched the search query. By
  *   default, all items are highlighted and snippeted.
  * @param hitsPerPage
  *   Number of hits per page.
  * @param minWordSizefor1Typo
  *   Minimum number of characters a word in the search query must contain to accept matches with [one
  *   typo](https://www.algolia.com/doc/guides/managing-results/optimize-search-results/typo-tolerance/in-depth/configuring-typo-tolerance/#configuring-word-length-for-typos).
  * @param minWordSizefor2Typos
  *   Minimum number of characters a word in the search query must contain to accept matches with [two
  *   typos](https://www.algolia.com/doc/guides/managing-results/optimize-search-results/typo-tolerance/in-depth/configuring-typo-tolerance/#configuring-word-length-for-typos).
  * @param allowTyposOnNumericTokens
  *   Whether to allow typos on numbers in the search query. Turn off this setting to reduce the number of irrelevant
  *   matches when searching in large sets of similar numbers.
  * @param disableTypoToleranceOnAttributes
  *   Attributes for which you want to turn off [typo
  *   tolerance](https://www.algolia.com/doc/guides/managing-results/optimize-search-results/typo-tolerance/). Attribute
  *   names are case-sensitive. Returning only exact matches can help when: - [Searching in hyphenated
  *   attributes](https://www.algolia.com/doc/guides/managing-results/optimize-search-results/typo-tolerance/how-to/how-to-search-in-hyphenated-attributes/).
  *   \- Reducing the number of matches when you have too many. This can happen with attributes that are long blocks of
  *   text, such as product descriptions. Consider alternatives such as `disableTypoToleranceOnWords` or adding synonyms
  *   if your attributes have intentional unusual spellings that might look like typos.
  * @param keepDiacriticsOnCharacters
  *   Characters for which diacritics should be preserved. By default, Algolia removes diacritics from letters. For
  *   example, `é` becomes `e`. If this causes issues in your search, you can specify characters that should keep their
  *   diacritics.
  * @param queryLanguages
  *   Languages for language-specific query processing steps such as plurals, stop-word removal, and word-detection
  *   dictionaries. This setting sets a default list of languages used by the `removeStopWords` and `ignorePlurals`
  *   settings. This setting also sets a dictionary for word detection in the logogram-based
  *   [CJK](https://www.algolia.com/doc/guides/managing-results/optimize-search-results/handling-natural-languages-nlp/in-depth/normalization/#normalization-for-logogram-based-languages-cjk)
  *   languages. To support this, you must place the CJK language **first**. **You should always specify a query
  *   language.** If you don't specify an indexing language, the search engine uses all [supported
  *   languages](https://www.algolia.com/doc/guides/managing-results/optimize-search-results/handling-natural-languages-nlp/in-depth/supported-languages/),
  *   or the languages you specified with the `ignorePlurals` or `removeStopWords` parameters. This can lead to
  *   unexpected search results. For more information, see [Language-specific
  *   configuration](https://www.algolia.com/doc/guides/managing-results/optimize-search-results/handling-natural-languages-nlp/in-depth/language-specific-configurations/).
  * @param decompoundQuery
  *   Whether to split compound words into their building blocks. For more information, see [Word
  *   segmentation](https://www.algolia.com/doc/guides/managing-results/optimize-search-results/handling-natural-languages-nlp/in-depth/language-specific-configurations/#splitting-compound-words).
  *   Word segmentation is supported for these languages: German, Dutch, Finnish, Swedish, and Norwegian.
  * @param enableRules
  *   Whether to enable rules.
  * @param enablePersonalization
  *   Whether to enable Personalization.
  * @param advancedSyntax
  *   Whether to support phrase matching and excluding words from search queries. Use the `advancedSyntaxFeatures`
  *   parameter to control which feature is supported.
  * @param optionalWords
  *   Words that should be considered optional when found in the query. By default, records must match all words in the
  *   search query to be included in the search results. Adding optional words can help to increase the number of search
  *   results by running an additional search query that doesn't include the optional words. For example, if the search
  *   query is \"action video\" and \"video\" is an optional word, the search engine runs two queries. One for \"action
  *   video\" and one for \"action\". Records that match all words are ranked higher. For a search query with 4 or more
  *   words **and** all its words are optional, the number of matched words required for a record to be included in the
  *   search results increases for every 1,000 records: - If `optionalWords` has less than 10 words, the required number
  *   of matched words increases by 1: results 1 to 1,000 require 1 matched word, results 1,001 to 2000 need 2 matched
  *   words. - If `optionalWords` has 10 or more words, the number of required matched words increases by the number of
  *   optional words dividied by 5 (rounded down). For example, with 18 optional words: results 1 to 1,000 require 1
  *   matched word, results 1,001 to 2000 need 4 matched words. For more information, see [Optional
  *   words](https://www.algolia.com/doc/guides/managing-results/optimize-search-results/empty-or-insufficient-results/#creating-a-list-of-optional-words).
  * @param disableExactOnAttributes
  *   Searchable attributes for which you want to [turn off the Exact ranking
  *   criterion](https://www.algolia.com/doc/guides/managing-results/optimize-search-results/override-search-engine-defaults/in-depth/adjust-exact-settings/#turn-off-exact-for-some-attributes).
  *   Attribute names are case-sensitive. This can be useful for attributes with long values, where the likelyhood of an
  *   exact match is high, such as product descriptions. Turning off the Exact ranking criterion for these attributes
  *   favors exact matching on other attributes. This reduces the impact of individual attributes with a lot of content
  *   on ranking.
  * @param alternativesAsExact
  *   Alternatives of query words that should be considered as exact matches by the Exact ranking criterion. -
  *   `ignorePlurals`. Plurals and similar declensions added by the `ignorePlurals` setting are considered exact
  *   matches. - `singleWordSynonym`. Single-word synonyms, such as \"NY/NYC\" are considered exact matches. -
  *   `multiWordsSynonym`. Multi-word synonyms, such as \"NY/New York\" are considered exact matches.
  * @param advancedSyntaxFeatures
  *   Advanced search syntax features you want to support. - `exactPhrase`. Phrases in quotes must match exactly. For
  *   example, `sparkly blue \"iPhone case\"` only returns records with the exact string \"iPhone case\". -
  *   `excludeWords`. Query words prefixed with a `-` must not occur in a record. For example, `search -engine` matches
  *   records that contain \"search\" but not \"engine\". This setting only has an effect if `advancedSyntax` is true.
  * @param replaceSynonymsInHighlight
  *   Whether to replace a highlighted word with the matched synonym. By default, the original words are highlighted
  *   even if a synonym matches. For example, with `home` as a synonym for `house` and a search for `home`, records
  *   matching either \"home\" or \"house\" are included in the search results, and either \"home\" or \"house\" are
  *   highlighted. With `replaceSynonymsInHighlight` set to `true`, a search for `home` still matches the same records,
  *   but all occurences of \"house\" are replaced by \"home\" in the highlighted response.
  * @param minProximity
  *   Minimum proximity score for two matching words. This adjusts the [Proximity ranking
  *   criterion](https://www.algolia.com/doc/guides/managing-results/relevance-overview/in-depth/ranking-criteria/#proximity)
  *   by equally scoring matches that are farther apart. For example, if `minProximity` is 2, neighboring matches and
  *   matches with one word between them would have the same score.
  * @param responseFields
  *   Properties to include in the API response of `search` and `browse` requests. By default, all response properties
  *   are included. To reduce the response size, you can select, which attributes should be included. You can't exclude
  *   these properties: `message`, `warning`, `cursor`, `serverUsed`, `indexUsed`, `abTestVariantID`, `parsedQuery`, or
  *   any property triggered by the `getRankingInfo` parameter. Don't exclude properties that you might need in your
  *   search UI.
  * @param maxFacetHits
  *   Maximum number of facet values to return when [searching for facet
  *   values](https://www.algolia.com/doc/guides/managing-results/refine-results/faceting/#search-for-facet-values).
  * @param maxValuesPerFacet
  *   Maximum number of facet values to return for each facet.
  * @param sortFacetValuesBy
  *   Order in which to retrieve facet values. - `count`. Facet values are retrieved by decreasing count. The count is
  *   the number of matching records containing this facet value. - `alpha`. Retrieve facet values alphabetically. This
  *   setting doesn't influence how facet values are displayed in your UI (see `renderingContent`). For more
  *   information, see [facet value
  *   display](https://www.algolia.com/doc/guides/building-search-ui/ui-and-ux-patterns/facet-display/js/).
  * @param attributeCriteriaComputedByMinProximity
  *   Whether the best matching attribute should be determined by minimum proximity. This setting only affects ranking
  *   if the Attribute ranking criterion comes before Proximity in the `ranking` setting. If true, the best matching
  *   attribute is selected based on the minimum proximity of multiple matches. Otherwise, the best matching attribute
  *   is determined by the order in the `searchableAttributes` setting.
  * @param enableReRanking
  *   Whether this search will use [Dynamic Re-Ranking](https://www.algolia.com/doc/guides/algolia-ai/re-ranking/). This
  *   setting only has an effect if you activated Dynamic Re-Ranking for this index in the Algolia dashboard.
  * @param facet
  *   Facet name.
  * @param indexName
  *   Index name (case-sensitive).
  * @param facetQuery
  *   Text to search inside the facet's values.
  */
case class SearchForFacets(
    params: Option[String] = scala.None,
    query: Option[String] = scala.None,
    similarQuery: Option[String] = scala.None,
    filters: Option[String] = scala.None,
    facetFilters: Option[FacetFilters] = scala.None,
    optionalFilters: Option[OptionalFilters] = scala.None,
    numericFilters: Option[NumericFilters] = scala.None,
    tagFilters: Option[TagFilters] = scala.None,
    sumOrFiltersScores: Option[Boolean] = scala.None,
    restrictSearchableAttributes: Option[Seq[String]] = scala.None,
    facets: Option[Seq[String]] = scala.None,
    facetingAfterDistinct: Option[Boolean] = scala.None,
    page: Option[Int] = scala.None,
    offset: Option[Int] = scala.None,
    length: Option[Int] = scala.None,
    aroundLatLng: Option[String] = scala.None,
    aroundLatLngViaIP: Option[Boolean] = scala.None,
    aroundRadius: Option[AroundRadius] = scala.None,
    aroundPrecision: Option[AroundPrecision] = scala.None,
    minimumAroundRadius: Option[Int] = scala.None,
    insideBoundingBox: Option[Seq[Seq[Double]]] = scala.None,
    insidePolygon: Option[Seq[Seq[Double]]] = scala.None,
    naturalLanguages: Option[Seq[SupportedLanguage]] = scala.None,
    ruleContexts: Option[Seq[String]] = scala.None,
    personalizationImpact: Option[Int] = scala.None,
    userToken: Option[String] = scala.None,
    getRankingInfo: Option[Boolean] = scala.None,
    synonyms: Option[Boolean] = scala.None,
    clickAnalytics: Option[Boolean] = scala.None,
    analytics: Option[Boolean] = scala.None,
    analyticsTags: Option[Seq[String]] = scala.None,
    percentileComputation: Option[Boolean] = scala.None,
    enableABTest: Option[Boolean] = scala.None,
    attributesToRetrieve: Option[Seq[String]] = scala.None,
    ranking: Option[Seq[String]] = scala.None,
    customRanking: Option[Seq[String]] = scala.None,
    relevancyStrictness: Option[Int] = scala.None,
    attributesToHighlight: Option[Seq[String]] = scala.None,
    attributesToSnippet: Option[Seq[String]] = scala.None,
    highlightPreTag: Option[String] = scala.None,
    highlightPostTag: Option[String] = scala.None,
    snippetEllipsisText: Option[String] = scala.None,
    restrictHighlightAndSnippetArrays: Option[Boolean] = scala.None,
    hitsPerPage: Option[Int] = scala.None,
    minWordSizefor1Typo: Option[Int] = scala.None,
    minWordSizefor2Typos: Option[Int] = scala.None,
    typoTolerance: Option[TypoTolerance] = scala.None,
    allowTyposOnNumericTokens: Option[Boolean] = scala.None,
    disableTypoToleranceOnAttributes: Option[Seq[String]] = scala.None,
    ignorePlurals: Option[IgnorePlurals] = scala.None,
    removeStopWords: Option[RemoveStopWords] = scala.None,
    keepDiacriticsOnCharacters: Option[String] = scala.None,
    queryLanguages: Option[Seq[SupportedLanguage]] = scala.None,
    decompoundQuery: Option[Boolean] = scala.None,
    enableRules: Option[Boolean] = scala.None,
    enablePersonalization: Option[Boolean] = scala.None,
    queryType: Option[QueryType] = scala.None,
    removeWordsIfNoResults: Option[RemoveWordsIfNoResults] = scala.None,
    mode: Option[Mode] = scala.None,
    semanticSearch: Option[SemanticSearch] = scala.None,
    advancedSyntax: Option[Boolean] = scala.None,
    optionalWords: Option[Seq[String]] = scala.None,
    disableExactOnAttributes: Option[Seq[String]] = scala.None,
    exactOnSingleWordQuery: Option[ExactOnSingleWordQuery] = scala.None,
    alternativesAsExact: Option[Seq[AlternativesAsExact]] = scala.None,
    advancedSyntaxFeatures: Option[Seq[AdvancedSyntaxFeatures]] = scala.None,
    distinct: Option[Distinct] = scala.None,
    replaceSynonymsInHighlight: Option[Boolean] = scala.None,
    minProximity: Option[Int] = scala.None,
    responseFields: Option[Seq[String]] = scala.None,
    maxFacetHits: Option[Int] = scala.None,
    maxValuesPerFacet: Option[Int] = scala.None,
    sortFacetValuesBy: Option[String] = scala.None,
    attributeCriteriaComputedByMinProximity: Option[Boolean] = scala.None,
    renderingContent: Option[RenderingContent] = scala.None,
    enableReRanking: Option[Boolean] = scala.None,
    reRankingApplyFilter: Option[ReRankingApplyFilter] = scala.None,
    facet: String,
    indexName: String,
    facetQuery: Option[String] = scala.None,
    `type`: SearchTypeFacet
) extends SearchQueryTrait
