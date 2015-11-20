package algolia

import dispatch.Defaults._
import dispatch._

trait DispatchHttpClient {
  implicit val formats = org.json4s.DefaultFormats

  def get[T](host: String,
             path: Seq[String],
             headers: Map[String, String],
             queryParameters: Map[String, String])(implicit m: Manifest[T]): Future[T] = {
    val request = path.foldLeft(url(host).secure) {
      (url, p) => url / p
    } <<? queryParameters <:< headers

    Http(request OK dispatch.as.json4s.Json)
      .map(_.extract[T])
  }

  def post[T](host: String,
              path: Seq[String],
              headers: Map[String, String],
              queryParameters: Map[String, String],
              body: String)(implicit m: Manifest[T]): Future[T] = {
    val request = path.foldLeft(url(host).secure) {
      (url, p) => url / p
    } <<? queryParameters <:< headers << body

    Http(request OK dispatch.as.json4s.Json)
      .map(_.extract[T])
  }


}

object DispatchHttpClient extends DispatchHttpClient