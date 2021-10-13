package algolia.objects

sealed trait RequestEndpoint

object RequestEndpoint {
  object Search extends RequestEndpoint
  object Indexing extends RequestEndpoint
  object Analytics extends RequestEndpoint
  object Insights extends RequestEndpoint
  object Personalization extends RequestEndpoint
}
