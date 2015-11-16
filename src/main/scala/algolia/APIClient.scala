package algolia

class APIClient(applicationID: String, apiKey: String) {

  private val ALGOLIANET_COM_HOST = "algolianet.com"
  private val ALGOLIANET_HOST = "algolia.net"

  val indexingHosts = Seq(
    s"$applicationID-1.$ALGOLIANET_COM_HOST",
    s"$applicationID-2.$ALGOLIANET_COM_HOST",
    s"$applicationID-3.$ALGOLIANET_COM_HOST",
    s"$applicationID.$ALGOLIANET_HOST"
  )

  val queryHosts = Seq(
    s"$applicationID-1.$ALGOLIANET_COM_HOST",
    s"$applicationID-2.$ALGOLIANET_COM_HOST",
    s"$applicationID-3.$ALGOLIANET_COM_HOST",
    s"$applicationID-dsn.$ALGOLIANET_HOST"
  )

  val userAgent = s"Algolia Scala ${util.Properties.versionNumberString}"

}
