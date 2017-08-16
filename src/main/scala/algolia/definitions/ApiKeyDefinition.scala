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

import algolia.http._
import algolia.objects.{ApiKey, RequestOptions}
import algolia.responses.{AllKeys, CreateUpdateKey, DeleteKey}
import algolia.{AlgoliaClient, Executable}
import org.json4s.Formats
import org.json4s.native.Serialization._

import scala.concurrent.{ExecutionContext, Future}

case class GetApiKeyDefinition(keyName: String,
                               indexName: Option[String] = None,
                               requestOptions: Option[RequestOptions] = None)
    extends Definition {

  type T = GetApiKeyDefinition

  def from(indexName: String): GetApiKeyDefinition = copy(indexName = Some(indexName))

  override def options(requestOptions: RequestOptions): GetApiKeyDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    val path = if (indexName.isEmpty) {
      Seq("1", "keys", keyName)
    } else {
      Seq("1", "indexes", indexName.get, "keys", keyName)
    }

    HttpPayload(
      GET,
      path,
      isSearch = false,
      requestOptions = requestOptions
    )
  }
}

case class AddApiKeyDefinition(
    key: ApiKey,
    indexName: Option[String] = None,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {
  type T = AddApiKeyDefinition

  def to(indexName: String): AddApiKeyDefinition = copy(indexName = Some(indexName))

  override def options(requestOptions: RequestOptions): AddApiKeyDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    val path = if (indexName.isEmpty) {
      Seq("1", "keys")
    } else {
      Seq("1", "indexes", indexName.get, "keys")
    }

    HttpPayload(
      POST,
      path,
      body = Some(write(key)),
      isSearch = false,
      requestOptions = requestOptions
    )
  }
}

case class DeleteApiKeyDefinition(keyName: String,
                                  indexName: Option[String] = None,
                                  requestOptions: Option[RequestOptions] = None)
    extends Definition {

  type T = DeleteApiKeyDefinition

  def from(indexName: String): DeleteApiKeyDefinition = copy(indexName = Some(indexName))

  override def options(requestOptions: RequestOptions): DeleteApiKeyDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    val path = if (indexName.isEmpty) {
      Seq("1", "keys", keyName)
    } else {
      Seq("1", "indexes", indexName.get, "keys", keyName)
    }

    HttpPayload(
      DELETE,
      path,
      isSearch = false,
      requestOptions = requestOptions
    )
  }
}

case class UpdateApiKeyDefinition(
    keyName: String,
    key: Option[ApiKey] = None,
    indexName: Option[String] = None,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  type T = UpdateApiKeyDefinition

  def `with`(key: ApiKey): UpdateApiKeyDefinition = copy(key = Some(key))

  def from(indexName: String): UpdateApiKeyDefinition = copy(indexName = Some(indexName))

  override def options(requestOptions: RequestOptions): UpdateApiKeyDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    val path = if (indexName.isEmpty) {
      Seq("1", "keys", keyName)
    } else {
      Seq("1", "indexes", indexName.get, "keys", keyName)
    }

    HttpPayload(
      PUT,
      path,
      body = Some(write(key)),
      isSearch = false,
      requestOptions = requestOptions
    )
  }
}

case class GetAllApiKeyDefinition(indexName: Option[String] = None,
                                  requestOptions: Option[RequestOptions] = None)
    extends Definition {

  type T = GetAllApiKeyDefinition

  override def options(requestOptions: RequestOptions): GetAllApiKeyDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    val path = if (indexName.isEmpty) {
      Seq("1", "keys")
    } else {
      Seq("1", "indexes", indexName.get, "keys")
    }

    HttpPayload(
      GET,
      path,
      isSearch = false,
      requestOptions = requestOptions
    )
  }
}

trait ApiKeyDefinitionDsl {

  implicit val formats: Formats

  implicit object GetApiKeyDefinitionExecutable extends Executable[GetApiKeyDefinition, ApiKey] {
    override def apply(client: AlgoliaClient, query: GetApiKeyDefinition)(
        implicit executor: ExecutionContext): Future[ApiKey] =
      client.request[ApiKey](query.build())
  }

  implicit object AddApiKeyDefinitionExecutable
      extends Executable[AddApiKeyDefinition, CreateUpdateKey] {
    override def apply(client: AlgoliaClient, query: AddApiKeyDefinition)(
        implicit executor: ExecutionContext): Future[CreateUpdateKey] =
      client.request[CreateUpdateKey](query.build())
  }

  implicit object DeleteApiKeyDefinitionExecutable
      extends Executable[DeleteApiKeyDefinition, DeleteKey] {
    override def apply(client: AlgoliaClient, query: DeleteApiKeyDefinition)(
        implicit executor: ExecutionContext): Future[DeleteKey] =
      client.request[DeleteKey](query.build())
  }

  implicit object UpdateApiKeyDefinitionExecutable
      extends Executable[UpdateApiKeyDefinition, CreateUpdateKey] {
    override def apply(client: AlgoliaClient, query: UpdateApiKeyDefinition)(
        implicit executor: ExecutionContext): Future[CreateUpdateKey] =
      client.request[CreateUpdateKey](query.build())
  }

  implicit object GetAllApiKeyDefinitionExecutable
      extends Executable[GetAllApiKeyDefinition, AllKeys] {
    override def apply(client: AlgoliaClient, query: GetAllApiKeyDefinition)(
        implicit executor: ExecutionContext): Future[AllKeys] =
      client.request[AllKeys](query.build())
  }

}
