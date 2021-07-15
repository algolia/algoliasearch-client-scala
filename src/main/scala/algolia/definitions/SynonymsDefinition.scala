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

import algolia.AlgoliaDsl.{
  ClearExistingSynonyms,
  ForwardToReplicas,
  ReplaceExistingSynonyms,
  clearExistingSynonyms
}
import algolia.http._
import algolia.objects.{AbstractSynonym, QuerySynonyms, RequestOptions}
import org.json4s.Formats
import org.json4s.native.Serialization._

case class GetSynonymDefinition(
    synId: String,
    index: Option[String] = None,
    requestOptions: Option[RequestOptions] = None
)(implicit val formats: Formats)
    extends Definition {

  type T = GetSynonymDefinition

  def from(index: String): GetSynonymDefinition = copy(index = Some(index))

  override def options(requestOptions: RequestOptions): GetSynonymDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    HttpPayload(
      GET,
      Seq("1", "indexes") ++ index ++ Seq("synonyms", synId),
      isSearch = true,
      requestOptions = requestOptions
    )
  }

}

case class DeleteSynonymDefinition(
    synId: String,
    index: Option[String] = None,
    option: Option[ForwardToReplicas] = None,
    requestOptions: Option[RequestOptions] = None
)(implicit val formats: Formats)
    extends Definition {

  type T = DeleteSynonymDefinition

  def from(index: String): DeleteSynonymDefinition = copy(index = Some(index))

  def and(option: ForwardToReplicas): DeleteSynonymDefinition =
    copy(option = Some(option))

  override def options(
      requestOptions: RequestOptions
  ): DeleteSynonymDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    val queryParameters = if (option.isDefined) {
      Some(Map("forwardToReplicas" -> "true"))
    } else {
      None
    }

    HttpPayload(
      DELETE,
      Seq("1", "indexes") ++ index ++ Seq("synonyms", synId),
      queryParameters = queryParameters,
      isSearch = false,
      requestOptions = requestOptions
    )
  }

}

case class ClearSynonymsDefinition(
    index: Option[String] = None,
    option: Option[ForwardToReplicas] = None,
    requestOptions: Option[RequestOptions] = None
)(implicit val formats: Formats)
    extends Definition {

  type T = ClearSynonymsDefinition

  def index(index: String): ClearSynonymsDefinition = copy(index = Some(index))

  def and(option: ForwardToReplicas): ClearSynonymsDefinition =
    copy(option = Some(option))

  override def options(
      requestOptions: RequestOptions
  ): ClearSynonymsDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    val path = Seq("1", "indexes") ++ index ++ Seq("synonyms", "clear")

    val queryParameters = if (option.isDefined) {
      Some(Map("forwardToReplicas" -> "true"))
    } else {
      None
    }

    HttpPayload(
      POST,
      path,
      queryParameters = queryParameters,
      isSearch = false,
      requestOptions = requestOptions
    )
  }
}

case class SaveSynonymDefinition(
    synonym: AbstractSynonym,
    index: Option[String] = None,
    option: Option[ForwardToReplicas] = None,
    requestOptions: Option[RequestOptions] = None
)(implicit val formats: Formats)
    extends Definition {

  type T = SaveSynonymDefinition

  def inIndex(index: String): SaveSynonymDefinition = copy(index = Some(index))

  def and(option: ForwardToReplicas): SaveSynonymDefinition =
    copy(option = Some(option))

  override def options(requestOptions: RequestOptions): SaveSynonymDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    val path = Seq("1", "indexes") ++ index ++ Seq("synonyms", synonym.objectID)

    val queryParameters = if (option.isDefined) {
      Some(Map("forwardToReplicas" -> "true"))
    } else {
      None
    }

    HttpPayload(
      PUT,
      path,
      queryParameters = queryParameters,
      body = Some(write(synonym)),
      isSearch = false,
      requestOptions = requestOptions
    )
  }
}

case class BatchSynonymsDefinition(
    synonyms: Iterable[AbstractSynonym],
    index: Option[String] = None,
    forward: Option[ForwardToReplicas] = None,
    replace: Option[ClearExistingSynonyms] = None,
    requestOptions: Option[RequestOptions] = None
)(implicit val formats: Formats)
    extends Definition {

  type T = BatchSynonymsDefinition

  def inIndex(index: String): BatchSynonymsDefinition =
    copy(index = Some(index))

  def and(forward: ForwardToReplicas): BatchSynonymsDefinition =
    copy(forward = Some(forward))

  @deprecated("Use ClearExistingSynonyms instead")
  def and(replace: ReplaceExistingSynonyms): BatchSynonymsDefinition =
    and(clearExistingSynonyms)

  def and(replace: ClearExistingSynonyms): BatchSynonymsDefinition =
    copy(replace = Some(replace))

  override def options(
      requestOptions: RequestOptions
  ): BatchSynonymsDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    val path = Seq("1", "indexes") ++ index ++ Seq("synonyms", "batch")

    var queryParameters = Map.empty[String, String]
    forward.foreach(_ => queryParameters += ("forwardToReplicas" -> "true"))
    replace.foreach(_ =>
      queryParameters += ("replaceExistingSynonyms" -> "true")
    )

    HttpPayload(
      POST,
      path,
      queryParameters = Some(queryParameters),
      body = Some(write(synonyms)),
      isSearch = false,
      requestOptions = requestOptions
    )
  }
}

case class SearchSynonymsDefinition(
    indx: Option[String] = None,
    query: Option[QuerySynonyms] = None,
    requestOptions: Option[RequestOptions] = None
)(implicit val formats: Formats)
    extends Definition {

  type T = SearchSynonymsDefinition

  def index(indx: String): SearchSynonymsDefinition = copy(indx = Some(indx))

  def query(query: QuerySynonyms): SearchSynonymsDefinition =
    copy(query = Some(query))

  override def options(
      requestOptions: RequestOptions
  ): SearchSynonymsDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    HttpPayload(
      POST,
      Seq("1", "indexes") ++ indx ++ Seq("synonyms", "search"),
      body = Some(write(query)),
      isSearch = true,
      requestOptions = requestOptions
    )
  }
}
