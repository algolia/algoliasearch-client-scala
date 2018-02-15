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

import algolia.AlgoliaDsl.{ClearExistingRules, ForwardToReplicas}
import algolia.http._
import algolia.objects.{QueryRules, RequestOptions, Rule}
import algolia.responses.{SearchRuleResult, Task}
import algolia.{AlgoliaClient, Executable}
import org.json4s.Formats
import org.json4s.native.Serialization._

import scala.concurrent.{ExecutionContext, Future}

case class GetRuleDefinition(
    objectId: String,
    index: Option[String] = None,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  override type T = GetRuleDefinition

  override def options(requestOptions: RequestOptions): GetRuleDefinition =
    copy(requestOptions = Some(requestOptions))

  def from(index: String): GetRuleDefinition = copy(index = Some(index))

  override private[algolia] def build(): HttpPayload = {
    HttpPayload(
      GET,
      Seq("1", "indexes") ++ index ++ Seq("rules", objectId),
      isSearch = true,
      requestOptions = requestOptions
    )
  }

}

case class DeleteRuleDefinition(
    objectId: String,
    index: Option[String] = None,
    option: Option[ForwardToReplicas] = None,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  override type T = DeleteRuleDefinition

  override def options(requestOptions: RequestOptions): DeleteRuleDefinition =
    copy(requestOptions = Some(requestOptions))

  def from(index: String): DeleteRuleDefinition = copy(index = Some(index))

  def and(option: ForwardToReplicas): DeleteRuleDefinition =
    copy(option = Some(option))

  override private[algolia] def build(): HttpPayload = {
    val queryParameters = if (option.isDefined) {
      Some(Map("forwardToReplicas" -> "true"))
    } else {
      None
    }

    HttpPayload(
      DELETE,
      Seq("1", "indexes") ++ index ++ Seq("rules", objectId),
      queryParameters = queryParameters,
      isSearch = false,
      requestOptions = requestOptions
    )
  }

}

case class ClearRulesDefinition(
    index: Option[String] = None,
    option: Option[ForwardToReplicas] = None,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  override type T = ClearRulesDefinition

  override def options(requestOptions: RequestOptions): ClearRulesDefinition =
    copy(requestOptions = Some(requestOptions))

  def index(index: String): ClearRulesDefinition = copy(index = Some(index))

  def and(option: ForwardToReplicas): ClearRulesDefinition =
    copy(option = Some(option))

  override private[algolia] def build(): HttpPayload = {
    val path = Seq("1", "indexes") ++ index ++ Seq("rules", "clear")

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

case class SaveRuleDefinition(
    rule: Rule,
    index: Option[String] = None,
    forwardToReplicas: Option[ForwardToReplicas] = None,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  override type T = SaveRuleDefinition

  override def options(requestOptions: RequestOptions): SaveRuleDefinition =
    copy(requestOptions = Some(requestOptions))

  def inIndex(index: String): SaveRuleDefinition = copy(index = Some(index))

  def and(forwardToReplicas: ForwardToReplicas): SaveRuleDefinition =
    copy(forwardToReplicas = Some(forwardToReplicas))

  override private[algolia] def build(): HttpPayload = {
    val path = Seq("1", "indexes") ++ index ++ Seq("rules", rule.objectID)

    val queryParameters = if (forwardToReplicas.isDefined) {
      Some(Map("forwardToReplicas" -> "true"))
    } else {
      None
    }

    HttpPayload(
      PUT,
      path,
      queryParameters = queryParameters,
      body = Some(write(rule)),
      isSearch = false,
      requestOptions = requestOptions
    )
  }
}

case class BatchRulesDefinition(
    rules: Iterable[Rule],
    index: Option[String] = None,
    forwardToReplicas: Option[ForwardToReplicas] = None,
    clearExistingRules: Option[ClearExistingRules] = None,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  override type T = BatchRulesDefinition

  override def options(requestOptions: RequestOptions): BatchRulesDefinition =
    copy(requestOptions = Some(requestOptions))

  def inIndex(index: String): BatchRulesDefinition =
    copy(index = Some(index))

  def and(forwardToReplicas: ForwardToReplicas): BatchRulesDefinition =
    copy(forwardToReplicas = Some(forwardToReplicas))

  def and(clearExistingRules: ClearExistingRules): BatchRulesDefinition =
    copy(clearExistingRules = Some(clearExistingRules))

  override private[algolia] def build(): HttpPayload = {
    val path = Seq("1", "indexes") ++ index ++ Seq("rules", "batch")

    var queryParameters = Map.empty[String, String]
    forwardToReplicas.foreach(_ => queryParameters += ("forwardToReplicas" -> "true"))
    clearExistingRules.foreach(_ => queryParameters += ("clearExistingRules" -> "true"))

    HttpPayload(
      POST,
      path,
      queryParameters = Some(queryParameters),
      body = Some(write(rules)),
      isSearch = false,
      requestOptions = requestOptions
    )
  }
}

case class SearchRulesDefinition(
    indx: Option[String] = None,
    query: Option[QueryRules] = None,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  override type T = SearchRulesDefinition

  override def options(requestOptions: RequestOptions): SearchRulesDefinition =
    copy(requestOptions = Some(requestOptions))

  def index(indx: String): SearchRulesDefinition = copy(indx = Some(indx))

  def query(query: QueryRules): SearchRulesDefinition = copy(query = Some(query))

  override private[algolia] def build(): HttpPayload = {
    HttpPayload(
      POST,
      Seq("1", "indexes") ++ indx ++ Seq("rules", "search"),
      body = Some(write(query)),
      isSearch = true,
      requestOptions = requestOptions
    )
  }
}
