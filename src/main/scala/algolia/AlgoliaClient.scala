package algolia

import java.util.concurrent.TimeoutException

import algolia.definitions.SearchDefinition
import algolia.responses.{SearchImpl, Search}
import org.json4s.native.Serialization.write

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

  def search(definition: SearchDefinition): Future[Search] = {
    implicit val formats = org.json4s.DefaultFormats
    post[SearchImpl](Seq("1", "indexes", definition.index.name, "query"), body = write(definition.build()))
  }

  def get[T: Manifest](path: Seq[String], queryParameters: Map[String, String] = Map()): Future[T] = {
    import scala.concurrent.ExecutionContext.Implicits.global
    queryHosts.foldLeft(Future.failed[T](new TimeoutException())) { (future, host) =>
      future.recoverWith {
        case _ => httpClient get[T](host, path, headers, queryParameters)
      }
    }
  }

  def post[T: Manifest](path: Seq[String], queryParameters: Map[String, String] = Map(), body: String = ""): Future[T] = {
    import scala.concurrent.ExecutionContext.Implicits.global
    queryHosts.foldLeft(Future.failed[T](new TimeoutException())) { (future, host) =>
      future.recoverWith {
        case _ => httpClient post[T](host, path, headers, queryParameters, body)
      }
    }
  }

  def execute[QUERY, RESULT](query: QUERY)(implicit executable: Executable[QUERY, RESULT]): Future[RESULT] = executable(this, query)

}



