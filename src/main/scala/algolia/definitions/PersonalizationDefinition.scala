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

import algolia.http.{DELETE, GET, HttpPayload, POST}
import algolia.objects.{RequestOptions, SetStrategyRequest}
import org.json4s.Formats
import org.json4s.native.Serialization.write

case class GeStrategyDefinition(
    requestOptions: Option[RequestOptions] = None
)(implicit val formats: Formats)
    extends Definition {

  override type T = GeStrategyDefinition

  override def options(
      requestOptions: RequestOptions
  ): GeStrategyDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    HttpPayload(
      GET,
      Seq("1", "strategies", "personalization"),
      isSearch = false,
      isPersonalization = true,
      requestOptions = requestOptions
    )
  }

}

case class SetStrategyDefinition(
    s: SetStrategyRequest,
    requestOptions: Option[RequestOptions] = None
)(implicit val formats: Formats)
    extends Definition {

  override type T = SetStrategyDefinition

  override def options(
      requestOptions: RequestOptions
  ): SetStrategyDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    HttpPayload(
      POST,
      Seq("1", "strategies", "personalization"),
      body = Some(write(s)),
      isSearch = false,
      isPersonalization = true,
      requestOptions = requestOptions
    )
  }

}

case class GetPersonalizationProfileDefinition(
    userToken: String,
    requestOptions: Option[RequestOptions] = None
)(implicit val formats: Formats)
    extends Definition {
  override type T = GetPersonalizationProfileDefinition

  override def options(
      requestOptions: RequestOptions
  ): GetPersonalizationProfileDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build() = {
    HttpPayload(
      GET,
      Seq("1", "profiles", "personalization", userToken),
      isSearch = false,
      isPersonalization = true,
      requestOptions = requestOptions
    )
  }
}

case class DeletePersonalizationProfileDefinition(
    userToken: String,
    requestOptions: Option[RequestOptions] = None
)(implicit val formats: Formats)
    extends Definition {
  override type T = DeletePersonalizationProfileDefinition

  override def options(
      requestOptions: RequestOptions
  ): DeletePersonalizationProfileDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build() = {
    HttpPayload(
      DELETE,
      Seq("1", "profiles", userToken),
      isSearch = false,
      isPersonalization = true,
      requestOptions = requestOptions
    )
  }
}
