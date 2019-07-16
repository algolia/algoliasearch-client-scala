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
import algolia.inputs._

class SendTest extends AlgoliaTest {

  describe("send event payloads") {

    it("should produce ClickedFilters payload") {
      (send event ClickedFilters("user-token", "event-name", "index-name", Seq("filter")))
        .build() should be(
        HttpPayload(
          POST,
          Seq("1", "events"),
          body = Some(
            """{"events":[{"eventType":"click","eventName":"event-name","index":"index-name","userToken":"user-token","filters":["filter"]}]}"""),
          isSearch = false,
          isInsights = true,
          requestOptions = None
        )
      )
    }

    it("should produce ClickedObjectIDs payload") {
      (send event ClickedObjectIDs("user-token", "event-name", "index-name", Seq("objectID")))
        .build() should be(
        HttpPayload(
          POST,
          Seq("1", "events"),
          body = Some(
            """{"events":[{"eventType":"click","eventName":"event-name","index":"index-name","userToken":"user-token","objectIDs":["objectID"]}]}"""),
          isSearch = false,
          isInsights = true,
          requestOptions = None
        )
      )
    }

    it("should produce ClickedObjectIDsAfterSearch payload") {
      (send event ClickedObjectIDsAfterSearch("user-token",
                                              "event-name",
                                              "index-name",
                                              Seq("objectID"),
                                              Seq(42),
                                              "query-id")).build() should be(
        HttpPayload(
          POST,
          Seq("1", "events"),
          body = Some(
            """{"events":[{"eventType":"click","eventName":"event-name","index":"index-name","userToken":"user-token","objectIDs":["objectID"],"positions":[42],"queryID":"query-id"}]}"""),
          isSearch = false,
          isInsights = true,
          requestOptions = None
        )
      )
    }

    it("should produce ConvertedObjectIDs payload") {
      (send event ConvertedObjectIDs("user-token", "event-name", "index-name", Seq("objectID")))
        .build() should be(
        HttpPayload(
          POST,
          Seq("1", "events"),
          body = Some(
            """{"events":[{"eventType":"conversion","eventName":"event-name","index":"index-name","userToken":"user-token","objectIDs":["objectID"]}]}"""),
          isSearch = false,
          isInsights = true,
          requestOptions = None
        )
      )
    }

    it("should produce ConvertedObjectIDsAfterSearch payload") {
      (send event ConvertedObjectIDsAfterSearch("user-token",
                                                "event-name",
                                                "index-name",
                                                Seq("objectID"),
                                                "query-id")).build() should be(
        HttpPayload(
          POST,
          Seq("1", "events"),
          body = Some(
            """{"events":[{"eventType":"conversion","eventName":"event-name","index":"index-name","userToken":"user-token","objectIDs":["objectID"],"queryID":"query-id"}]}"""),
          isSearch = false,
          isInsights = true,
          requestOptions = None
        )
      )
    }

    it("should produce ConvertedFilters payload") {
      (send event ConvertedFilters("user-token", "event-name", "index-name", Seq("filter")))
        .build() should be(
        HttpPayload(
          POST,
          Seq("1", "events"),
          body = Some(
            """{"events":[{"eventType":"conversion","eventName":"event-name","index":"index-name","userToken":"user-token","filters":["filter"]}]}"""),
          isSearch = false,
          isInsights = true,
          requestOptions = None
        )
      )
    }

    it("should produce ViewedFilters payload") {
      (send event ViewedFilters("user-token", "event-name", "index-name", Seq("filter")))
        .build() should be(
        HttpPayload(
          POST,
          Seq("1", "events"),
          body = Some(
            """{"events":[{"eventType":"view","eventName":"event-name","index":"index-name","userToken":"user-token","filters":["filter"]}]}"""),
          isSearch = false,
          isInsights = true,
          requestOptions = None
        )
      )
    }

    it("should produce ViewedObjectIDs payload") {
      (send event ViewedObjectIDs("user-token", "event-name", "index-name", Seq("objectID")))
        .build() should be(
        HttpPayload(
          POST,
          Seq("1", "events"),
          body = Some(
            """{"events":[{"eventType":"view","eventName":"event-name","index":"index-name","userToken":"user-token","objectIDs":["objectID"]}]}"""),
          isSearch = false,
          isInsights = true,
          requestOptions = None
        )
      )
    }

  }
}
