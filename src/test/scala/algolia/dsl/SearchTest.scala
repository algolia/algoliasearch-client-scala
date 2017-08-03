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

package algolia.dsl

import algolia.AlgoliaDsl._
import algolia.AlgoliaTest
import algolia.http._
import algolia.objects._

class SearchTest extends AlgoliaTest {

  describe("search") {

    it("should search") {
      search into "indexName" query Query()
    }

    it("should call the API") {
      (search into "indexName" query Query(query = Some("a")))
        .build() should be(
        HttpPayload(
          POST,
          List("1", "indexes", "indexName", "query"),
          body = Some("""{"params":"query=a"}"""),
          isSearch = true,
          requestOptions = None
        )
      )
    }

    it("should call the API with a full search") {
      val q = Query(
        query = Some("query"),
        queryType = Some(QueryType.prefixAll),
        typoTolerance = Some(TypoTolerance.strict),
        minWordSizefor1Typo = Some(1),
        minWordSizefor2Typos = Some(2),
        allowTyposOnNumericTokens = Some(true),
        ignorePlurals = Some(IgnorePlurals.`false`),
        restrictSearchableAttributes = Some(Seq("att1", "att2")),
        advancedSyntax = Some(true),
        analytics = Some(true),
        analyticsTags = Some(Seq("a", "b")),
        synonyms = Some(true),
        replaceSynonymsInHighlight = Some(false),
        optionalWords = Some(Seq("le", "la")),
        minProximity = Some(10),
        removeWordsIfNoResults = Some(RemoveWordsIfNoResults.allOptional),
        disableTypoToleranceOnAttributes = Some(Seq("att2", "att3")),
        removeStopWords = Some(RemoveStopWords.`false`),
        exactOnSingleWordQuery = Some(Seq("e", "a")),
        alternativesAsExact = Some(Seq("true", "false")),
        page = Some(1),
        hitsPerPage = Some(19),
        attributesToRetrieve = Some(Seq("att4")),
        attributesToHighlight = Some(Seq("att5")),
        attributesToSnippet = Some(Seq("att6:1")),
        getRankingInfo = Some(true),
        highlightPreTag = Some("<em>"),
        highlightPostTag = Some("</em>"),
        snippetEllipsisText = Some("…"),
        numericFilters = Some(Seq("1", "2")),
        tagFilters = Some(Seq("tag1")),
        distinct = Some(Distinct.int(1)),
        facets = Some(Seq("facet1")),
        facetFilters = Some(Seq("facet2")),
        maxValuesPerFacet = Some(1),
        filters = Some("filter"),
        aroundLatLng = Some(AroundLatLng("1", "2")),
        aroundLatLngViaIP = Some(true),
        aroundRadius = Some(AroundRadius.integer(0)),
        aroundPrecision = Some(20),
        minimumAroundRadius = Some(30),
        insideBoundingBox = Some(Seq(InsideBoundingBox("1", "2", "3", "4"))),
        insidePolygon = Some(Seq(InsidePolygon("1", "2", "3", "4", "5", "6"))),
        userToken = Some("userToken"),
        responseFields = Some(Seq("att7", "att8"))
      )

      (search into "indexName" query q).build() should be(
        HttpPayload(
          POST,
          List("1", "indexes", "indexName", "query"),
          body = Some(
            """{"params":"numericFilters=1%2C2&alternativesAsExact=true%2Cfalse&attributesToRetrieve=att4&advancedSyntax=true&synonyms=true&tagFilters=tag1&disableTypoToleranceOnAttributes=att2%2Catt3&snippetEllipsisText=%E2%80%A6&restrictSearchableAttributes=att1%2Catt2&userToken=userToken&responseFields=att7%2Catt8&facetFilters=facet2&aroundLatLngViaIP=true&allowTyposOnNumericTokens=true&minWordSizefor2Typos=2&optionalWords=le%2Cla&page=1&minimumAroundRadius=30&aroundLatLng=1%2C2&analyticsTags=a%2Cb&query=query&ignorePlurals=false&getRankingInfo=true&highlightPreTag=%3Cem%3E&aroundPrecision=20&maxValuesPerFacet=1&attributesToSnippet=att6%3A1&exactOnSingleWordQuery=e%2Ca&replaceSynonymsInHighlight=false&aroundRadius=0&filters=filter&distinct=1&minWordSizefor1Typo=1&analytics=true&typoTolerance=strict&insidePolygon=%5B%5B1%2C2%2C3%2C4%2C5%2C6%5D%5D&hitsPerPage=19&queryType=prefixAll&facets=facet1&minProximity=10&insideBoundingBox=%5B%5B1%2C2%2C3%2C4%5D%5D&removeStopWords=false&attributesToHighlight=att5&removeWordsIfNoResults=allOptional&highlightPostTag=%3C%2Fem%3E"}"""),
          isSearch = true,
          requestOptions = None
        )
      )
    }

    it("should call the API with a search with custom parameters") {
      val q = Query(
        query = Some("query"),
        customParameters = Some(Map("my" -> "parameter"))
      )

      (search into "indexName" query q).build() should be(
        HttpPayload(
          POST,
          List("1", "indexes", "indexName", "query"),
          body = Some("""{"params":"query=query&my=parameter"}"""),
          isSearch = true,
          requestOptions = None
        )
      )
    }
  }

