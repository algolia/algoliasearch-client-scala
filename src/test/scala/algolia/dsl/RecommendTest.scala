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
import algolia.http.{HttpPayload, POST}
import algolia.objects.{
  FrequentlyBoughtTogetherQuery,
  Query,
  RecommendationsQuery,
  RelatedProductsQuery
}

class RecommendTest extends AlgoliaTest {

  describe("test recommend payload") {

    it("should produce valid recommendation query payload") {
      (get recommendations RecommendationsQuery(
        indexName = "products",
        model = "bought-together",
        objectID = "B018APC4LE",
        maxRecommendations = Some(10),
        queryParameters = Some(Query(attributesToRetrieve = Some(Seq("*"))))
      )).build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "*", "recommendations"),
          body = Some(
            "{\"indexName\":\"products\",\"model\":\"bought-together\",\"objectID\":\"B018APC4LE\",\"threshold\":0,\"maxRecommendations\":10,\"queryParameters\":{\"attributesToRetrieve\":[\"*\"]}}"
          ),
          isSearch = true,
          requestOptions = None
        )
      )
    }

    it("should produce valid related products query payload") {
      (get relatedProducts RelatedProductsQuery(
        indexName = "products",
        objectID = "B018APC4LE",
        threshold = 10,
        maxRecommendations = Some(10),
        queryParameters = Some(Query(attributesToRetrieve = Some(Seq("*"))))
      )).build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "*", "recommendations"),
          body = Some(
            "{\"indexName\":\"products\",\"objectID\":\"B018APC4LE\",\"threshold\":10,\"maxRecommendations\":10,\"queryParameters\":{\"attributesToRetrieve\":[\"*\"]},\"model\":\"related-products\"}"
          ),
          isSearch = true,
          requestOptions = None
        )
      )
    }

    it("should produce valid frequently bought together query payload") {
      (get frequentlyBoughtTogether FrequentlyBoughtTogetherQuery(
        indexName = "products",
        objectID = "B018APC4LE",
        threshold = 10,
        maxRecommendations = Some(10),
        queryParameters = Some(Query(attributesToRetrieve = Some(Seq("*"))))
      )).build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "*", "recommendations"),
          body = Some(
            "{\"indexName\":\"products\",\"objectID\":\"B018APC4LE\",\"threshold\":10,\"maxRecommendations\":10,\"queryParameters\":{\"attributesToRetrieve\":[\"*\"]},\"model\":\"bought-together\"}"
          ),
          isSearch = true,
          requestOptions = None
        )
      )
    }
  }
}
