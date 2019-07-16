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
import algolia.objects.{IndexSettings, SearchableAttributes}
import algolia.responses._

import scala.concurrent.Future

class IndexSettingsIntegrationTest extends AlgoliaTest {

  val indexToChangeSettings: String = getTestIndexName("indexToChangeSettings")

  after {
    clearIndices(indexToChangeSettings)
  }

  it("should get settings") {
    val create = AlgoliaTest.client.execute {
      batch(
        index into indexToChangeSettings `object` ObjectToGet("1", "toto")
      )
    }

    taskShouldBeCreatedAndWaitForIt(create, indexToChangeSettings)

    val request: Future[IndexSettings] = AlgoliaTest.client.execute {
      settings of indexToChangeSettings
    }

    whenReady(request) { result =>
      result shouldBe a[IndexSettings] //just checking it deserialize
    }
  }

  it("should set settings") {
    val change: Future[Task] = AlgoliaTest.client.execute {
      setSettings of indexToChangeSettings `with` IndexSettings(
        searchableAttributes = Some(Seq(SearchableAttributes.attribute("att")))
      )
    }

    taskShouldBeCreatedAndWaitForIt(change, indexToChangeSettings)

    val request: Future[IndexSettings] = AlgoliaTest.client.execute {
      settings of indexToChangeSettings
    }

    whenReady(request) { result =>
      result.searchableAttributes should be(Some(Seq(SearchableAttributes.attribute("att"))))
    }
  }

}
