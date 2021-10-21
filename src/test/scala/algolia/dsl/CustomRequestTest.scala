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

import algolia.AlgoliaDsl.custom
import algolia.AlgoliaTest
import algolia.http.{HttpPayload, POST}
import algolia.objects.{CustomRequest, RequestEndpoint}

class CustomRequestTest extends AlgoliaTest {
  describe("customRequest") {
    it("custom request ") {
      custom request CustomRequest(
        verb = POST,
        path = Seq("1", "indexes", "indexName", "query"),
        endpoint = RequestEndpoint.Search,
        body = Some("""{"query": "van"}""")
      )
    }
    it("should call search endpoint") {
      val json = """{"query":"van","hitsPerPage":1}"""
      (custom request CustomRequest(
        verb = POST,
        path = Seq("1", "indexes", "indexName", "query"),
        endpoint = RequestEndpoint.Search,
        body = Some(json)
      )).build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "indexName", "query"),
          body = Some(json),
          isSearch = true,
          requestOptions = None
        )
      )
    }

    it("should call indexing endpoint") {
      (custom request CustomRequest(
        verb = POST,
        path = Seq("1", "indexes", "indexName", "clear"),
        endpoint = RequestEndpoint.Indexing
      )).build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "indexName", "clear"),
          isSearch = false,
          requestOptions = None
        )
      )
    }

    it("should call analytics endpoint") {
      (custom request CustomRequest(
        verb = POST,
        path = Seq("2", "abtests", "42", "stop"),
        endpoint = RequestEndpoint.Analytics
      )).build() should be(
        HttpPayload(
          POST,
          Seq("2", "abtests", "42", "stop"),
          isSearch = false,
          isAnalytics = true,
          requestOptions = None
        )
      )
    }

    it("should call insights endpoint") {
      val json =
        """{"events":[{"eventType":"conversion","eventName":"event-name","index":"index-name","userToken":"user-token","queryID":"query-id","objectIDs":["objectID"]}]}"""
      (custom request CustomRequest(
        verb = POST,
        path = Seq("1", "events"),
        endpoint = RequestEndpoint.Insights,
        body = Some(json)
      )).build() should be(
        HttpPayload(
          verb = POST,
          path = Seq("1", "events"),
          body = Some(json),
          isSearch = false,
          isInsights = true,
          requestOptions = None
        )
      )
    }

    it("should call personalization endpoint") {
      val json =
        """{"eventsScoring":[{"eventName":"buy","eventType":"conversion","score":10}],"facetsScoring":[{"facetName":"brand","score":10}],"personalizationImpact":75}"""
      (custom request CustomRequest(
        verb = POST,
        path = Seq("1", "strategies", "personalization"),
        endpoint = RequestEndpoint.Personalization,
        body = Some(json)
      )).build() should be(
        HttpPayload(
          verb = POST,
          path = Seq("1", "strategies", "personalization"),
          body = Some(json),
          isSearch = false,
          isPersonalization = true,
          requestOptions = None
        )
      )
    }
  }
}
