/*
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
import algolia.objects.{QuerySynonyms, Synonym, SynonymType}
import algolia.{AlgoliaDsl, AlgoliaTest}

import scala.concurrent.ExecutionContext.Implicits.global

class SynonymIntegrationTest extends AlgoliaTest {

  after {
    clearIndices("indexToSynonym")
  }

  describe("synonyms") {

    it("should save a synonym") {
      val res = client.execute {
        save synonym AltCorrection1("altcorrection1", "p", Seq("a", "b")) inIndex "indexToSynonym"
      }

      taskShouldBeCreatedAndWaitForIt(res, "indexToSynonym")

      val r = client.execute {
        get synonym "altcorrection1" from "indexToSynonym"
      }

      whenReady(r) { res =>
        res.objectID should be("altcorrection1")
        res.`type` should be(SynonymType.altCorrection1)
      }
    }

    it("should search synonyms") {
      val res1 = client.execute {
        save synonym AltCorrection1("altcorrection1", "p", Seq("a", "b")) inIndex "indexToSynonym"
      }

      taskShouldBeCreatedAndWaitForIt(res1, "indexToSynonym")

      val res2 = client.execute {
        save synonym OneWaySynonym("one-way", "a", Seq("b")) inIndex "indexToSynonym"
      }

      taskShouldBeCreatedAndWaitForIt(res2, "indexToSynonym")

      val s = client.execute {
        search synonyms in index "indexToSynonym" query QuerySynonyms("altcorrection1", hitsPerPage = Some(10))
      }

      whenReady(s) { res =>
        res.hits should have size 1
      }
    }

    it("should delete synonym") {
      val res1 = client.execute {
        save synonym AltCorrection1("altcorrection1", "p", Seq("a", "b")) inIndex "indexToSynonym"
      }

      taskShouldBeCreatedAndWaitForIt(res1, "indexToSynonym")

      val res2 = client.execute {
        save synonym OneWaySynonym("one-way", "a", Seq("b")) inIndex "indexToSynonym"
      }

      taskShouldBeCreatedAndWaitForIt(res2, "indexToSynonym")

      val d = client.execute {
        delete synonym "one-way" from "indexToSynonym"
      }

      taskShouldBeCreatedAndWaitForIt(d, "indexToSynonym")

      val s = client.execute {
        search synonyms in index "indexToSynonym" query QuerySynonyms("", hitsPerPage = Some(10))
      }

      whenReady(s) { res =>
        res.hits should have size 1
      }
    }

    it("should clear synonyms") {
      val res1 = client.execute {
        save synonym AltCorrection1("altcorrection1", "p", Seq("a", "b")) inIndex "indexToSynonym"
      }

      taskShouldBeCreatedAndWaitForIt(res1, "indexToSynonym")

      val res2 = client.execute {
        save synonym OneWaySynonym("one-way", "a", Seq("b")) inIndex "indexToSynonym"
      }

      taskShouldBeCreatedAndWaitForIt(res2, "indexToSynonym")

      val d = client.execute {
        clear synonyms AlgoliaDsl.of index "indexToSynonym"
      }

      taskShouldBeCreatedAndWaitForIt(d, "indexToSynonym")

      val s = client.execute {
        search synonyms in index "indexToSynonym" query QuerySynonyms("", hitsPerPage = Some(10))
      }

      whenReady(s) { res =>
        res.hits should have size 0
      }
    }

    it("should batch insert synonyms") {
      val synonymsToInsert = Seq(
        AltCorrection1("altcorrection1", "p", Seq("a", "b")),
        OneWaySynonym("one-way", "a", Seq("b"))
      )

      val res1 = client.execute {
        save synonyms synonymsToInsert inIndex "indexToSynonym"
      }

      taskShouldBeCreatedAndWaitForIt(res1, "indexToSynonym")

      val s = client.execute {
        search synonyms in index "indexToSynonym" query QuerySynonyms("", hitsPerPage = Some(10))
      }

      whenReady(s) { res =>
        res.hits should have size 2
      }
    }

  }


}
