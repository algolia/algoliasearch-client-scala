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
import org.json4s.Formats
import org.json4s.native.Serialization._

case class GetKeyDefinition(keyName: String,
                            indexName: Option[String] = None,
                            requestOptions: Option[RequestOptions] = None)
    extends Definition {

  type T = GetKeyDefinition

  @deprecated("use without index", "1.27.0")
  def from(indexName: String): GetKeyDefinition = copy(indexName = Some(indexName))

  override def options(requestOptions: RequestOptions): GetKeyDefinition =
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

case class AddKeyDefinition(
    key: ApiKey,
    indexName: Option[String] = None,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {
  type T = AddKeyDefinition

  @deprecated("use without index", "1.27.0")
  def to(indexName: String): AddKeyDefinition = copy(indexName = Some(indexName))

  override def options(requestOptions: RequestOptions): AddKeyDefinition =
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

case class RestoreKeyDefinition(key: String, requestOptions: Option[RequestOptions] = None)(
    implicit val formats: Formats)
    extends Definition {
  type T = RestoreKeyDefinition

  override def options(requestOptions: RequestOptions): RestoreKeyDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    HttpPayload(
      POST,
      Seq("1", "keys", key, "restore"),
      body = None,
      isSearch = false,
      requestOptions = requestOptions
    )
  }
}

case class DeleteKeyDefinition(keyName: String,
                               indexName: Option[String] = None,
                               requestOptions: Option[RequestOptions] = None)
    extends Definition {

  type T = DeleteKeyDefinition

  @deprecated("use without index", "1.27.0")
  def from(indexName: String): DeleteKeyDefinition = copy(indexName = Some(indexName))

  override def options(requestOptions: RequestOptions): DeleteKeyDefinition =
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

case class UpdateKeyDefinition(
    keyName: String,
    key: Option[ApiKey] = None,
    indexName: Option[String] = None,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  type T = UpdateKeyDefinition

  def `with`(key: ApiKey): UpdateKeyDefinition = copy(key = Some(key))

  @deprecated("use without index", "1.27.0")
  def from(indexName: String): UpdateKeyDefinition = copy(indexName = Some(indexName))

  override def options(requestOptions: RequestOptions): UpdateKeyDefinition =
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

case class ListKeysDefinition(indexName: Option[String] = None,
                              requestOptions: Option[RequestOptions] = None)
    extends Definition {

  type T = ListKeysDefinition

  override def options(requestOptions: RequestOptions): ListKeysDefinition =
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
