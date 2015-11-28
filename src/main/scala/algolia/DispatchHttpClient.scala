package algolia

import algolia.http._
import dispatch.Defaults._
import dispatch._

private[algolia] trait DispatchHttpClient {
  implicit val formats = org.json4s.DefaultFormats

  def request[T: Manifest](host: String, headers: Map[String, String], payload: HttpPayload): Future[T] = {
    val path = payload.path.foldLeft(url(host).secure) {
      (url, p) => url / p
    }

    var request = (payload.verb match {
      case GET => path.GET
      case POST => path.POST
      case PUT => path.PUT
      case DELETE => path.DELETE
    }) <:< headers

    if (payload.queryParameters.isDefined) {
      request = request <<? payload.queryParameters.get
    }

    if (payload.body.isDefined) {
      request = request << payload.body.get
    }

    Http(request OK dispatch.as.json4s.Json).map(_.extract[T])
  }

}

private[algolia] object DispatchHttpClient extends DispatchHttpClient