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

case class Query(/* FULL TEXT SEARCH PARAMETERS */
                 query: Option[String] = None,
                 private[algolia] val facetQuery: Option[String] = None,
                 queryType: Option[QueryType] = None,
                 typoTolerance: Option[TypoTolerance] = None,
                 minWordSizefor1Typo: Option[Int] = None,
                 minWordSizefor2Typos: Option[Int] = None,
                 allowTyposOnNumericTokens: Option[Boolean] = None,
                 ignorePlurals: Option[IgnorePlurals] = None,
                 restrictSearchableAttributes: Option[Seq[String]] = None,
                 advancedSyntax: Option[Boolean] = None,
                 analytics: Option[Boolean] = None,
                 analyticsTags: Option[Seq[String]] = None,
                 synonyms: Option[Boolean] = None,
                 replaceSynonymsInHighlight: Option[Boolean] = None,
                 optionalWords: Option[Seq[String]] = None,
                 minProximity: Option[Int] = None,
                 removeWordsIfNoResults: Option[RemoveWordsIfNoResults] = None,
                 disableTypoToleranceOnAttributes: Option[Seq[String]] = None,
                 removeStopWords: Option[RemoveStopWords] = None,
                 exactOnSingleWordQuery: Option[String] = None,
                 alternativesAsExact: Option[String] = None,

                 /* PAGINATION PARAMETERS */
                 page: Option[Int] = None,
                 hitsPerPage: Option[Int] = None,

                 /* PARAMETERS TO CONTROL RESULTS CONTENT */
                 attributesToRetrieve: Option[Seq[String]] = None,
                 attributesToHighlight: Option[Seq[String]] = None,
                 attributesToSnippet: Option[Seq[(String, Int)]] = None,
                 getRankingInfo: Option[Boolean] = None,
                 highlightPreTag: Option[String] = None,
                 highlightPostTag: Option[String] = None,
                 snippetEllipsisText: Option[String] = None,

                 /* NUMERIC SEARCH PARAMETERS */
                 numericFilters: Option[String] = None,

                 /* CATEGORY SEARCH PARAMETER */
                 tagFilters: Option[Seq[String]] = None,

                 /* DISTINCT PARAMETER */
                 distinct: Option[Distinct] = None,

                 responseFields: Option[Seq[String]] = None,

                 /* FACETING PARAMETERS */
                 facets: Option[Seq[String]] = None,
                 facetFilters: Option[Seq[String]] = None,
                 maxValuesPerFacet: Option[Int] = None,

                 /* UNIFIED FILTER PARAMETER (SQL LIKE) */
                 filters: Option[String] = None,

                 /* GEO-SEARCH PARAMETERS */
                 aroundLatLng: Option[AroundLatLng] = None,
                 aroundLatLngViaIP: Option[Boolean] = None,
                 aroundRadius: Option[AroundRadius] = None,
                 aroundPrecision: Option[Int] = None,
                 minimumAroundRadius: Option[Int] = None,
                 insideBoundingBox: Option[InsideBoundingBox] = None,
                 insidePolygon: Option[InsidePolygon] = None,

                 /* SECURED API KEYS */
                 userToken: Option[String] = None,
                 validUntil: Option[Int] = None,
                 restrictIndices: Option[Seq[String]] = None,
                 restrictSources: Option[String] = None,

                 /* BROWSE */
                 cursor: Option[String] = None,

                 /* CUSTOM */
                 customParameters: Option[Map[String, String]] = None) {

  def toParam: String = {
    toQueryParam
      .map { case (k, v) => s"$k=${URLEncoder.encode(v, "UTF-8")}" }
      .mkString("&")
  }

  def toQueryParam: Map[String, String] = {
    val queryParam = Map(
      /* FULL TEXT SEARCH PARAMETERS */
      "query" -> query,
      "facetQuery" -> facetQuery,
      "queryType" -> queryType.map(_.name),
      "typoTolerance" -> typoTolerance.map(_.name),
      "minWordSizefor1Typo" -> minWordSizefor1Typo.map(_.toString),
      "minWordSizefor2Typos" -> minWordSizefor2Typos.map(_.toString),
      "allowTyposOnNumericTokens" -> allowTyposOnNumericTokens.map(_.toString),
      "ignorePlurals" -> ignorePlurals.map(_.value),
      "restrictSearchableAttributes" -> restrictSearchableAttributes.map(_.mkString(",")),
      "advancedSyntax" -> advancedSyntax.map(_.toString),
      "analytics" -> analytics.map(_.toString),
      "analyticsTags" -> analyticsTags.map(_.mkString(",")),
      "synonyms" -> synonyms.map(_.toString),
      "replaceSynonymsInHighlight" -> replaceSynonymsInHighlight.map(_.toString),
      "optionalWords" -> optionalWords.map(_.mkString(",")),
      "minProximity" -> minProximity.map(_.toString),
      "removeWordsIfNoResults" -> removeWordsIfNoResults.map(_.name),
      "disableTypoToleranceOnAttributes" -> disableTypoToleranceOnAttributes.map(_.mkString(",")),
      "removeStopWords" -> removeStopWords.map(_.value),
      "exactOnSingleWordQuery" -> exactOnSingleWordQuery.map(_.toString),
      "alternativesAsExact" -> alternativesAsExact.map(_.toString),

      /* PAGINATION PARAMETERS */
      "page" -> page.map(_.toString),
      "hitsPerPage" -> hitsPerPage.map(_.toString),

      /* PARAMETERS TO CONTROL RESULTS CONTENT */
      "attributesToRetrieve" -> attributesToRetrieve.map(_.mkString(",")),
      "attributesToHighlight" -> attributesToHighlight.map(_.mkString(",")),
      "attributesToSnippet" -> attributesToSnippet.map(_.map(e => s"${e._1}:${e._2}").mkString(",")),
      "getRankingInfo" -> getRankingInfo.map(_.toString),
      "highlightPreTag" -> highlightPreTag,
      "highlightPostTag" -> highlightPostTag,
      "snippetEllipsisText" -> snippetEllipsisText,

      /* NUMERIC SEARCH PARAMETERS */
      "numericFilters" -> numericFilters,

      /* CATEGORY SEARCH PARAMETER */
      "tagFilters" -> tagFilters.map(_.mkString(",")),

      /* DISTINCT PARAMETER */
      "distinct" -> distinct.map(_.value),

      "responseFields" -> responseFields.map(_.mkString(",")),

      /* FACETING PARAMETERS */
      "facets" -> facets.map(_.mkString(",")),
      "facetFilters" -> facetFilters.map(_.mkString(",")),
      "maxValuesPerFacet" -> maxValuesPerFacet.map(_.toString),

      /* UNIFIED FILTER PARAMETER (SQL LIKE) */
      "filters" -> filters,

      /* GEO-SEARCH PARAMETERS */
      "aroundLatLng" -> aroundLatLng.map(_.toString),
      "aroundLatLngViaIP" -> aroundLatLngViaIP.map(_.toString),
      "aroundRadius" -> aroundRadius.map(_.name),
      "aroundPrecision" -> aroundPrecision.map(_.toString),
      "minimumAroundRadius" -> minimumAroundRadius.map(_.toString),
      "insideBoundingBox" -> insideBoundingBox.map(_.toString),
      "insidePolygon" -> insidePolygon.map(_.toString),

      /* SECURED API KEYS */
      "userToken" -> userToken,
      "validUntil" -> validUntil.map(_.toString),
      "restrictIndices" -> restrictIndices.map(_.mkString(",")),
      "restrictSources" -> restrictSources,

      /* BROWSE */
      "cursor" -> cursor
    )
      .filter { case (_, v) => v.isDefined }
      .map { case (k, v) => k -> v.get }

    customParameters.fold(queryParam)(c => queryParam ++ c)
  }
}
