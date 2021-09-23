package algolia.objects

/**
  * Recommendations query options.
  *
  * @see [[https://www.algolia.com/doc/rest-api/recommend/#method-param-request-object Documentation]]
  */
sealed trait RecommendationsOptions {

  /** Name of the index to target */
  val indexName: String

  /** The recommendation model to use */
  val model: String

  /** The object ID to get recommendations for */
  val objectID: String

  /** The threshold to use when filtering recommendations by their score, default 0, between 0 and 100 */
  val threshold: Int = 0

  /** The maximum number of recommendations to retrieve */
  val maxRecommendations: Option[Int] = None

  /** Search parameters to filter the recommendations */
  val queryParameters: Option[Query] = None

  /** Search parameters to use as fallback when there are no recommendations */
  val fallbackParameters: Option[Query] = None
}

case class RecommendationsQuery(
    override val indexName: String,
    override val model: String,
    override val objectID: String
) extends RecommendationsOptions

case class RelatedProductsQuery(
    override val indexName: String,
    override val objectID: String
) extends RecommendationsOptions {
  override val model: String = "related-products"
}

case class FrequentlyBoughtTogetherQuery(
    override val indexName: String,
    override val objectID: String
) extends RecommendationsOptions {
  override val model: String = "bought-together"
}
