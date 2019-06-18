/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Algolia
 * http://www.algolia.com/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package algolia.objects

import java.net.URLEncoder

case class Query(
                 /* Advanced */
                 distinct: Option[Distinct] = None,
                 getRankingInfo: Option[Boolean] = None,
                 numericFilters: Option[Seq[String]] = None,
                 tagFilters: Option[Seq[String]] = None,
                 analytics: Option[Boolean] = None,
                 analyticsTags: Option[Seq[String]] = None,
                 synonyms: Option[Boolean] = None,
                 replaceSynonymsInHighlight: Option[Boolean] = None,
                 minProximity: Option[Int] = None,
                 responseFields: Option[Seq[String]] = None,
                 maxFacetHits: Option[Int] = None,
                 percentileComputation: Option[Boolean] = None,
                 queryLanguages: Option[Seq[String]] = None,
                 /* Attributes */
                 attributesToRetrieve: Option[Seq[String]] = None,
                 restrictSearchableAttributes: Option[Seq[String]] = None,
                 /* Filtering faceting */
                 filters: Option[String] = None,
                 facets: Option[Seq[String]] = None,
                 maxValuesPerFacet: Option[Int] = None,
                 facetFilters: Option[Seq[String]] = None,
                 facetingAfterDistinct: Option[Boolean] = None,
                 sortFacetValuesBy: Option[String] = None,
                 /* Geo Search */
                 aroundLatLng: Option[AroundLatLng] = None,
                 aroundLatLngViaIP: Option[Boolean] = None,
                 aroundRadius: Option[AroundRadius] = None,
                 aroundPrecision: Option[Int] = None,
                 minimumAroundRadius: Option[Int] = None,
                 insideBoundingBox: Option[Seq[InsideBoundingBox]] = None,
                 insidePolygon: Option[Seq[InsidePolygon]] = None,
                 /* Highlighting Snippeting */
                 attributesToHighlight: Option[Seq[String]] = None,
                 attributesToSnippet: Option[Seq[String]] = None,
                 highlightPreTag: Option[String] = None,
                 highlightPostTag: Option[String] = None,
                 snippetEllipsisText: Option[String] = None,
                 restrictHighlightAndSnippetArrays: Option[Boolean] = None,
                 /* Pagination */
                 page: Option[Int] = None,
                 hitsPerPage: Option[Int] = None,
                 offset: Option[Int] = None,
                 length: Option[Int] = None,
                 /* Performance */
                 //Nothing in Query

                 /* Query rules */
                 enableRules: Option[Boolean] = None,
                 ruleContexts: Option[Seq[String]] = None,
                 /* Query strategy */
                 queryType: Option[QueryType] = None,
                 removeWordsIfNoResults: Option[RemoveWordsIfNoResults] = None,
                 advancedSyntax: Option[Boolean] = None,
                 advancedSyntaxFeatures: Option[Seq[String]] = None,
                 optionalWords: Option[Seq[String]] = None,
                 removeStopWords: Option[RemoveStopWords] = None,
                 disableExactOnAttributes: Option[Seq[String]] = None,
                 exactOnSingleWordQuery: Option[Seq[String]] = None,
                 alternativesAsExact: Option[Seq[String]] = None,
                 /* Ranking */
                 //Nothing in Query

                 /* Search */
                 query: Option[String] = None,
                 private[algolia] val facetQuery: Option[String] = None,
                 similarQuery: Option[String] = None,
                 /* Typos */
                 minWordSizefor1Typo: Option[Int] = None,
                 minWordSizefor2Typos: Option[Int] = None,
                 typoTolerance: Option[TypoTolerance] = None,
                 allowTyposOnNumericTokens: Option[Boolean] = None,
                 ignorePlurals: Option[IgnorePlurals] = None,
                 disableTypoToleranceOnAttributes: Option[Seq[String]] = None,
                 /* Secured API Keys */
                 userToken: Option[String] = None,
                 validUntil: Option[Int] = None,
                 restrictIndices: Option[Seq[String]] = None,
                 restrictSources: Option[String] = None,
                 /* Browse */
                 cursor: Option[String] = None,
                 /* Personalization */
                 enablePersonalization: Option[Boolean] = None,
                 /* CUSTOM */
                 customParameters: Option[Map[String, String]] = None) {

  def toParam: String = {
    toQueryParam
      .map { case (k, v) => s"$k=${URLEncoder.encode(v, "UTF-8")}" }
      .mkString("&")
  }

  def toQueryParam: Map[String, String] = {
    val queryParam = Map(
      /* Advanced */
      "distinct" -> distinct.map(_.value),
      "getRankingInfo" -> getRankingInfo.map(_.toString),
      "numericFilters" -> numericFilters.map(_.mkString(",")),
      "tagFilters" -> tagFilters.map(_.mkString(",")),
      "analytics" -> analytics.map(_.toString),
      "analyticsTags" -> analyticsTags.map(_.mkString(",")),
      "synonyms" -> synonyms.map(_.toString),
      "replaceSynonymsInHighlight" -> replaceSynonymsInHighlight.map(_.toString),
      "minProximity" -> minProximity.map(_.toString),
      "responseFields" -> responseFields.map(_.mkString(",")),
      "maxFacetHits" -> maxFacetHits.map(_.toString),
      "percentileComputation" -> percentileComputation.map(_.toString),
      "queryLanguages" -> queryLanguages.map(_.mkString(",")),
      /* Attributes */
      "attributesToRetrieve" -> attributesToRetrieve.map(_.mkString(",")),
      "restrictSearchableAttributes" -> restrictSearchableAttributes.map(_.mkString(",")),
      /* Filtering faceting */
      "filters" -> filters,
      "facets" -> facets.map(_.mkString(",")),
      "maxValuesPerFacet" -> maxValuesPerFacet.map(_.toString),
      "facetFilters" -> facetFilters.map(_.mkString(",")),
      "facetingAfterDistinct" -> facetingAfterDistinct.map(_.toString),
      "sortFacetValuesBy" -> sortFacetValuesBy.map(_.toString),
      /* Geo Search */
      "aroundLatLng" -> aroundLatLng.map(_.toString),
      "aroundLatLngViaIP" -> aroundLatLngViaIP.map(_.toString),
      "aroundRadius" -> aroundRadius.map(_.name),
      "aroundPrecision" -> aroundPrecision.map(_.toString),
      "minimumAroundRadius" -> minimumAroundRadius.map(_.toString),
      "insideBoundingBox" -> insideBoundingBox.map(e => s"[${e.map(_.toString).mkString(",")}]"),
      "insidePolygon" -> insidePolygon.map(e => s"[${e.map(_.toString).mkString(",")}]"),
      /* Highlighting Snippeting */
      "attributesToHighlight" -> attributesToHighlight.map(_.mkString(",")),
      "attributesToSnippet" -> attributesToSnippet.map(_.mkString(",")),
      "highlightPreTag" -> highlightPreTag,
      "highlightPostTag" -> highlightPostTag,
      "snippetEllipsisText" -> snippetEllipsisText,
      "restrictHighlightAndSnippetArrays" -> restrictHighlightAndSnippetArrays.map(_.toString),
      /* Pagination */
      "page" -> page.map(_.toString),
      "hitsPerPage" -> hitsPerPage.map(_.toString),
      "offset" -> offset.map(_.toString),
      "length" -> length.map(_.toString),
      /* Performance */
      //Nothing in Query

      /* Query rules */
      "enableRules" -> enableRules.map(_.toString),
      "ruleContexts" -> ruleContexts.map(_.mkString(",")),
      /* Query strategy */
      "queryType" -> queryType.map(_.name),
      "removeWordsIfNoResults" -> removeWordsIfNoResults.map(_.name),
      "advancedSyntax" -> advancedSyntax.map(_.toString),
      "advancedSyntaxFeatures" -> advancedSyntaxFeatures.map(_.mkString(",")),
      "optionalWords" -> optionalWords.map(_.mkString(",")),
      "removeStopWords" -> removeStopWords.map(_.value),
      "disableExactOnAttributes" -> disableExactOnAttributes.map(_.mkString(",")),
      "exactOnSingleWordQuery" -> exactOnSingleWordQuery.map(_.mkString(",")),
      "alternativesAsExact" -> alternativesAsExact.map(_.mkString(",")),
      /* Ranking */
      //Nothing in Query

      /* Search */
      "query" -> query,
      "facetQuery" -> facetQuery,
      /* Typos */
      "minWordSizefor1Typo" -> minWordSizefor1Typo.map(_.toString),
      "minWordSizefor2Typos" -> minWordSizefor2Typos.map(_.toString),
      "typoTolerance" -> typoTolerance.map(_.name),
      "allowTyposOnNumericTokens" -> allowTyposOnNumericTokens.map(_.toString),
      "ignorePlurals" -> ignorePlurals.map(_.value),
      "disableTypoToleranceOnAttributes" -> disableTypoToleranceOnAttributes.map(_.mkString(",")),
      /* Secured API Keys */
      "userToken" -> userToken,
      "validUntil" -> validUntil.map(_.toString),
      "restrictIndices" -> restrictIndices.map(_.mkString(",")),
      "restrictSources" -> restrictSources.map(_.mkString(",")),
      /* Browse */
      "cursor" -> cursor
    ).filter { case (_, v) => v.isDefined }
      .map { case (k, v) => k -> v.get }

    customParameters.fold(queryParam)(c => queryParam ++ c)
  }
}
