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

package algolia.integration

import algolia.AlgoliaDsl._
import algolia.AlgoliaTest
import algolia.objects.Dictionary.Stopwords
import algolia.objects._

import java.util.UUID

class DictionaryIntegrationTest extends AlgoliaTest {

  describe("dictionaries") {

    val client = AlgoliaTest.client2
    val id = UUID.randomUUID().toString
    val stopwords = Seq(StopwordEntry("MyObjectID", "en", "upper", "enabled"))

    it("should save stop word entries") {
      val r = client.execute {
        save dictionary Stopwords entries stopwords
      }

      appTaskShouldBeCreatedAndWaitForIt(client, r)

      whenReady(r) { res =>
        res.updatedAt shouldNot be(None)
      }
    }

    it("should search for stop words entries") {
      val r = client.execute {
        search into Stopwords query QueryDictionary(query = Some("MyObjectID"))
      }

      whenReady(r) { res =>
        println(res)
        res.hits shouldNot have size 0
      }
    }
  }
}
