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

import java.time.temporal.ChronoUnit
import java.time.{LocalDateTime, ZoneOffset}

import algolia.AlgoliaDsl._
import algolia.AlgoliaTest
import algolia.inputs.{ABTest, ABTestVariant}
import algolia.`4XXAPIException`

import org.scalatest.RecoverMethods._

class ABTestIntegrationTest extends AlgoliaTest {

  val now = LocalDateTime.now
  val nowStr = now.atOffset(ZoneOffset.UTC).toEpochSecond.toString
  val indexName1 = s"indexAbTest1-$nowStr"
  val indexName2 = s"indexAbTest2-$nowStr"

  before {
    waitForMultipleIndexTasks(createIndices(indexName1, indexName2).taskID)
    deleteAllABTests()
  }

  after {
    waitForMultipleIndexTasks(clearIndices(indexName1, indexName2).taskID)
  }

  def dummyABTest = ABTest(
    name = s"abTestName-$nowStr",
    variants = Seq(
      ABTestVariant(indexName1, 60, Some("a description")),
      ABTestVariant(indexName2, 40)
    ),
    endAt = now.plus(10, ChronoUnit.DAYS)
  )

  describe("AB testing") {

    it("should send an API test") {
      val inputAbTest = dummyABTest

      taskShouldBeCreatedAndWaitForIt(client.execute(add abTest inputAbTest), indexName1)

      val task = client.execute(get all abTests)
      whenReady(task) { abTests =>
        abTests.abtests should have size 1
        abTests.abtests.map { abTest =>
          {
            abTest.name should be(inputAbTest.name)
            abTest.endAt should be(inputAbTest.endAt)

            abTest.variants.size should be(inputAbTest.variants.size)
            abTest.variants.forall { variant =>
              val isVariantFound =
                inputAbTest.variants
                  .map { expectedVariant =>
                    expectedVariant.index == variant.index &&
                    expectedVariant.description.getOrElse("") == variant.description &&
                    expectedVariant.trafficPercentage == variant.trafficPercentage
                  }
                  .reduce((a, b) => a || b)

              isVariantFound
            }
          }
        }
      }
    }

    it("should stop an API test") {
      val inputAbTest = dummyABTest

      val addTask = client.execute(add abTest inputAbTest)
      val abTestID = whenReady(addTask) { res =>
        res.abTestID
      }
      taskShouldBeCreatedAndWaitForIt(addTask, indexName1)

      val stopTask = client.execute(stop abTest abTestID)
      taskShouldBeCreatedAndWaitForIt(stopTask, indexName1)

      val getTask = client.execute(get abTest abTestID)
      whenReady(getTask) { abTest =>
        abTest.abTestID should be(abTestID)
        abTest.status should be("stopped")
      }
    }

    it("should delete an API test") {
      val inputAbTest = dummyABTest

      val addTask = client.execute(add abTest inputAbTest)
      val abTestID = whenReady(addTask) { res =>
        res.abTestID
      }
      taskShouldBeCreatedAndWaitForIt(addTask, indexName1)

      val deleteTask = client.execute(delete abTest abTestID)
      whenReady(deleteTask) { res =>
        res
      }

      recoverToSucceededIf[`4XXAPIException`] {
        client.execute(get abTest abTestID)
      }
    }
  }
}
