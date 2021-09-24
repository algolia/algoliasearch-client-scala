/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Algolia
 * http://www.algolia.com/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
  val threshold: Int

  /** The maximum number of recommendations to retrieve */
  val maxRecommendations: Option[Int]

  /** Search parameters to filter the recommendations */
  val queryParameters: Option[Query]

  /** Search parameters to use as fallback when there are no recommendations */
  val fallbackParameters: Option[Query]
}

case class RecommendationsQuery(
    override val indexName: String,
    override val model: String,
    override val objectID: String,
    override val threshold: Int = 0,
    override val maxRecommendations: Option[Int] = None,
    override val queryParameters: Option[Query] = None,
    override val fallbackParameters: Option[Query] = None
) extends RecommendationsOptions

case class RelatedProductsQuery(
    override val indexName: String,
    override val objectID: String,
    override val threshold: Int = 0,
    override val maxRecommendations: Option[Int] = None,
    override val queryParameters: Option[Query] = None,
    override val fallbackParameters: Option[Query] = None
) extends RecommendationsOptions {
  override val model: String = "related-products"
}

case class FrequentlyBoughtTogetherQuery(
    override val indexName: String,
    override val objectID: String,
    override val threshold: Int = 0,
    override val maxRecommendations: Option[Int] = None,
    override val queryParameters: Option[Query] = None,
    override val fallbackParameters: Option[Query] = None
) extends RecommendationsOptions {
  override val model: String = "bought-together"
}
