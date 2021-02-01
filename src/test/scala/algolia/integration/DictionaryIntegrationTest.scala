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
    val entry = StopwordEntry(id, "en", "upper", "enabled")
    val stopwords = Seq(entry)

    it("save stop word entries") {
      val r = client.execute {
        save dictionary Stopwords entries stopwords
      }

      appTaskShouldBeCreatedAndWaitForIt(client, r)

      whenReady(r) { res =>
        res.updatedAt shouldNot be(None)
      }
    }

    it("search for stop words entries") {
      val r = client.execute {
        search into Stopwords query QueryDictionary(query = Some(id))
      }

      whenReady(r) { res =>
        res.hits shouldNot have size 0
      }
    }

    it("replace stop words entries") {
      val r = client.execute {
        replace dictionary Stopwords entries Seq(entry.copy(word = "uppercase"))
      }

      appTaskShouldBeCreatedAndWaitForIt(client, r)

      whenReady(r) { res =>
        res.updatedAt shouldNot be(None)
      }
    }

    it("search for updated stop words entries") {
      val r = client.execute {
        search into Stopwords query QueryDictionary(query = Some(id))
      }

      whenReady(r) { res =>
        res.hits shouldNot have size 0
        val entries = res.asEntry[StopwordEntry]
        entries shouldNot have size 0
        entries.head.word should be("uppercase")
      }
    }

    it("delete stop words entries") {
      val r = client.execute {
        delete dictionary Stopwords entries Seq(id)
      }

      appTaskShouldBeCreatedAndWaitForIt(client, r)

      whenReady(r) { res =>
        res.updatedAt shouldNot be(None)
      }
    }

    it("search for deleted stop words entries") {
      val r = client.execute {
        search into Stopwords query QueryDictionary(query = Some(id))
      }

      whenReady(r) { res =>
        res.nbHits shouldBe 0
      }
    }

    it("search for clear stop words entries") {
      val r = client.execute {
        clear dictionary Stopwords
      }

      appTaskShouldBeCreatedAndWaitForIt(client, r)

      whenReady(r) { res =>
        res.updatedAt shouldNot be(None)
      }
    }
  }
}
