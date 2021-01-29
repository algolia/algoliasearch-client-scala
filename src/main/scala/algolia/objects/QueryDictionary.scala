package algolia.objects

case class QueryDictionary(
    query: Option[String] = None,
    page: Option[Int] = None,
    hitsPerPage: Option[Int] = None,
    language: Option[String] = None,
    customParameters: Option[Map[String, String]] = None
) {

  def toQueryParam: Map[String, String] = {
    val queryParam = Map(
      "query" -> query,
      "page" -> page.map(_.toString),
      "hitsPerPage" -> hitsPerPage.map(_.toString),
      "language" -> language
    ).filter { case (_, v) => v.isDefined }
      .map { case (k, v) => k -> v.get }

    customParameters.fold(queryParam)(c => queryParam ++ c)
  }
}
