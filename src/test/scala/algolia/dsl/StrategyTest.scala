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
import algolia.http.{GET, HttpPayload, POST}
import algolia.objects.{Score, ScoreType, Strategy}

import scala.language.postfixOps

class StrategyTest extends AlgoliaTest {

  describe("personalization strategy payloads") {

    it("should produce empty set strategy payload") {
      (set personalizationStrategy Strategy()).build() should be(
        HttpPayload(
          POST,
          Seq("1", "recommendation", "personalization", "strategy"),
          body = Some("{}"),
          isSearch = false,
          requestOptions = None
        )
      )
    }

    it("should produce non-empty set strategy payload") {
      val strategy = Strategy(
        eventsScoring = Some(
          Map(
            "event 1" -> ScoreType(10, "click"),
            "event 2" -> ScoreType(20, "conversion")
          )),
        facetsScoring = Some(
          Map(
            "event 3" -> Score(30)
          ))
      )

      (set personalizationStrategy strategy).build() should be(
        HttpPayload(
          POST,
          Seq("1", "recommendation", "personalization", "strategy"),
          body = Some(
            """{"eventsScoring":{"event 1":{"score":10,"type":"click"},"event 2":{"score":20,"type":"conversion"}},"facetsScoring":{"event 3":{"score":30}}}"""),
          isSearch = false,
          requestOptions = None
        )
      )
    }

    it("should produce get strategy payload") {
      (get personalizationStrategy).build() should be(
        HttpPayload(
          GET,
          Seq("1", "recommendation", "personalization", "strategy"),
          body = None,
          isSearch = true,
          requestOptions = None
        )
      )
    }

  }
}
