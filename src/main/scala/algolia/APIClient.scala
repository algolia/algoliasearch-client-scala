package algolia

import rapture.net._

class APIClient(applicationID: String, apiKey: String) {

  private val ALGOLIANET_COM_HOST = "algolianet.com"
  private val ALGOLIANET_HOST = "algolia.net"

  val indexingHosts: Stream[HttpPathRoot] = Stream(
    Https / s"$applicationID-1.$ALGOLIANET_COM_HOST",
    Https / s"$applicationID-2.$ALGOLIANET_COM_HOST",
    Https / s"$applicationID-3.$ALGOLIANET_COM_HOST",
    Https / s"$applicationID.$ALGOLIANET_HOST"
  )

  val queryHosts: Stream[HttpPathRoot] = Stream(
    Https / s"$applicationID-1.$ALGOLIANET_COM_HOST",
    Https / s"$applicationID-2.$ALGOLIANET_COM_HOST",
    Https / s"$applicationID-3.$ALGOLIANET_COM_HOST",
    Https / s"$applicationID-dsn.$ALGOLIANET_HOST"
  )

  val userAgent = s"Algolia Scala ${util.Properties.versionNumberString}"

  val headers: Map[String, String] = Map(
    "Accept-Encoding" -> "gzip",
    "X-Algolia-Application-Id" -> applicationID,
    "X-Algolia-API-Key" -> apiKey,
    "User-Agent" -> userAgent,
    "Content-type" -> "application/json"
  )

  val httpClient: RaptureHttpClient = RaptureHttpClient

  implicit val defaultHttpTimeout = new HttpTimeout(1000)


  //  protected def post(host: HttpPathRoot, path: String): Option[HttpResponse] = {
  //    import rapture.core.modes.returnOption._
  //    host / path httpPost(None, headers)
  //  }

  def get(path: String): Option[HttpResponse] = {
    queryHosts.map(host => httpClient get(host, path, headers)).find(_.isDefined).flatten
  }

  //  private def post(path: String): Option[HttpResponse] = {
  //    queryHosts.map(host => post(host, path)).find(_.isDefined).flatten
  //  }

  def indexes() = {
    get("/1/indexes")
  }

  //
  //  import ExecutionContext.Implicits.global
  //  private def get(host: HttpPathRoot, path: String): Future[HttpResponse] = {
  //    import modes.returnFuture._
  //    host / path httpGet headers
  //  }
  //
  //  private def post(host: HttpPathRoot, path: String): Future[HttpResponse] = {
  //    import modes.returnFuture._
  //    host / path httpPost headers
  //  }

}
