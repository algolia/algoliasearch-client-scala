package algolia.objects

/**
  * Content defining how the search interface should be rendered. This is set via the settings for a default value
  * and can be overridden via rules.
  */
case class RenderingContent(
    facetOrdering: Option[FacetOrdering] = None
)

/**
  * Facets and facets values ordering rules container.
  */
case class FacetOrdering(
    facets: FacetsOrder,
    values: Map[String, FacetValuesOrder]
)

/**
  * Define or override the way facet attributes are displayed.
  */
case class FacetsOrder(
    order: Seq[String]
)

/**
  * Facet values ordering rule container.
  */
case class FacetValuesOrder(
    order: Seq[String],
    sortRemainingBy: String
)
