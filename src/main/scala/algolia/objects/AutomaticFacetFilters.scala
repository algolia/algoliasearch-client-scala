package algolia.objects

case class AutomaticFacetFilters(facet: String,
                                 disjunctive: Option[Boolean] = None,
                                 score: Option[Int] = None)
