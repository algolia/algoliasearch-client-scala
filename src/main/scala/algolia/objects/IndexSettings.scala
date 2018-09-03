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

case class IndexSettings(
    /* Advanced */
    attributeForDistinct: Option[String] = None,
    distinct: Option[Distinct] = None,
    replaceSynonymsInHighlight: Option[Boolean] = None,
    placeholders: Option[Map[String, Seq[String]]] = None,
    minProximity: Option[Int] = None,
    keepDiacriticsOnCharacters: Option[String] = None,
    queryLanguages: Option[Seq[String]] = None,
    /* Attributes */
    attributesToIndex: Option[Seq[AttributesToIndex]] = None,
    searchableAttributes: Option[Seq[SearchableAttributes]] = None,
    attributesForFaceting: Option[Seq[String]] = None,
    unretrievableAttributes: Option[Seq[String]] = None,
    attributesToRetrieve: Option[Seq[String]] = None,
    /* Filtering faceting */
    maxValuesPerFacet: Option[Int] = None,
    sortFacetValuesBy: Option[String] = None,
    /* Geo Search */
    //Nothing in IndexSettings

    /* Highlighting Snippeting */
    attributesToHighlight: Option[Seq[String]] = None,
    attributesToSnippet: Option[Seq[String]] = None,
    highlightPreTag: Option[String] = None,
    highlightPostTag: Option[String] = None,
    snippetEllipsisText: Option[String] = None,
    restrictHighlightAndSnippetArrays: Option[Boolean] = None,
    /* Pagination */
    hitsPerPage: Option[Int] = None,
    paginationLimitedTo: Option[Int] = None,
    /* Performance */
    numericAttributesForFiltering: Option[Seq[String]] = None,
    numericAttributesToIndex: Option[Seq[NumericAttributesToIndex]] = None,
    allowCompressionOfIntegerArray: Option[Boolean] = None,
    /* Query rules */
    enableRules: Option[Boolean] = None,
    /* Query strategy */
    queryType: Option[QueryType] = None,
    removeWordsIfNoResults: Option[String] = None,
    advancedSyntax: Option[Boolean] = None,
    optionalWords: Option[Seq[String]] = None,
    removeStopWords: Option[RemoveStopWords] = None,
    disablePrefixOnAttributes: Option[Seq[String]] = None,
    disableExactOnAttributes: Option[Seq[String]] = None,
    exactOnSingleWordQuery: Option[Seq[String]] = None,
    alternativesAsExact: Option[Seq[String]] = None,
    /* Ranking */
    ranking: Option[Seq[Ranking]] = None,
    customRanking: Option[Seq[CustomRanking]] = None,
    slaves: Option[Seq[String]] = None,
    replicas: Option[Seq[String]] = None,
    /* Search */
    //Nothing in IndexSettings

    /* Typos */
    minWordSizefor1Typo: Option[Int] = None,
    minWordSizefor2Typos: Option[Int] = None,
    typoTolerance: Option[TypoTolerance] = None,
    allowTyposOnNumericTokens: Option[Boolean] = None,
    ignorePlurals: Option[IgnorePlurals] = None,
    disableTypoToleranceOnAttributes: Option[Seq[String]] = None,
    disableTypoToleranceOnWords: Option[Seq[String]] = None,
    separatorsToIndex: Option[String] = None
)
