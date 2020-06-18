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
import algolia.objects.Synonym.{AltCorrection1, OneWaySynonym}
import algolia.objects.{QuerySynonyms, SynonymType}
import algolia.{AlgoliaDsl, AlgoliaTest}

class SynonymIntegrationTest extends AlgoliaTest {

  val baseIndexName: String = getTestIndexName("indexToSynonym")

  describe("synonyms") {

    it("should save a synonym") {
      val indexName = baseIndexName + "_save"
      
      val res = AlgoliaTest.client.execute {
        save synonym AltCorrection1("altcorrection1", "p", Seq("a", "b")) inIndex indexName
      }

      taskShouldBeCreatedAndWaitForIt(res, indexName)

      val r = AlgoliaTest.client.execute {
        get synonym "altcorrection1" from indexName
      }

      whenReady(r) { res =>
        res.objectID should be("altcorrection1")
        res.`type` should be(SynonymType.altCorrection1)
      }
    }

    it("should search synonyms") {
      val indexName = baseIndexName + "_search"
      
      val res1 = AlgoliaTest.client.execute {
        save synonym AltCorrection1("altcorrection1", "p", Seq("a", "b")) inIndex indexName
      }

      taskShouldBeCreatedAndWaitForIt(res1, indexName)

      val res2 = AlgoliaTest.client.execute {
        save synonym OneWaySynonym("one-way", "a", Seq("b")) inIndex indexName
      }

      taskShouldBeCreatedAndWaitForIt(res2, indexName)

      val s = AlgoliaTest.client.execute {
        search synonyms in index indexName query QuerySynonyms(
          "altcorrection1",
          hitsPerPage = Some(10)
        )
      }

      whenReady(s) { res =>
        res.hits should have size 1
      }
    }

    it("should delete synonym") {
      val indexName = baseIndexName + "_delete"
      
      val res1 = AlgoliaTest.client.execute {
        save synonym AltCorrection1("altcorrection1", "p", Seq("a", "b")) inIndex indexName
      }

      taskShouldBeCreatedAndWaitForIt(res1, indexName)

      val res2 = AlgoliaTest.client.execute {
        save synonym OneWaySynonym("one-way", "a", Seq("b")) inIndex indexName
      }

      taskShouldBeCreatedAndWaitForIt(res2, indexName)

      val d = AlgoliaTest.client.execute {
        delete synonym "one-way" from indexName
      }

      taskShouldBeCreatedAndWaitForIt(d, indexName)

      val s = AlgoliaTest.client.execute {
        search synonyms in index indexName query QuerySynonyms(
          "",
          hitsPerPage = Some(10)
        )
      }

      whenReady(s) { res =>
        res.hits should have size 1
      }
    }

    it("should clear synonyms") {
      val indexName = baseIndexName + "_clear"
      
      val res1 = AlgoliaTest.client.execute {
        save synonym AltCorrection1("altcorrection1", "p", Seq("a", "b")) inIndex indexName
      }

      taskShouldBeCreatedAndWaitForIt(res1, indexName)

      val res2 = AlgoliaTest.client.execute {
        save synonym OneWaySynonym("one-way", "a", Seq("b")) inIndex indexName
      }

      taskShouldBeCreatedAndWaitForIt(res2, indexName)

      val d = AlgoliaTest.client.execute {
        clear synonyms AlgoliaDsl.of index indexName
      }

      taskShouldBeCreatedAndWaitForIt(d, indexName)

      val s = AlgoliaTest.client.execute {
        search synonyms in index indexName query QuerySynonyms(
          "",
          hitsPerPage = Some(10)
        )
      }

      whenReady(s) { res =>
        res.hits should have size 0
      }
    }

    it("should batch insert synonyms") {
      val indexName = baseIndexName + "_batch"
      
      val synonymsToInsert = Seq(
        AltCorrection1("altcorrection1", "p", Seq("a", "b")),
        OneWaySynonym("one-way", "a", Seq("b"))
      )

      val res1 = AlgoliaTest.client.execute {
        save synonyms synonymsToInsert inIndex indexName
      }

      taskShouldBeCreatedAndWaitForIt(res1, indexName)

      val s = AlgoliaTest.client.execute {
        search synonyms in index indexName query QuerySynonyms(
          "",
          hitsPerPage = Some(10)
        )
      }

      whenReady(s) { res =>
        res.hits should have size 2
      }
    }

  }

}
