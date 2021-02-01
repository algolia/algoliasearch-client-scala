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

package algolia.dsl

import algolia.AlgoliaDsl._
import algolia.AlgoliaTest
import algolia.http.{HttpPayload, POST}
import algolia.objects.Dictionary.Stopwords
import algolia.objects.{QueryDictionary, StopwordEntry}
import org.json4s.native.Serialization.write

class DictionaryTest extends AlgoliaTest {

  describe("dictionary") {

    val stopwordEntry = StopwordEntry("MyObjectID", "en", "word", "enabled")
    val stopwordEntries = Seq(stopwordEntry)

    describe("save entries to stop word dictionary") {

      it("should save entries") {
        save dictionary Stopwords entries stopwordEntries
      }

      it("should call API") {
        (save dictionary Stopwords entries stopwordEntries)
          .build() should be(
          HttpPayload(
            POST,
            Seq("1", "dictionaries", "stopwords", "batch"),
            body = Some(
              write(
                Map(
                  "clearExistingDictionaryEntries" -> false,
                  "requests" -> stopwordEntries.map(entry =>
                    Map(
                      "action" -> "addEntry",
                      "body" -> entry
                    )
                  )
                )
              )
            ),
            isSearch = false,
            requestOptions = None
          )
        )
      }
    }

    describe("replace entries in stop word dictionary") {

      it("should replace entries") {
        replace dictionary Stopwords entries stopwordEntries
      }

      it("should call API") {
        (replace dictionary Stopwords entries stopwordEntries)
          .build() should be(
          HttpPayload(
            POST,
            Seq("1", "dictionaries", "stopwords", "batch"),
            body = Some(
              write(
                Map(
                  "clearExistingDictionaryEntries" -> true,
                  "requests" -> stopwordEntries.map(entry =>
                    Map(
                      "action" -> "addEntry",
                      "body" -> entry
                    )
                  )
                )
              )
            ),
            isSearch = false,
            requestOptions = None
          )
        )
      }
    }

    describe("delete entries in stop word dictionary") {

      it("should delete entries") {
        delete dictionary Stopwords entries Seq(stopwordEntry.objectID)
      }

      it("should call API") {
        (delete dictionary Stopwords entries Seq(stopwordEntry.objectID))
          .build() should be(
          HttpPayload(
            POST,
            Seq("1", "dictionaries", "stopwords", "batch"),
            body = Some(
              write(
                Map(
                  "clearExistingDictionaryEntries" -> false,
                  "requests" -> Seq(
                    Map(
                      "action" -> "deleteEntry",
                      "body" -> Map("objectID" -> stopwordEntry.objectID)
                    )
                  )
                )
              )
            ),
            isSearch = false,
            requestOptions = None
          )
        )
      }
    }

    describe("clear all entries in stop word dictionary") {

      it("should clear entries") {
        clear dictionary Stopwords
      }

      it("should call API") {
        (clear dictionary Stopwords).build() should be(
          HttpPayload(
            POST,
            Seq("1", "dictionaries", "stopwords", "batch"),
            body = Some(
              write(
                Map(
                  "clearExistingDictionaryEntries" -> true,
                  "requests" -> Seq.empty
                )
              )
            ),
            isSearch = false,
            requestOptions = None
          )
        )
      }
    }

    describe("search stop word dictionary") {

      it("should search for entity") {
        search into Stopwords query QueryDictionary(query = Some("MyObjectID"))
      }

      it("should call API") {
        (search into Stopwords query QueryDictionary(query = Some("MyObjectID")
        )).build() should be(
          HttpPayload(
            POST,
            Seq("1", "dictionaries", "stopwords", "search"),
            body = Some(
              write(
                Map(
                  "query" -> "MyObjectID"
                )
              )
            ),
            isSearch = true,
            requestOptions = None
          )
        )
      }
    }
  }
}
