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
import algolia.http.{HttpPayload, POST}
import algolia.objects.{EventsScoring, FacetsScoring, SetStrategyRequest}
import algolia.{AlgoliaClient, AlgoliaTest}

class PersonalizationTest extends AlgoliaTest {
  describe("test personalization payload") {
    it("should produce valid payload") {
      (set strategy (
        SetStrategyRequest(
          Seq[EventsScoring](
            EventsScoring("buy", "conversion", 10),
            EventsScoring("add to cart", "conversion", 20)
          ),
          Seq[FacetsScoring](
            FacetsScoring("brand", 10),
            FacetsScoring("category", 20)
          ),
          75
        )
      )).build() should be(
        HttpPayload(
          POST,
          Seq("1", "strategies", "personalization"),
          body = Some(
            "{\"eventsScoring\":[{\"eventName\":\"buy\",\"eventType\":\"conversion\",\"score\":10},{\"eventName\":\"add to cart\",\"eventType\":\"conversion\",\"score\":20}],\"facetsScoring\":[{\"facetName\":\"brand\",\"score\":10},{\"facetName\":\"category\",\"score\":20}],\"personalizationImpact\":75}"
          ),
          isSearch = false,
          isPersonalization = true,
          requestOptions = None
        )
      )
    }

    it("should override default 'us' host") {
      val personalizationClient = new AlgoliaClient("appID", "apiKEY")
      personalizationClient.personalizationHost =
        "https://personalization.eu.algolia.com"
      personalizationClient.personalizationHost shouldEqual "https://personalization.eu.algolia.com"
    }
  }

}