  describe("Query toParam") {

    it("should parametrize stuff") {
      val q = Query(
        query = Some("a&b"),
        queryType = Some(QueryType.prefixAll)
      )

      q.toParam should be("query=a%26b&queryType=prefixAll")
    }

  }

  describe("search in facet") {

    it("should search in facet") {
      search into "indexName" facet "facetName" values "facetQuery" query Query()
    }

    it("should call the API") {
      (search into "indexName" facet "facetName" values "facetQuery" query Query(
        query = Some("a"))).build() should be(
        HttpPayload(
          POST,
          List("1", "indexes", "indexName", "facets", "facetName", "query"),
          body = Some("""{"params":"facetQuery=facetQuery&query=a"}"""),
          isSearch = true,
          requestOptions = None
        )
      )
    }

    it("should call the API with a full search") {
      val q = Query(
        query = Some("query"),
        facetQuery = Some("facetQuery"),
        queryType = Some(QueryType.prefixAll),
        typoTolerance = Some(TypoTolerance.strict),
        minWordSizefor1Typo = Some(1),
        minWordSizefor2Typos = Some(2),
        allowTyposOnNumericTokens = Some(true),
        ignorePlurals = Some(IgnorePlurals.`false`),
        restrictSearchableAttributes = Some(Seq("att1", "att2")),
        advancedSyntax = Some(true),
        analytics = Some(true),
        analyticsTags = Some(Seq("a", "b")),
        synonyms = Some(true),
        replaceSynonymsInHighlight = Some(false),
        optionalWords = Some(Seq("le", "la")),
        minProximity = Some(10),
        removeWordsIfNoResults = Some(RemoveWordsIfNoResults.allOptional),
        disableTypoToleranceOnAttributes = Some(Seq("att2", "att3")),
        removeStopWords = Some(RemoveStopWords.`false`),
        exactOnSingleWordQuery = Some(Seq("e", "a")),
        alternativesAsExact = Some(Seq("true", "false")),
        page = Some(1),
        hitsPerPage = Some(19),
        attributesToRetrieve = Some(Seq("att4")),
        attributesToHighlight = Some(Seq("att5")),
        attributesToSnippet = Some(Seq("att6:1")),
        getRankingInfo = Some(true),
        highlightPreTag = Some("<em>"),
        highlightPostTag = Some("</em>"),
        snippetEllipsisText = Some("…"),
        numericFilters = Some(Seq("1", "2")),
        tagFilters = Some(Seq("tag1")),
        distinct = Some(Distinct.int(1)),
        facets = Some(Seq("facet1")),
        facetFilters = Some(Seq("facet2")),
        maxValuesPerFacet = Some(1),
        filters = Some("filter"),
        aroundLatLng = Some(AroundLatLng("1", "2")),
        aroundLatLngViaIP = Some(true),
        aroundRadius = Some(AroundRadius.integer(0)),
        aroundPrecision = Some(20),
        minimumAroundRadius = Some(30),
        insideBoundingBox = Some(Seq(InsideBoundingBox("1", "2", "3", "4"))),
        insidePolygon = Some(Seq(InsidePolygon("1", "2", "3", "4", "5", "6"))),
        userToken = Some("userToken")
      )

      (search into "indexName" query q).build() should be(
        HttpPayload(
          POST,
          List("1", "indexes", "indexName", "query"),
          body = Some(
            """{"params":"numericFilters=1%2C2&alternativesAsExact=true%2Cfalse&attributesToRetrieve=att4&advancedSyntax=true&synonyms=true&tagFilters=tag1&disableTypoToleranceOnAttributes=att2%2Catt3&snippetEllipsisText=%E2%80%A6&restrictSearchableAttributes=att1%2Catt2&userToken=userToken&facetFilters=facet2&aroundLatLngViaIP=true&allowTyposOnNumericTokens=true&minWordSizefor2Typos=2&facetQuery=facetQuery&optionalWords=le%2Cla&page=1&minimumAroundRadius=30&aroundLatLng=1%2C2&analyticsTags=a%2Cb&query=query&ignorePlurals=false&getRankingInfo=true&highlightPreTag=%3Cem%3E&aroundPrecision=20&maxValuesPerFacet=1&attributesToSnippet=att6%3A1&exactOnSingleWordQuery=e%2Ca&replaceSynonymsInHighlight=false&aroundRadius=0&filters=filter&distinct=1&minWordSizefor1Typo=1&analytics=true&typoTolerance=strict&insidePolygon=%5B%5B1%2C2%2C3%2C4%2C5%2C6%5D%5D&hitsPerPage=19&queryType=prefixAll&facets=facet1&minProximity=10&insideBoundingBox=%5B%5B1%2C2%2C3%2C4%5D%5D&removeStopWords=false&attributesToHighlight=att5&removeWordsIfNoResults=allOptional&highlightPostTag=%3C%2Fem%3E"}"""),
          isSearch = true,
          requestOptions = None
        )
      )
    }

    it("should call the API with a search with custom parameters") {
      val q = Query(
        query = Some("query"),
        customParameters = Some(Map("my" -> "parameter"))
      )

      (search into "indexName" query q).build() should be(
        HttpPayload(
          POST,
          List("1", "indexes", "indexName", "query"),
          body = Some("""{"params":"query=query&my=parameter"}"""),
          isSearch = true,
          requestOptions = None
        )
      )
    }
  }

}
