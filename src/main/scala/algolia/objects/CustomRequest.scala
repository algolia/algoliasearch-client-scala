package algolia.objects

import algolia.http.HttpVerb

case class CustomRequest(
    verb: HttpVerb,
    path: Seq[String],
    endpoint: RequestEndpoint,
    queryParameters: Option[Map[String, String]] = None,
    body: Option[String] = None,
    requestOptions: Option[RequestOptions] = None
)

sealed trait RequestEndpoint

object RequestEndpoint {
  object Search extends RequestEndpoint
  object Indexing extends RequestEndpoint
  object Analytics extends RequestEndpoint
  object Insights extends RequestEndpoint
  object Personalization extends RequestEndpoint
}
