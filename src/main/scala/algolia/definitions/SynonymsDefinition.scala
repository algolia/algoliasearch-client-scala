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

package algolia.definitions

import algolia.AlgoliaDsl.{ForwardToSlave, ReplaceExistingSynonyms}
import algolia.http._
import algolia.objects.{AbstractSynonym, QuerySynonyms}
import algolia.responses.{SearchSynonymResult, Task}
import algolia.{AlgoliaClient, Executable}
import org.json4s.Formats
import org.json4s.native.Serialization._

import scala.concurrent.{ExecutionContext, Future}

case class GetSynonymDefinition(synId: String, index: Option[String] = None)(implicit val formats: Formats) extends Definition {

  def from(index: String) = copy(index = Some(index))

  override private[algolia] def build(): HttpPayload = {
    HttpPayload(
      GET,
      Seq("1", "indexes") ++ index ++ Seq("synonyms", synId),
      isSearch = true
    )
  }

}

case class DeleteSynonymDefinition(synId: String, index: Option[String] = None, option: Option[ForwardToSlave] = None)(implicit val formats: Formats) extends Definition {

  def from(index: String) = copy(index = Some(index))

  def and(option: ForwardToSlave) = copy(option = Some(option))

  override private[algolia] def build(): HttpPayload = {
    val queryParameters = if (option.isDefined) {
      Some(Map("forwardToSlaves" -> "true"))
    } else {
      None
    }

    HttpPayload(
      DELETE,
      Seq("1", "indexes") ++ index ++ Seq("synonyms", synId),
      queryParameters = queryParameters,
      isSearch = false
    )
  }

}

case class ClearSynonymsDefinition(index: Option[String] = None, option: Option[ForwardToSlave] = None)(implicit val formats: Formats) extends Definition {

  def index(index: String) = copy(index = Some(index))

  def and(option: ForwardToSlave) = copy(option = Some(option))

  override private[algolia] def build(): HttpPayload = {
    val path = Seq("1", "indexes") ++ index ++ Seq("synonyms", "clear")

    val queryParameters = if (option.isDefined) {
      Some(Map("forwardToSlaves" -> "true"))
    } else {
      None
    }

    HttpPayload(
      POST,
      path,
      queryParameters = queryParameters,
      isSearch = false
    )
  }
}

case class SaveSynonymDefinition(synonym: AbstractSynonym, index: Option[String] = None, option: Option[ForwardToSlave] = None)(implicit val formats: Formats) extends Definition {

  def inIndex(index: String) = copy(index = Some(index))

  def and(option: ForwardToSlave) = copy(option = Some(option))

  override private[algolia] def build(): HttpPayload = {
    val path = Seq("1", "indexes") ++ index ++ Seq("synonyms", synonym.objectID)

    val queryParameters = if (option.isDefined) {
      Some(Map("forwardToSlaves" -> "true"))
    } else {
      None
    }

    HttpPayload(
      PUT,
      path,
      queryParameters = queryParameters,
      body = Some(write(synonym)),
      isSearch = false
    )
  }
}

case class BatchSynonymsDefinition(synonyms: Iterable[AbstractSynonym], index: Option[String] = None, forward: Option[ForwardToSlave] = None, replace: Option[ReplaceExistingSynonyms] = None)(implicit val formats: Formats) extends Definition {

  def inIndex(index: String) = copy(index = Some(index))

  def and(forward: ForwardToSlave) = copy(forward = Some(forward))

  def and(replace: ReplaceExistingSynonyms) = copy(replace = Some(replace))

  override private[algolia] def build(): HttpPayload = {
    val path = Seq("1", "indexes") ++ index ++ Seq("synonyms", "batch")

    var queryParameters = Map.empty[String, String]
    forward.foreach(_ => queryParameters += ("forwardToSlaves" -> "true"))
    replace.foreach(_ => queryParameters += ("replaceExistingSynonyms" -> "true"))

    HttpPayload(
      POST,
      path,
      queryParameters = Some(queryParameters),
      body = Some(write(synonyms)),
      isSearch = false
    )
  }
}

case class SearchSynonymsDefinition(indx: Option[String] = None, query: Option[QuerySynonyms] = None)(implicit val formats: Formats) extends Definition {

  def index(indx: String) = copy(indx = Some(indx))

  def query(query: QuerySynonyms) = copy(query = Some(query))

  override private[algolia] def build(): HttpPayload = {
    HttpPayload(
      POST,
      Seq("1", "indexes") ++ indx ++ Seq("synonyms", "search"),
      body = Some(write(query)),
      isSearch = true
    )
  }
}

trait SynonymsDsl {

  implicit val formats: Formats

  object save {

    def synonym(synonym: AbstractSynonym) = SaveSynonymDefinition(synonym = synonym)

    def synonyms(synonyms: Iterable[AbstractSynonym]) = BatchSynonymsDefinition(synonyms = synonyms)

  }

  implicit object GetSynonymDefinitionExecutable extends Executable[GetSynonymDefinition, AbstractSynonym] {

    override def apply(client: AlgoliaClient, query: GetSynonymDefinition)(implicit executor: ExecutionContext): Future[AbstractSynonym] = {
      client request[AbstractSynonym] query.build()
    }

  }

  implicit object DeleteSynonymDefinitionExecutable extends Executable[DeleteSynonymDefinition, Task] {

    override def apply(client: AlgoliaClient, query: DeleteSynonymDefinition)(implicit executor: ExecutionContext): Future[Task] = {
      client request[Task] query.build()
    }

  }

  implicit object ClearSynonymsDefinitionExecutable extends Executable[ClearSynonymsDefinition, Task] {

    override def apply(client: AlgoliaClient, query: ClearSynonymsDefinition)(implicit executor: ExecutionContext): Future[Task] = {
      client request[Task] query.build()
    }

  }

  implicit object SaveSynonymDefinitionExecutable extends Executable[SaveSynonymDefinition, Task] {

    override def apply(client: AlgoliaClient, query: SaveSynonymDefinition)(implicit executor: ExecutionContext): Future[Task] = {
      client request[Task] query.build()
    }

  }

  implicit object BatchSynonymsDefinitionExecutable extends Executable[BatchSynonymsDefinition, Task] {

    override def apply(client: AlgoliaClient, query: BatchSynonymsDefinition)(implicit executor: ExecutionContext): Future[Task] = {
      client request[Task] query.build()
    }

  }

  implicit object SearchSynonymsDefinitionExecutable extends Executable[SearchSynonymsDefinition, SearchSynonymResult] {

    override def apply(client: AlgoliaClient, query: SearchSynonymsDefinition)(implicit executor: ExecutionContext): Future[SearchSynonymResult] = {
      client request[SearchSynonymResult] query.build()
    }

  }

}
