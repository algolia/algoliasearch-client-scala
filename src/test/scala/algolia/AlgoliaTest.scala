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

package algolia

import java.time.{ZoneOffset, ZonedDateTime}

import algolia.AlgoliaDsl._
import algolia.objects.{Condition, Consequence, Rule}
import algolia.responses.{AlgoliaTask, TasksMultipleIndex}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, EitherValues, Inside, Inspectors}
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.concurrent.{Eventually, ScalaFutures}
import org.scalatest.time.{Millis, Seconds, Span}

import scala.concurrent.{ExecutionContext, Future}

object AlgoliaTest {

  lazy val userName: String = System.getProperty("user.name")
  lazy val osName: String = System.getProperty("os.name").trim
  lazy val scalaVersion: String = util.Properties.versionNumberString
  lazy val applicationId: String = System.getenv("APPLICATION_ID")
  lazy val apiKey: String = System.getenv("API_KEY")
  lazy val client: AlgoliaClient = new AlgoliaClient(applicationId, apiKey) {
    override val httpClient: AlgoliaHttpClient =
      AlgoliaHttpClient(AlgoliaClientConfiguration(100000, 100000, 100000, 100000, 100000))
  }
}

class AlgoliaTest
    extends AnyFunSpec
    with Matchers
    with BeforeAndAfter
    with BeforeAndAfterAll
    with Inspectors
    with ScalaFutures
    with Inside
    with MockFactory
    with EitherValues
    with Eventually {

  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  implicit val patience: PatienceConfig =
    PatienceConfig(timeout = Span(30000, Seconds), interval = Span(500, Millis))

  override protected def afterAll(): Unit = {
    //   AlgoliaTest.client.close()
  }

  def taskShouldBeCreated(task: Future[AlgoliaTask])(implicit ec: ExecutionContext): AlgoliaTask = {
    whenReady(task) { result =>
      result.idToWaitFor should not be 0
      result
    }
  }

  def taskShouldBeCreatedAndWaitForIt(task: Future[AlgoliaTask], index: String)(
      implicit ec: ExecutionContext): Unit = {
    val t: AlgoliaTask = taskShouldBeCreated(task)

    val waiting = AlgoliaTest.client.execute {
      waitFor task t from index maxDelay (60 * 10 * 1000) //600 seconds
    }

    whenReady(waiting) { result =>
      result.status should equal("published")
    }
  }

  def waitForMultipleIndexTasks(tasks: Map[String, Long]): Unit = {
    tasks.foreach {
      case (index, taskID) =>
        taskID should not be 0
        val f = AlgoliaTest.client.execute { waitFor task taskID from index }
        whenReady(f) { status =>
          status
        }
    }
  }

  case class DummyObject(objectID: String)

  def getTestIndexName(indexName: String): String = {
    val utc = ZonedDateTime.now(ZoneOffset.UTC)
    s"scala_${AlgoliaTest.scalaVersion}_${utc}_${AlgoliaTest.osName}_${AlgoliaTest.userName}_$indexName"
      .replaceAll("\\s", "")
  }

  def createIndices(indices: String*): TasksMultipleIndex = {
    val add = AlgoliaTest.client.execute {
      batch(indices.map { i =>
        index into i `object` DummyObject("one")
      })
    }

    whenReady(add) { res =>
      res
    }
  }

  def deleteAllABTests(): Unit = {
    var areABTestsRemaining: Boolean = true

    while (areABTestsRemaining) {
      areABTestsRemaining = deleteOnePageOfABTests()
    }
  }

  def deleteOnePageOfABTests(): Boolean = {
    val f = AlgoliaTest.client.execute {
      get all abTests
    }

    whenReady(f) { res =>
      val areABTestsRemaining = res.total - res.count > 0

      val ids = res.abtests.map(_.abTestID)

      val futures = ids.map { id =>
        AlgoliaTest.client.execute {
          delete abTest id
        }
      }

      futures.foreach { fDelete =>
        whenReady(fDelete) { res =>
          res
        }
      }

      areABTestsRemaining
    }
  }

  def clearIndices(indices: String*): TasksMultipleIndex = {
    val del = AlgoliaTest.client.execute {
      batch(indices.map { i =>
        delete index i
      })
    }

    whenReady(del) { res =>
      res
    }
  }

  def generateRule(ruleId: String): Rule = {
    Rule(
      objectID = ruleId,
      condition = Condition(
        pattern = "a",
        anchoring = "is"
      ),
      consequence = Consequence(
        params = Some(
          Map("query" -> Map("remove" -> Seq("1")))
        ),
        userData = Some(Map("a" -> "b"))
      ),
      description = Some(ruleId)
    )
  }
}
