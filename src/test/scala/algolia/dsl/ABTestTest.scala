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

import java.time.LocalDateTime

import algolia.AlgoliaDsl._
import algolia.AlgoliaTest
import algolia.http.{DELETE, GET, HttpPayload, POST}
import algolia.inputs.{ABTest, ABTestVariant}

import scala.language.postfixOps

class ABTestTest extends AlgoliaTest {

  describe("Build AB test HTTP payloads") {

    it("should build an 'add AB test' payload") {
      val abTest: ABTest = ABTest(
        "my_abtest",
        Seq(
          ABTestVariant("index1", 60, Some("this is the main index")),
          ABTestVariant("index2", 40, None)
        ),
        LocalDateTime.of(2018, 6, 14, 13, 42, 43)
      )

      (add abTest abTest).build() should be(
        HttpPayload(
          verb = POST,
          path = Seq("2", "abtests"),
          isSearch = false,
          isAnalytics = true,
          requestOptions = None,
          body = Some(
            """{"name":"my_abtest","variants":[{"index":"index1","trafficPercentage":60,"description":"this is the main index"},{"index":"index2","trafficPercentage":40}],"endAt":"2018-06-14T13:42:43Z"}"""
          )
        )
      )
    }

    it("should build a 'get AB test' payload") {
      (get abTest 42).build() should be(
        HttpPayload(
          verb = GET,
          path = Seq("2", "abtests", "42"),
          isSearch = false,
          isAnalytics = true,
          requestOptions = None
        )
      )
    }

    it("should build a 'stop AB test' payload") {
      (stop abTest 42).build() should be(
        HttpPayload(
          verb = POST,
          path = Seq("2", "abtests", "42", "stop"),
          isSearch = false,
          isAnalytics = true,
          requestOptions = None
        )
      )
    }

    it("should build a 'delete AB test' payload") {
      (delete abTest 42).build() should be(
        HttpPayload(
          verb = DELETE,
          path = Seq("2", "abtests", "42"),
          isSearch = false,
          isAnalytics = true,
          requestOptions = None
        )
      )
    }

    it("should build a 'get AB tests' payload") {
      (get all abTests).build() should be(
        HttpPayload(
          verb = GET,
          path = Seq("2", "abtests"),
          queryParameters = Some(Map("offset" -> "0", "limit" -> "10")),
          isSearch = false,
          isAnalytics = true,
          requestOptions = None
        )
      )
    }

    it("should build a 'get AB tests' payload with extra parameters") {
      (get all abTests offset 3 limit 4).build() should be(
        HttpPayload(
          verb = GET,
          path = Seq("2", "abtests"),
          queryParameters = Some(Map("offset" -> "3", "limit" -> "4")),
          isSearch = false,
          isAnalytics = true,
          requestOptions = None
        )
      )
    }

  }
}
