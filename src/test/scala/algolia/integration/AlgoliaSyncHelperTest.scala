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
import algolia.AlgoliaSyncHelper._
import algolia.objects._
import algolia.responses._
import algolia.{AlgoliaSyncHelper, AlgoliaTest}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class AlgoliaSyncHelperTest extends AlgoliaTest {

  import scala.collection.compat._

  val helper = AlgoliaSyncHelper(AlgoliaTest.client)
  val list: Seq[Value] = 1.to(100).map(i => Value(i, i.toString))
  val syns: Seq[Synonym.Synonym] =
    1.to(10).map(i => Synonym.Synonym(s"obj_$i", Seq(s"a_$i", s"b_$i")))
  val rules: Seq[Rule] = 1.to(10).map(i => generateRule(s"obj_$i"))

  before {
    val insert = AlgoliaTest.client.execute {
      index into "testBrowseSync" objects list
    }

    taskShouldBeCreatedAndWaitForIt(insert, "testBrowseSync")

    val insertSynonyms = AlgoliaTest.client.execute {
      save synonyms syns inIndex "testBrowseSync"
    }

    taskShouldBeCreatedAndWaitForIt(insertSynonyms, "testBrowseSync")

    val insertRules = AlgoliaTest.client.execute {
      save rules rules inIndex "testBrowseSync"
    }

    taskShouldBeCreatedAndWaitForIt(insertRules, "testBrowseSync")
  }

  after {
    clearIndices("testBrowseSync")
  }

  describe("browse") {

    val query = Query(hitsPerPage = Some(10))

    it("should browse in sync") {

      val res: Iterator[Seq[Value]] =
        helper.browse[Value]("testBrowseSync", query)

      res should have size 10
      forAll(res.iterator.to(Iterable)) { r =>
        r should have size 10
        forAll(r) { v =>
          v.int should (be >= 1 and be <= 100)
        }
      }
    }

  }

  describe("delete by query") {

    val query = Query(filters = Some("int > 10"))

    it("should delete with a query") {
      val delete = helper.deleteByQuery[Value]("testBrowseSync", query)
      val tasksToWait = whenReady(delete) { res =>
        Future.traverse(res)(i =>
          AlgoliaTest.client.execute(waitFor task i from "testBrowseSync")
        )
      }

      whenReady(tasksToWait) { res =>
        res
      }

      val res: Iterator[Seq[Value]] =
        helper.browse[Value]("testBrowseSync", query)

      res should have size 1
      forAll(res.iterator.to(Iterable)) { r =>
        r should have size 10
        forAll(r) { v =>
          v.int should (be >= 1 and be <= 10)
        }
      }
    }

  }

  case class Employee(company: String, name: String, objectID: String)
      extends ObjectID

  describe("search response helpers") {

    val objs = Seq(
      Employee("Algolia", "Julien Lemoine", "julien-lemoine"),
      Employee("Algolia", "Nicolas Dessaigne", "nicolas-dessaigne"),
      Employee("Amazon", "Jeff Bezos", "1000"),
      Employee("Apple", "Steve Jobs", "1020"),
      Employee("Apple", "Steve Wozniak", "1030"),
      Employee("Arista Networks", "Jayshree Ullal", "1040"),
      Employee("Google", "Larry Page", "1050"),
      Employee("Google", "Rob Pike", "1060"),
      Employee("Google", "Sergueï Brin", "1070"),
      Employee("Microsoft", "Bill Gates", "1080"),
      Employee("SpaceX", "Elon Musk", "1090"),
      Employee("Tesla", "Elon Musk", "1100"),
      Employee("Yahoo", "Marissa Mayer", "1110")
    )

    it("should find the search results' position correctly") {
      val indexName = "testGetObjectPosition"

      val clearTask =
        Await.result(helper.client.execute(clear index indexName), Duration.Inf)
      Await.result(
        helper.client.execute(waitFor task clearTask from indexName),
        Duration.Inf
      )

      val indexingTask = Await.result(
        helper.client.execute(index into indexName objects objs),
        Duration.Inf
      )
      Await.result(
        helper.client.execute(waitFor task indexingTask from indexName),
        Duration.Inf
      )

      val res = Await.result(
        helper.client
          .execute(search into indexName query Query(query = Some("algolia"))),
        Duration.Inf
      )

      res.getObjectPosition[Employee]("nicolas-dessaigne") should be(Some(0))
      res.getObjectPosition[Employee]("julien-lemoine") should be(Some(1))
      res.getObjectPosition[Employee]("") should be(None)
    }

    it("should find the first object correctly") {
      val indexName = "testFindObject"

      val clearTask =
        Await.result(helper.client.execute(clear index indexName), Duration.Inf)
      Await.result(
        helper.client.execute(waitFor task clearTask from indexName),
        Duration.Inf
      )

      val indexingTask = Await.result(
        helper.client.execute(index into indexName objects objs),
        Duration.Inf
      )
      Await.result(
        helper.client.execute(waitFor task indexingTask from indexName),
        Duration.Inf
      )

      helper.findObject(indexName, Query(), (_: Employee) => false) should be(
        None
      )

      var obj = helper.findObject(indexName, Query(), (_: Employee) => true)
      obj should not be (None)
      obj.get.page should be(0)
      obj.get.position should be(0)

      val predicate = (e: Employee) => e.company == "Apple"

      helper.findObject(indexName, Query(query = Some("algolia")), predicate) should be(
        None
      )

      helper.findObject(
        indexName,
        Query(
          query = Some(""),
          hitsPerPage = Some(5)
        ),
        predicate,
        paginate = false
      ) should be(None)

      obj = helper.findObject(
        indexName,
        Query(
          query = Some(""),
          hitsPerPage = Some(5)
        ),
        predicate
      )
      obj should not be (None)
      obj.get.page should be(2)
      obj.get.position should be(0)
    }
  }

  describe("export synonyms") {

    it("should export synonyms") {
      val list: List[AbstractSynonym] =
        helper.exportSynonyms("testBrowseSync").toList.flatten

      list should have size 10
      forAll(list) { r =>
        r.objectID should startWith("obj_")
      }
    }

  }

  describe("export rules") {

    it("should export rules") {
      val list: List[Rule] = helper.exportRules("testBrowseSync").toList.flatten

      list should have size 10
      forAll(list) { r =>
        r.objectID should startWith("obj_")
      }
    }

  }

}

case class Value(int: Int, objectID: String) extends ObjectID
