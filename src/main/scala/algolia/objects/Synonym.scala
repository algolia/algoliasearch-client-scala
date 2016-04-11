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

package algolia.objects

sealed trait AbstractSynonym {

  val objectID: String
  private[algolia] val `type`: SynonymType

}

object Synonym {

  /*
  {
   "objectID":"NAME",
   "type":"oneWaySynoym",
   "input": "word1",
   "synonyms":[
      "word2 word3",
      "word4"
   ]
  }
  */
  case class OneWaySynonym(objectID: String,
                           input: String,
                           synonyms: Seq[String]) extends AbstractSynonym {

    override private[algolia] val `type`: SynonymType = SynonymType.oneWaySynonym
  }

  /*
  {
   "objectID":"NAME",
   "type":"synonym",
   "synonyms":[
      "word1",
      "word2 word3",
      "word4"
    ]
   }
  */
  case class Synonym(objectID: String, synonyms: Seq[String]) extends AbstractSynonym {

    override private[algolia] val `type`: SynonymType = SynonymType.synonym
  }

  /*
  {
    "objectID":"NAME",
    "type":"altCorrection1|altCorrection2",
    "word": "word1",
    "corrections":[
      "word2",
      "word3"
    ]
  }
  */
  case class AltCorrection1(objectID: String,
                            word: String,
                            corrections: Seq[String]) extends AbstractSynonym {

    override private[algolia] val `type`: SynonymType = SynonymType.altCorrection1
  }

  case class AltCorrection2(objectID: String,
                            word: String,
                            corrections: Seq[String]) extends AbstractSynonym {

    override private[algolia] val `type`: SynonymType = SynonymType.altCorrection2
  }

  /*
  {
    "objectID":"NAME",
    "type":"placeholder",
    "placeholder": "<number>",
    "replacements":[
    "1",
    "2",
    "3"
    ]
  }
  */
  case class Placeholder(objectID: String,
                         placeholder: String,
                         replacements: Seq[String]) extends AbstractSynonym {

    override private[algolia] val `type`: SynonymType = SynonymType.placeholder
  }

}

sealed trait SynonymType {
  val name: String

  override def toString = name
}

object SynonymType {

  case object synonym extends SynonymType {
    override val name: String = "synonym"
  }

  case object oneWaySynonym extends SynonymType {
    override val name: String = "oneWaySynonym"
  }

  case object placeholder extends SynonymType {
    override val name: String = "placeholder"
  }

  case object altCorrection1 extends SynonymType {
    override val name: String = "altCorrection1"
  }

  case object altCorrection2 extends SynonymType {
    override val name: String = "altCorrection2"
  }

}
