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
import algolia.objects.Dictionary.{Compounds, Plurals, Stopwords}
import algolia.objects._

import java.util.UUID
import scala.language.postfixOps

class DictionaryIntegrationTest extends AlgoliaTest {

  private val client = AlgoliaTest.client2

  describe("Stop word dictionary") {
    val id = UUID.randomUUID().toString
    val entry = StopwordEntry(id, "en", "upper", "enabled")

    it("save stop word entries") {
      val r = client.execute {
        save dictionary Stopwords entries Seq(entry)
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
        delete dictionary Stopwords objectIDs Seq(id)
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

    it("clear stop words entries") {
      val r = client.execute {
        clear dictionary Stopwords
      }

      appTaskShouldBeCreatedAndWaitForIt(client, r)

      whenReady(r) { res =>
        res.updatedAt shouldNot be(None)
      }
    }
  }

  describe("Plurals dictionary") {
    val id = UUID.randomUUID().toString
    val entry = PluralEntry(id, "en", Seq("cheval", "chevaux"))

    it("save plural entries") {

      val r = client.execute {
        replace dictionary Plurals entries Seq(entry)
      }

      appTaskShouldBeCreatedAndWaitForIt(client, r)

      whenReady(r) { res =>
        res.updatedAt shouldNot be(None)
      }
    }

    it("search plural entries") {
      val r = client.execute {
        search into Plurals query QueryDictionary(query = Some(id))
      }

      whenReady(r) { res =>
        res.hits shouldNot have size 0
        res.asEntry[PluralEntry].head should be(entry)
      }
    }

    it("delete plural entries") {

      val r = client.execute {
        delete dictionary Plurals objectIDs Seq(id)
      }

      appTaskShouldBeCreatedAndWaitForIt(client, r)

      whenReady(r) { res =>
        res.updatedAt shouldNot be(None)
      }
    }
  }

  describe("Compounds dictionary") {
    val id = UUID.randomUUID().toString
    val entry = CompoundEntry(
      id,
      "nl",
      "kopfschmerztablette",
      Seq("kopf", "schmerz", "tablette")
    )

    it("save compound entries") {

      val r = client.execute {
        replace dictionary Compounds entries Seq(entry)
      }

      appTaskShouldBeCreatedAndWaitForIt(client, r)

      whenReady(r) { res =>
        res.updatedAt shouldNot be(None)
      }
    }

    it("search compound entries") {
      val r = client.execute {
        search into Compounds query QueryDictionary(query = Some(id))
      }

      whenReady(r) { res =>
        res.hits shouldNot have size 0
        res.asEntry[CompoundEntry].head should be(entry)
      }
    }

    it("delete compound entries") {

      val r = client.execute {
        delete dictionary Compounds objectIDs Seq(id)
      }

      appTaskShouldBeCreatedAndWaitForIt(client, r)

      whenReady(r) { res =>
        res.updatedAt shouldNot be(None)
      }
    }
  }

  describe("Dictionary Settings") {

    val settings = DictionarySettings(
      Some(
        DisableStandardEntries(
          stopwords = Some(Map("en" -> true))
        )
      )
    )

    it("set settings") {
      val r = client.execute {
        set dictionarySettings settings
      }

      appTaskShouldBeCreatedAndWaitForIt(client, r)

      whenReady(r) { res =>
        res.updatedAt shouldNot be(None)
      }
    }

    it("get settings") {
      val r = client.execute {
        get dictionarySettings
      }

      whenReady(r) { res =>
        res should be(settings)
      }
    }
  }
}
