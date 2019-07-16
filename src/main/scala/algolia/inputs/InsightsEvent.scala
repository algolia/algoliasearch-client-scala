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

package algolia.inputs

case class InsightsEvent(
    eventType: String,
    eventName: String,
    index: String,
    userToken: String,
    timestamp: Option[Long] = None, // Epoch timestamp in millisecond
    objectIDs: Option[Iterable[String]] = None,
    filters: Option[Iterable[String]] = None,
    positions: Option[Iterable[Int]] = None,
    queryID: Option[String] = None
)

object ClickedFilters {
  def apply(userToken: String, eventName: String, indexName: String, filters: Iterable[String]) =
    InsightsEvent("click", eventName, indexName, userToken, filters = Some(filters))
}

object ClickedObjectIDs {
  def apply(userToken: String, eventName: String, indexName: String, objectIDs: Iterable[String]) =
    InsightsEvent("click", eventName, indexName, userToken, objectIDs = Some(objectIDs))
}

object ClickedObjectIDsAfterSearch {
  def apply(userToken: String,
            eventName: String,
            indexName: String,
            objectIDs: Iterable[String],
            positions: Iterable[Int],
            queryID: String) =
    InsightsEvent("click",
                  eventName,
                  indexName,
                  userToken,
                  objectIDs = Some(objectIDs),
                  positions = Some(positions),
                  queryID = Some(queryID))
}

object ConvertedObjectIDs {
  def apply(userToken: String, eventName: String, indexName: String, objectIDs: Iterable[String]) =
    InsightsEvent("conversion", eventName, indexName, userToken, objectIDs = Some(objectIDs))
}

object ConvertedObjectIDsAfterSearch {
  def apply(userToken: String,
            eventName: String,
            indexName: String,
            objectIDs: Iterable[String],
            queryID: String) =
    InsightsEvent("conversion",
                  eventName,
                  indexName,
                  userToken,
                  objectIDs = Some(objectIDs),
                  queryID = Some(queryID))
}

object ConvertedFilters {
  def apply(userToken: String, eventName: String, indexName: String, filters: Iterable[String]) =
    InsightsEvent("conversion", eventName, indexName, userToken, filters = Some(filters))
}

object ViewedFilters {
  def apply(userToken: String, eventName: String, indexName: String, filters: Iterable[String]) =
    InsightsEvent("view", eventName, indexName, userToken, filters = Some(filters))
}

object ViewedObjectIDs {
  def apply(userToken: String, eventName: String, indexName: String, objectIDs: Iterable[String]) =
    InsightsEvent("view", eventName, indexName, userToken, objectIDs = Some(objectIDs))
}
