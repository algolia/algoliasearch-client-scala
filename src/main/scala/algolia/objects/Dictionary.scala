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

sealed trait Dictionary[+T <: DictionaryEntry] {
  val name: String
}

object Dictionary {

  case object Plurals extends Dictionary[PluralEntry] {
    override val name: String = "plurals"
  }

  case object Stopwords extends Dictionary[StopwordEntry] {
    override val name: String = "stopwords"
  }
  case object Compounds extends Dictionary[CompoundEntry] {
    override val name: String = "compounds"
  }
}

sealed trait DictionaryEntry {
  val objectID: String
  val language: String
}

case class StopwordEntry(
    objectID: String,
    language: String,
    word: String,
    state: String
) extends DictionaryEntry

case class PluralEntry(
    objectID: String,
    language: String,
    words: Seq[String]
) extends DictionaryEntry

case class CompoundEntry(
    objectID: String,
    language: String,
    word: String,
    decomposition: Seq[String]
) extends DictionaryEntry
