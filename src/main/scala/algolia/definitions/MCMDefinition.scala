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
import algolia.inputs.{UserIDAssignment, UserIDsAssignment}
import algolia.objects.RequestOptions
import org.json4s.Formats
import org.json4s.native.Serialization.write

case class AssignUserIDDefinition(
    assignment: UserIDAssignment,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  override type T = AssignUserIDDefinition

  override def options(requestOptions: RequestOptions): AssignUserIDDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    val extraHeaders = Map("X-Algolia-User-ID" -> assignment.userID)
    val newRequestOptions = requestOptions match {
      case Some(opts) => opts.addExtraHeaders(extraHeaders)
      case None => RequestOptions(extraHeaders = Some(extraHeaders))
    }

    val body = Map(
      "cluster" -> assignment.cluster
    )

    HttpPayload(
      POST,
      Seq("1", "clusters", "mapping"),
      body = Some(write(body)),
      isSearch = false,
      requestOptions = Some(newRequestOptions)
    )
  }

}

case class AssignUserIDsDefinition(
    assignment: UserIDsAssignment,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  override type T = AssignUserIDsDefinition

  override def options(requestOptions: RequestOptions): AssignUserIDsDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    val body = Map(
      "cluster" -> assignment.cluster,
      "users" -> assignment.userIDs
    )

    HttpPayload(
      POST,
      Seq("1", "clusters", "mapping", "batch"),
      body = Some(write(body)),
      isSearch = false,
      requestOptions = requestOptions
    )
  }
}

case class GetTopUserIDDefinition(requestOptions: Option[RequestOptions] = None)
    extends Definition {

  override type T = GetTopUserIDDefinition

  override def options(requestOptions: RequestOptions): GetTopUserIDDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload =
    HttpPayload(
      GET,
      Seq("1", "clusters", "mapping", "top"),
      isSearch = true,
      requestOptions = requestOptions
    )
}

case class GetUserIDDefinition(userID: String, requestOptions: Option[RequestOptions] = None)
    extends Definition {

  override type T = GetUserIDDefinition

  override def options(requestOptions: RequestOptions): GetUserIDDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    HttpPayload(
      GET,
      Seq("1", "clusters", "mapping", userID),
      isSearch = true,
      requestOptions = requestOptions
    )
  }
}

case class ListClustersDefinition(requestOptions: Option[RequestOptions] = None)
    extends Definition {

  override type T = ListClustersDefinition

  override def options(requestOptions: RequestOptions): ListClustersDefinition =
    copy(Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    HttpPayload(
      GET,
      Seq("1", "clusters"),
      isSearch = true,
      requestOptions = requestOptions
    )
  }

}

case class ListUserIDsDefinition(
    page: Int = 0,
    hitsPerPage: Int = 20,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  override type T = ListUserIDsDefinition

  override def options(requestOptions: RequestOptions): ListUserIDsDefinition =
    copy(requestOptions = Some(requestOptions))

  def page(page: Int): ListUserIDsDefinition =
    copy(page = page)

  def hitsPerPage(hitsPerPage: Int): ListUserIDsDefinition =
    copy(hitsPerPage = hitsPerPage)

  override private[algolia] def build(): HttpPayload = {
    val body = Map(
      "page" -> page,
      "hitsPerPage" -> hitsPerPage
    )

    HttpPayload(
      GET,
      Seq("1", "clusters", "mapping"),
      isSearch = true,
      body = Some(write(body)),
      requestOptions = requestOptions
    )
  }

}

case class RemoveUserIDDefinition(userID: String, requestOptions: Option[RequestOptions] = None)
    extends Definition {

  override type T = RemoveUserIDDefinition

  override def options(requestOptions: RequestOptions): RemoveUserIDDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    val extraHeaders = Map("X-Algolia-User-ID" -> userID)
    val newRequestOptions = requestOptions match {
      case Some(opts) => opts.addExtraHeaders(extraHeaders)
      case None => RequestOptions(extraHeaders = Some(extraHeaders))
    }

    HttpPayload(
      DELETE,
      Seq("1", "clusters", "mapping"),
      isSearch = false,
      requestOptions = Some(newRequestOptions)
    )
  }

}

case class HadPendingMappingsDefinition(
    pending: Boolean = false,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  override type T = HadPendingMappingsDefinition

  override def options(requestOptions: RequestOptions): HadPendingMappingsDefinition =
    copy(requestOptions = Some(requestOptions))

  val queryParameters: Option[Map[String, String]] = Some(Map("getClusters" -> pending.toString))

  override private[algolia] def build(): HttpPayload = {
    HttpPayload(
      GET,
      Seq("1", "clusters", "mapping", "pending"),
      isSearch = false,
      queryParameters = queryParameters,
      requestOptions = requestOptions
    )
  }

}

case class SearchUserIDDefinition(
    query: String,
    cluster: String = "",
    page: Int = 0,
    hitsPerPage: Int = 20,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  override type T = SearchUserIDDefinition

  override def options(requestOptions: RequestOptions): SearchUserIDDefinition =
    copy(requestOptions = Some(requestOptions))

  def cluster(cluster: String): SearchUserIDDefinition =
    copy(cluster = cluster)

  def page(page: Int): SearchUserIDDefinition =
    copy(page = page)

  def hitsPerPage(hitsPerPage: Int): SearchUserIDDefinition =
    copy(hitsPerPage = hitsPerPage)

  override private[algolia] def build(): HttpPayload = {
    val body = Map(
      "query" -> query,
      "cluster" -> cluster,
      "page" -> page,
      "hitsPerPage" -> hitsPerPage
    )

    HttpPayload(
      POST,
      Seq("1", "clusters", "mapping", "search"),
      body = Some(write(body)),
      isSearch = true,
      requestOptions = requestOptions
    )
  }
}
