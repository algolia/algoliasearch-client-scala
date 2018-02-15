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
import algolia.definitions.{SearchRulesDefinition, SearchSynonymsDefinition}
import algolia.objects._
import algolia.responses._

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

  def exportSynonyms(index: String, hitsPerPage: Int = 1000)(
      implicit duration: Duration,
      executor: ExecutionContext): Iterator[Seq[AbstractSynonym]] = {
    exportIterator[SearchSynonymsDefinition, AbstractSynonym, SearchSynonymResult](hitsPerPage) {
      page =>
        AlgoliaDsl.search synonyms in index index query QuerySynonyms("",
                                                                      page = Some(page),
                                                                      hitsPerPage =
                                                                        Some(hitsPerPage))
    }
  }

  def exportRules(index: String, hitsPerPage: Int = 999 /* bug in the rules, the timit is 999 */ )(
      implicit duration: Duration,
      executor: ExecutionContext): Iterator[Seq[Rule]] = {
    exportIterator[SearchRulesDefinition, Rule, SearchRuleResult](hitsPerPage) { page =>
      AlgoliaDsl.search rules in index index query QueryRules("",
                                                              page = Some(page),
                                                              hitsPerPage = Some(hitsPerPage))
    }
  }

  private def exportIterator[Q, A, B <: SearchHits[A]](hitsPerPage: Int)(query: Int => Q)(
      implicit duration: Duration,
      executable: Executable[Q, B],
      executor: ExecutionContext): Iterator[Seq[A]] = {
    var page: PageState = PageState.Init

    new Iterator[Seq[A]] {
      override def hasNext: Boolean = page match {
        case PageState.End => false
        case _ => true
      }

      override def next(): Seq[A] = {
        val future: Future[B] = client.execute {
          query(page.page)
        }

        val result = Await.result(future, duration).hits
        page = if (result.lengthCompare(hitsPerPage) < 0) {
          PageState.End
        } else {
          page.nextPage()
        }

        result
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

private[algolia] sealed trait PageState {
  val page: Int

  def nextPage(): PageState
}

private[algolia] object PageState {

  object Init extends PageState {
    override val page: Int = 0

    override def nextPage(): PageState = PageState.Full(1)
  }

  object End extends PageState {
    override val page: Int = 0

    override def nextPage(): PageState = PageState.End
  }

  case class Full(page: Int) extends PageState {
    override def nextPage(): PageState = Full(page + 1)
  }

}
