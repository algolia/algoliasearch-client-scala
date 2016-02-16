/*
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

package algolia.responses

case class IndexSettings(/* INDEXING PARAMETERS */
                         attributesToIndex: Option[Seq[AttributesToIndex]] = None,
                         numericAttributesToIndex: Option[Seq[NumericAttributesToIndex]] = None,
                         attributesForFaceting: Option[Seq[String]] = None,
                         attributeForDistinct: Option[String] = None,
                         ranking: Option[Seq[Ranking]] = None,
                         customRanking: Option[Seq[CustomRanking]] = None,
                         separatorsToIndex: Option[String] = None,
                         slaves: Option[Seq[String]] = None,
                         unretrievableAttributes: Option[Seq[String]] = None,
                         allowCompressionOfIntegerArray: Option[Boolean] = None,

                         /* QUERY EXPANSION */
                         synonyms: Option[Seq[Seq[String]]] = None,
                         placeholders: Option[Map[String, Seq[String]]] = None,
                         altCorrections: Option[Seq[AltCorrection]] = None,
                         disableTypoToleranceOnWords: Option[Seq[String]] = None,
                         disableTypoToleranceOnAttributes: Option[Seq[String]] = None,

                         /* DEFAULT QUERY PARAMETERS (CAN BE OVERRIDDEN AT QUERY-TIME) */
                         minWordSizefor1Typo: Option[Int] = None,
                         minWordSizefor2Typos: Option[Int] = None,
                         hitsPerPage: Option[Int] = None,
                         attributesToRetrieve: Option[Seq[String]] = None,
                         attributesToHighlight: Option[Seq[String]] = None,
                         attributesToSnippet: Option[Seq[String]] = None,
                         queryType: Option[QueryType] = None,
                         minProximity: Option[Int] = None,
                         highlightPreTag: Option[String] = None,
                         highlightPostTag: Option[String] = None,
                         optionalWords: Option[Seq[String]] = None,
                         allowTyposOnNumericTokens: Option[Boolean] = None,
                         ignorePlurals: Option[Boolean] = None,
                         advancedSyntax: Option[Boolean] = None,
                         replaceSynonymsInHighlight: Option[Boolean] = None,
                         maxValuesPerFacet: Option[Int] = None,
                         distinct: Option[Int] = None,
                         typoTolerance: Option[TypoTolerance] = None,
                         removeStopWords: Option[Boolean] = None,
                         snippetEllipsisText: Option[String] = None)

case class AltCorrection(word: String, correction: String, nbTypos: Int)

sealed trait AttributesToIndex

object AttributesToIndex {

  case class unordered(attribute: String) extends AttributesToIndex

  case class attribute(attribute: String) extends AttributesToIndex

  case class attributes(attributes: String*) extends AttributesToIndex

}

sealed trait NumericAttributesToIndex

object NumericAttributesToIndex {

  case class equalOnly(attribute: String) extends NumericAttributesToIndex

}

sealed trait Ranking

object Ranking {

  case class asc(attribute: String) extends Ranking

  case class desc(attribute: String) extends Ranking

  case object typo extends Ranking

  case object geo extends Ranking

  case object words extends Ranking

  case object proximity extends Ranking

  case object attribute extends Ranking

  case object exact extends Ranking

  case object custom extends Ranking

}

sealed trait CustomRanking

object CustomRanking {

  case class asc(attribute: String) extends CustomRanking

  case class desc(attribute: String) extends CustomRanking

}

sealed trait QueryType

object QueryType {

  case object prefixAll extends QueryType

  case object prefixLast extends QueryType

  case object prefixNone extends QueryType

}

sealed trait TypoTolerance

object TypoTolerance {

  case object `true` extends TypoTolerance

  case object `false` extends TypoTolerance

  case object min extends TypoTolerance

  case object strict extends TypoTolerance

}
