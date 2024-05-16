package algolia.objects

case class FacetStats (
  min: Float,
  max: Float,
  avg: Option[Float] = None,
  sum: Option[Float] = None
)
