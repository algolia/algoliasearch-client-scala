package algolia

import java.util.concurrent.TimeoutException

import algolia.AlgoliaDsl._
import algolia.definitions.SearchDefinition
import algolia.http.HttpPayload
import algolia.responses.{Indexes, Search, Task}

import scala.concurrent.{ExecutionContext, Future}

class AlgoliaClient(applicationId: String, apiKey: String) {

  final private val ALGOLIANET_COM_HOST = "algolianet.com"
  final private val ALGOLIANET_HOST = "algolia.net"

  val httpClient: DispatchHttpClient = DispatchHttpClient
  val random: AlgoliaRandom = AlgoliaRandom

  lazy val indexingHosts: Seq[String] = random.shuffle(Seq(
    s"https://$applicationId-1.$ALGOLIANET_COM_HOST",
    s"https://$applicationId-2.$ALGOLIANET_COM_HOST",
    s"https://$applicationId-3.$ALGOLIANET_COM_HOST"
  )) :+ s"https://$applicationId.$ALGOLIANET_HOST"

  lazy val queryHosts: Seq[String] = random.shuffle(Seq(
    s"https://$applicationId-1.$ALGOLIANET_COM_HOST",
    s"https://$applicationId-2.$ALGOLIANET_COM_HOST",
    s"https://$applicationId-3.$ALGOLIANET_COM_HOST"
  )) :+ s"https://$applicationId-dsn.$ALGOLIANET_HOST"

  val userAgent = s"Algolia Scala ${util.Properties.versionNumberString}"

  val headers: Map[String, String] = Map(
    "Accept-Encoding" -> "gzip",
    "X-Algolia-Application-Id" -> applicationId,
    "X-Algolia-API-Key" -> apiKey,
    "User-Agent" -> userAgent,
    "Content-type" -> "application/json",
    "Accept" -> "application/json"
  )

  def search(query: SearchDefinition)(implicit executor: ExecutionContext): Future[Search] = request[Search](query.build())

  def indexes()(implicit executor: ExecutionContext): Future[Indexes] = execute {
    AlgoliaDsl.indexes
  }

  def clear(ind: String)(implicit executor: ExecutionContext): Future[Task] = execute {
    AlgoliaDsl.clear index ind
  }

  def delete(ind: String)(implicit executor: ExecutionContext): Future[Task] = execute {
    AlgoliaDsl.delete index ind
  }

  def execute[QUERY, RESULT](query: QUERY)(implicit executable: Executable[QUERY, RESULT], executor: ExecutionContext): Future[RESULT] = executable(this, query)

  private[algolia] def request[T: Manifest](payload: HttpPayload)(implicit executor: ExecutionContext): Future[T] = {
    queryHosts.foldLeft(Future.failed[T](new TimeoutException())) { (future, host) =>
      future.recoverWith {
        case e: APIClientException => Future.failed(e) //No retry if 4XX
        case _ => httpClient request[T](host, headers, payload)
      }
    }
  }
}



