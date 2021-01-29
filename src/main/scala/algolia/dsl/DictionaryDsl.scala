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

import algolia.definitions.{
  SaveDictionaryDefinition,
  SearchDictionaryDefinition
}
import algolia.objects.{
  CompoundEntry,
  DictionaryEntry,
  PluralEntry,
  StopwordEntry
}
import algolia.responses.{DictionaryTask, SearchDictionaryResult}
import algolia.{AlgoliaClient, AlgoliaClientException, Executable}
import org.json4s.Formats

import scala.concurrent.{ExecutionContext, Future}

trait DictionaryDsl {

  implicit val formats: Formats

  // Save Dictionary Definition

  implicit object SaveStopwordDictionaryDefinitionExecutable
      extends SaveDictionaryDefinitionExecutable[StopwordEntry]

  implicit object SavePluralDictionaryDefinitionExecutable
      extends SaveDictionaryDefinitionExecutable[PluralEntry]

  implicit object SaveCompoundDictionaryDefinitionExecutable
      extends SaveDictionaryDefinitionExecutable[CompoundEntry]

  sealed abstract class SaveDictionaryDefinitionExecutable[T <: DictionaryEntry]
      extends Executable[SaveDictionaryDefinition[T], DictionaryTask] {

    override def apply(
        client: AlgoliaClient,
        query: SaveDictionaryDefinition[T]
    )(
        implicit executor: ExecutionContext
    ): Future[DictionaryTask] = {
      if (query.dictionaryEntries.isEmpty) {
        return Future.failed(
          new AlgoliaClientException(s"Dictionary entries cannot be empty")
        )
      }
      client.request[DictionaryTask](query.build())
    }
  }

  //Search Dictionary Definition

  implicit object SearchStopwordDictionaryDefinitionExecutable
      extends SearchDictionaryDefinitionExecutable[StopwordEntry]

  implicit object SearchPluralDictionaryDefinitionExecutable
      extends SearchDictionaryDefinitionExecutable[PluralEntry]

  implicit object SearchCompoundDictionaryDefinitionExecutable
      extends SearchDictionaryDefinitionExecutable[CompoundEntry]

  sealed abstract class SearchDictionaryDefinitionExecutable[
      T <: DictionaryEntry
  ] extends Executable[SearchDictionaryDefinition[T], SearchDictionaryResult] {

    override def apply(
        client: AlgoliaClient,
        query: SearchDictionaryDefinition[T]
    )(
        implicit executor: ExecutionContext
    ): Future[SearchDictionaryResult] = {
      client.requestSearch[SearchDictionaryResult](query.build())
    }
  }
}
