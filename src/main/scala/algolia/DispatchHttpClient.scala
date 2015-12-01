package algolia

import algolia.http._
import com.ning.http.client.Response
import dispatch._
import dispatch.as.json4s._
import org.json4s._

private[algolia] trait DispatchHttpClient {
  implicit val formats = DefaultFormats

  import scala.concurrent.ExecutionContext.Implicits.global

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

    val responseManager: Response => T = { response =>
      response.getStatusCode / 100 match {
        case 2 => Json(response).extract[T]
        case 4 => throw `4XX`(response.getStatusCode, (Json(response) \ "message").extract[String])
        case _ => throw UnexpectedResponse(response.getStatusCode)
      }
    }

    Http(request > responseManager)
  }

}

case class `4XX`(code: Int, message: String) extends Exception("Failure \"%s\", response status: %d".format(message, code))

case class UnexpectedResponse(code: Int) extends Exception("Unexpected response status: %d".format(code))

private[algolia] case class `4XXResponse`(message: String)

private[algolia] object DispatchHttpClient extends DispatchHttpClient