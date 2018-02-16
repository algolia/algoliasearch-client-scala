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

package algolia.dsl

import algolia.definitions._
import algolia.objects.{AbstractSynonym, Rule}
import algolia.responses.{SearchSynonymResult, SynonymTask}
import algolia.{AlgoliaClient, Executable}
import org.json4s.Formats

import scala.concurrent.{ExecutionContext, Future}

trait SynonymsDsl {

  implicit val formats: Formats

  object save {

    def synonym(synonym: AbstractSynonym) =
      SaveSynonymDefinition(synonym = synonym)

    def synonyms(synonyms: Iterable[AbstractSynonym]) =
      BatchSynonymsDefinition(synonyms = synonyms)

    def rule(rule: Rule) =
      SaveRuleDefinition(rule = rule)

    def rules(rules: Iterable[Rule]) =
      BatchRulesDefinition(rules = rules)

  }

  implicit object GetSynonymDefinitionExecutable
      extends Executable[GetSynonymDefinition, AbstractSynonym] {

    override def apply(client: AlgoliaClient, query: GetSynonymDefinition)(
        implicit executor: ExecutionContext): Future[AbstractSynonym] = {
      client.request[AbstractSynonym](query.build())
    }

  }

  implicit object DeleteSynonymDefinitionExecutable
      extends Executable[DeleteSynonymDefinition, SynonymTask] {

    override def apply(client: AlgoliaClient, query: DeleteSynonymDefinition)(
        implicit executor: ExecutionContext): Future[SynonymTask] = {
      client.request[SynonymTask](query.build())
    }

  }

  implicit object ClearSynonymsDefinitionExecutable
      extends Executable[ClearSynonymsDefinition, SynonymTask] {

    override def apply(client: AlgoliaClient, query: ClearSynonymsDefinition)(
        implicit executor: ExecutionContext): Future[SynonymTask] = {
      client.request[SynonymTask](query.build())
    }

  }

  implicit object SaveSynonymDefinitionExecutable
      extends Executable[SaveSynonymDefinition, SynonymTask] {

    override def apply(client: AlgoliaClient, query: SaveSynonymDefinition)(
        implicit executor: ExecutionContext): Future[SynonymTask] = {
      client.request[SynonymTask](query.build())
    }

  }

  implicit object BatchSynonymsDefinitionExecutable
      extends Executable[BatchSynonymsDefinition, SynonymTask] {

    override def apply(client: AlgoliaClient, query: BatchSynonymsDefinition)(
        implicit executor: ExecutionContext): Future[SynonymTask] = {
      client.request[SynonymTask](query.build())
    }

  }

  implicit object SearchSynonymsDefinitionExecutable
      extends Executable[SearchSynonymsDefinition, SearchSynonymResult] {

    override def apply(client: AlgoliaClient, query: SearchSynonymsDefinition)(
        implicit executor: ExecutionContext): Future[SearchSynonymResult] = {
      client.request[SearchSynonymResult](query.build())
    }

  }

}
