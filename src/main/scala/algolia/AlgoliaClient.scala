package algolia

import java.util.concurrent.TimeoutException

import algolia.AlgoliaDsl._
import algolia.definitions.SearchDefinition
import algolia.http.HttpPayload
import algolia.responses.{Indexes, Search, Task}

import scala.concurrent.Future

class AlgoliaClient(applicationId: String, apiKey: String) {

  final private val ALGOLIANET_COM_HOST = "algolianet.com"
  final private val ALGOLIANET_HOST = "algolia.net"

  val indexingHosts: Stream[String] = Stream(
    s"https://$applicationId-1.$ALGOLIANET_COM_HOST",
    s"https://$applicationId-2.$ALGOLIANET_COM_HOST",
    s"https://$applicationId-3.$ALGOLIANET_COM_HOST",
    s"https://$applicationId.$ALGOLIANET_HOST"
  )

  val queryHosts: Seq[String] = Seq(
    s"https://$applicationId-1.$ALGOLIANET_COM_HOST",
    s"https://$applicationId-2.$ALGOLIANET_COM_HOST",
    s"https://$applicationId-3.$ALGOLIANET_COM_HOST",
    s"https://$applicationId-dsn.$ALGOLIANET_HOST"
  )

  val userAgent = s"Algolia Scala ${util.Properties.versionNumberString}"

  val headers: Map[String, String] = Map(
    "Accept-Encoding" -> "gzip",
    "X-Algolia-Application-Id" -> applicationId,
    "X-Algolia-API-Key" -> apiKey,
    "User-Agent" -> userAgent,
    "Content-type" -> "application/json",
    "Accept" -> "application/json"
  )

  val httpClient: DispatchHttpClient = DispatchHttpClient

  def search(query: SearchDefinition): Future[Search] = request[Search](query.build())

  def indexes(): Future[Indexes] = execute {
    AlgoliaDsl.indexes
  }

  def clear(ind: String): Future[Task] = execute {
    AlgoliaDsl.clear index ind
  }

  def delete(ind: String): Future[Task] = execute {
    AlgoliaDsl.delete index ind
  }

  def execute[QUERY, RESULT](query: QUERY)(implicit executable: Executable[QUERY, RESULT]): Future[RESULT] = executable(this, query)

  /** * HTTP ***/
  private[algolia] def request[T: Manifest](payload: HttpPayload): Future[T] = {
    import scala.concurrent.ExecutionContext.Implicits.global
    queryHosts.foldLeft(Future.failed[T](new TimeoutException())) { (future, host) =>
      future.recoverWith {
        case _ => httpClient request[T](host, headers, payload)
      }
    }
  }
}



