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
import algolia.AlgoliaTest
import algolia.objects.{Acl, ApiKey}
import algolia.responses.{AllKeys, CreateUpdateKey}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ApiKeyIntegrationTest extends AlgoliaTest {

  describe("global keys") {

    it("should list keys") {
      val result: Future[AllKeys] = client.execute {
        get allKeys()
      }

      whenReady(result) { keys =>
        keys.keys shouldNot be(empty)
      }
    }

    it("should do stuff with keys") {
      val addKey = client.execute {
        add key ApiKey(acl = Some(Seq(Acl.addObject)))
      }

      var keyName = ""
      whenReady(addKey) { r =>
        r.key shouldNot be(empty)
        keyName = r.key
      }

      Thread.sleep(5000) //ok let's wait propagation

      val getKey = client.execute {
        get key keyName
      }

      whenReady(getKey) { r =>
        r.acl should equal(Some(Seq(Acl.addObject)))
      }

      val updateKey = client.execute {
        update key keyName `with` ApiKey(validity = Some(10))
      }

      whenReady(updateKey) { (r: CreateUpdateKey) =>
        r.key should be(keyName)
      }

      val deleteKey = client.execute {
        delete key keyName
      }

      whenReady(deleteKey) { r =>
        r.deletedAt shouldNot be(empty)
      }
    }
  }

  describe("index keys") {

    before {
      val pushObject = client.execute {
        index into "indexToTestKeys" `object` ObjectToGet("1", "toto")
      }

      taskShouldBeCreatedAndWaitForIt(pushObject, "indexToTestKeys")
    }

    after {
      clearIndices("indexToTestKeys")
    }

    it("should list keys") {
      val result: Future[AllKeys] = client.execute {
        get allKeysFrom "indexToTestKeys"
      }

      whenReady(result) { keys =>
        keys.keys should be(empty)
      }
    }

    it("should do stuff with keys") {
      val addKey = client.execute {
        add key ApiKey(acl = Some(Seq(Acl.addObject))) to "indexToTestKeys"
      }

      var keyName = ""
      whenReady(addKey) { (r: CreateUpdateKey) =>
        r.key shouldNot be(empty)
        keyName = r.key
      }

      Thread.sleep(5000) //ok let's wait propagation

      val getKey = client.execute {
        get key keyName from "indexToTestKeys"
      }

      whenReady(getKey) { r =>
        r.acl should equal(Some(Seq(Acl.addObject)))
      }

      val updateKey = client.execute {
        update key keyName `with` ApiKey(validity = Some(10)) from "indexToTestKeys"
      }

      whenReady(updateKey) { r =>
        r.key should be(keyName)
      }

      val deleteKey = client.execute {
        delete key keyName from "indexToTestKeys"
      }

      whenReady(deleteKey) { r =>
        r.deletedAt shouldNot be(empty)
      }
    }
  }
}
