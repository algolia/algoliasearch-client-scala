package algolia

case class HttpPayload(path: Seq[String],
                       queryParameters: Option[Map[String, String]] = None,
                       body: Option[String] = None)
