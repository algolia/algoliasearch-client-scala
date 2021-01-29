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

package algolia.definitions

import algolia.http.{HttpPayload, POST}
import algolia.objects.{
  Dictionary,
  DictionaryEntry,
  QueryDictionary,
  RequestOptions
}
import org.json4s.Formats
import org.json4s.native.Serialization.write

case class SaveDictionaryDefinition[A <: DictionaryEntry](
    dictionary: Dictionary[A],
    dictionaryEntries: Seq[A] = List(),
    requestOptions: Option[RequestOptions] = None
)(implicit val formats: Formats)
    extends Definition {

  override type T = SaveDictionaryDefinition[A]

  def entries(entries: Seq[A]): SaveDictionaryDefinition[A] =
    copy(dictionaryEntries = entries)

  override private[algolia] def build() = {
    val path = Seq("1", "dictionaries", dictionary.name, "batch")
    val body = Map(
      "clearExistingDictionaryEntries" -> false,
      "requests" -> dictionaryEntries.map(entry =>
        Map(
          "action" -> "addEntry",
          "body" -> entry
        )
      )
    )

    HttpPayload(
      POST,
      path,
      body = Some(write(body)),
      isSearch = false,
      requestOptions = requestOptions
    )
  }

  override def options(
      requestOptions: RequestOptions
  ): SaveDictionaryDefinition[A] = copy(requestOptions = Some(requestOptions))
}

case class SearchDictionaryDefinition[A <: DictionaryEntry](
    dictionary: Dictionary[A],
    query: QueryDictionary = QueryDictionary(),
    requestOptions: Option[RequestOptions] = None
)(implicit val formats: Formats)
    extends Definition {

  override type T = SearchDictionaryDefinition[A]

  def query(q: QueryDictionary): SearchDictionaryDefinition[A] = copy(query = q)

  override private[algolia] def build() = {
    val path = Seq("1", "dictionaries", dictionary.name, "search")
    val body = query.toQueryParam
    HttpPayload(
      POST,
      path,
      body = Some(write(body)),
      isSearch = true,
      requestOptions = requestOptions
    )
  }

  override def options(
      requestOptions: RequestOptions
  ): SearchDictionaryDefinition[A] = copy(requestOptions = Some(requestOptions))
}
