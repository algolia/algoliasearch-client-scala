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
    sortRemainingBy: Option[SortRule] = None
)

/**
  * Rule defining the sort order of facet values.
  */
sealed trait SortRule {
  val value: String
}

object SortRule {

  /**
    * Alphabetical (ascending)
    */
  case object alpha extends SortRule {
    override val value: String = "alpha"
  }

  /**
    * Facet count (descending)
    */
  case object count extends SortRule {
    override val value: String = "count"
  }

  /**
    * Hidden (show only pinned values)
    */
  case object hidden extends SortRule {
    override val value: String = "hidden"
  }
}
