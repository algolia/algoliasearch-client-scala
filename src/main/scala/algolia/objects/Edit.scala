package algolia.objects

case class Edit(`type`: String, delete: String, insert: Option[String] = None)
