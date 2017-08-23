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

import algolia.AlgoliaDsl._
import algolia.objects.Query
import algolia.responses.{BrowseResult, ObjectID, TasksMultipleIndex}

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}

object AlgoliaSyncHelper {

  implicit val duration: Duration = 5.seconds

}

case class AlgoliaSyncHelper(client: AlgoliaClient) {

  /**
    * @deprecated use `delete from "index" by Query()`
    */
  @Deprecated
  def deleteByQuery[T <: ObjectID: Manifest](index: String, query: Query)(
      implicit duration: Duration,
      executor: ExecutionContext): Future[Iterator[TasksMultipleIndex]] = {
    val res = browse[T](index, query).map { seq =>
      client.execute {
        delete from index objectIds seq.map(_.objectID)
      }
    }

    Future.sequence(res)
  }

  def browse[T <: ObjectID: Manifest](index: String, query: Query)(
      implicit duration: Duration,
      executor: ExecutionContext): Iterator[Seq[T]] = {
    var cursor: CursorState = CursorState.Init

    new Iterator[Seq[T]] {
      override def hasNext: Boolean = cursor match {
        case CursorState.Empty => false
        case _ => true
      }

      override def next(): Seq[T] = {
        val future: Future[BrowseResult] = client.execute {
          AlgoliaDsl.browse index index query query from cursor.value
        }

        val result = Await.result(future, duration)
        cursor = result.cursor.fold[CursorState](CursorState.Empty)(CursorState.Full)

        result.asWithObjectID[T]
      }
    }
  }

}

private[algolia] sealed trait CursorState {
  def value: String
}

private[algolia] object CursorState {

  case class Full(value: String) extends CursorState

  object Empty extends CursorState {
    override def value: String = ""
  }

  object Init extends CursorState {
    override def value: String = ""
  }

}
