package algolia.http

private[algolia] sealed trait HttpVerb


private[algolia] case object GET extends HttpVerb

private[algolia] case object POST extends HttpVerb

private[algolia] case object PUT extends HttpVerb

private[algolia] case object DELETE extends HttpVerb

private[algolia] case class HttpPayload(verb: HttpVerb,
                                        path: Seq[String],
                                        queryParameters: Option[Map[String, String]] = None,
                                        body: Option[String] = None)
