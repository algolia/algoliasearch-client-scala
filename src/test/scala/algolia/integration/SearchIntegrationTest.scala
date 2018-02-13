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
import algolia.objects.{IndexSettings, InsideBoundingBox, InsidePolygon, Query}
import algolia.responses._
import org.json4s._

class SearchIntegrationTest extends AlgoliaTest {

  val indexName = s"indexToSearch_${util.Properties.versionNumberString}"

  after {
    clearIndices(
      indexName,
      "indexToSearch2",
      "indexToSearchFacet"
    )
  }

  before {
    val obj = Test("algolia", 10, alien = false)
    val insert1 = client.execute {
      index into indexName objectId "563481290" `object` obj
    }

    taskShouldBeCreatedAndWaitForIt(insert1, indexName)

    val insert2 = client.execute {
      index into "indexToSearch2" `object` Test("algolia2", 10, alien = false)
    }

    taskShouldBeCreatedAndWaitForIt(insert2, "indexToSearch2")
  }

  describe("search") {

    it("should return generic object") {
      val s = client.execute {
        search into indexName query Query(query = Some("a"))
      }

      whenReady(s) { result =>
        result.hits should have length 1
        (result.hits.head \ "name").values should be("algolia")
        (result.hits.head \ "age").values should be(10)
        (result.hits.head \ "alien").values shouldBe false
      }
    }

    it("should return case class") {
      val s = client.execute {
        search into indexName query Query(query = Some("a"))
      }

      whenReady(s) { result =>
        result.hits should have length 1
        result.as[Test].head should be(Test("algolia", 10, alien = false))
      }
    }

    it("should return a hit") {
      val s = client.execute {
        search into indexName query Query(query = Some("a"))
      }

      whenReady(s) { result =>
        result.hits should have length 1
        val hit = EnhanceTest(
          "algolia",
          10,
          alien = false,
          "563481290",
          Some(
            Map(
              "name" -> HighlightResult("<em>a</em>lgolia",
                                        "full",
                                        Seq("a"),
                                        fullyHighlighted = false))),
          None,
          None,
          None
        )
        result.asHit[EnhanceTest].head should be(hit)
      }
    }

    it("should be able to search on insideBoundingBox") {
      val box = Seq(InsideBoundingBox("1", "2", "3", "4"), InsideBoundingBox("5", "6", "7", "8"))
      val s = client.execute {
        search into indexName query Query(query = Some("a"), insideBoundingBox = Some(box))
      }

      whenReady(s) { result =>
        result.hits should be(empty)
      }
    }

    it("should be able to search on insidePolygon") {
      val polygon = Seq(
        InsidePolygon("1", "2", "3", "4", "5", "6"),
        InsidePolygon("7", "8", "9", "10", "11", "12")
      )
      val s = client.execute {
        search into indexName query Query(query = Some("a"), insidePolygon = Some(polygon))
      }

      whenReady(s) { result =>
        result.hits should be(empty)
      }
    }

    it("should validate mapping of SearchResult") {
      val s = client.execute {
        search into indexName query
          Query(query = Some("a"), offset = Some(25), length = Some(10))
      }

      whenReady(s) { result =>
        result.hits should be(empty)
      }

    }
  }

  describe("multiple queries") {

    it("should return generic object") {
      val s = client.execute {
        multipleQueries(
          search into indexName query Query(query = Some("a")),
          search into "indexToSearch2" query Query(query = Some("a"))
        )
      }

      whenReady(s) { r =>
        r.results should have length 2
        forAll(r.results) { result =>
          (result.hits.head \ "name").values.toString should startWith("algolia")
          (result.hits.head \ "age").values should be(10)
          (result.hits.head \ "alien").values shouldBe false
        }
      }
    }

    it("should return case class") {
      val s = client.execute {
        multipleQueries(
          search into indexName query Query(query = Some("a")),
          search into "indexToSearch2" query Query(query = Some("a"))
        )
      }

      whenReady(s) { r =>
        r.results should have length 2
        forAll(r.results) { result =>
          result.hits should have length 1
          result.as[Test].head shouldBe a[Test]
        }
      }
    }

    it("should return a hit") {
      val s = client.execute {
        multipleQueries(
          search into indexName query Query(query = Some("a")),
          search into "indexToSearch2" query Query(query = Some("a"))
        )
      }

      whenReady(s) { r =>
        r.results should have length 2
        forAll(r.results) { result =>
          result.hits should have length 1
          result.asHit[EnhanceTest].head shouldBe a[EnhanceTest]
        }
      }
    }
  }

  describe("search in facet") {

    it("should search in facets") {
      val change = client.execute {
        changeSettings of "indexToSearchFacet" `with` IndexSettings(
          attributesForFaceting = Some(Seq("searchable(series)"))
        )
      }

      taskShouldBeCreatedAndWaitForIt(change, "indexToSearchFacet")

      val add = client.execute {
        index into "indexToSearchFacet" objects Seq(
          Character("snoopy", "Peanuts"),
          Character("woodstock", "Peanuts"),
          Character("Calvin", "Calvin & Hobbes")
        )
      }
      taskShouldBeCreatedAndWaitForIt(add, "indexToSearchFacet")

      val facetSearch = client.execute {
        search into "indexToSearchFacet" facet "series" values "Peanuts"
      }

      whenReady(facetSearch) { r =>
        r.facetHits
      }
    }

  }
}

case class Test(name: String, age: Int, alien: Boolean)

case class EnhanceTest(name: String,
                       age: Int,
                       alien: Boolean,
                       objectID: String,
                       _highlightResult: Option[Map[String, HighlightResult]],
                       _snippetResult: Option[Map[String, SnippetResult]],
                       _rankingInfo: Option[RankingInfo],
                       _distinctSeqID: Option[Integer])
    extends Hit

case class Character(name: String, series: String)
