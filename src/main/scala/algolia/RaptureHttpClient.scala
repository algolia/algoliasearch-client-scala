package algolia

import rapture.net.{HttpResponse, HttpPathRoot}

trait RaptureHttpClient {

  def get(host: HttpPathRoot, path: String, headers: Map[String, String]): Option[HttpResponse] = {
    import rapture.core.modes.returnOption._
    host / path httpGet headers
  }
  
}

object RaptureHttpClient extends RaptureHttpClient
