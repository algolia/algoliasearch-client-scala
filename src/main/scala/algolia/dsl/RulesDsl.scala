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
import algolia.objects.Rule
import algolia.responses.{SearchRuleResult, Task}
import algolia.{AlgoliaClient, AlgoliaClientException, Executable}
import org.json4s.Formats

import scala.concurrent.{ExecutionContext, Future}

trait RulesDsl {

  implicit val formats: Formats

  implicit object GetRuleDefinitionExecutable
      extends Executable[GetRuleDefinition, Rule] {

    override def apply(client: AlgoliaClient, query: GetRuleDefinition)(
        implicit executor: ExecutionContext
    ): Future[Rule] = {
      client.request[Rule](query.build())
    }

  }

  implicit object DeleteRuleDefinitionExecutable
      extends Executable[DeleteRuleDefinition, Task] {

    override def apply(client: AlgoliaClient, query: DeleteRuleDefinition)(
        implicit executor: ExecutionContext
    ): Future[Task] = {
      client.request[Task](query.build())
    }

  }

  implicit object ClearRulesDefinitionExecutable
      extends Executable[ClearRulesDefinition, Task] {

    override def apply(client: AlgoliaClient, query: ClearRulesDefinition)(
        implicit executor: ExecutionContext
    ): Future[Task] = {
      client.request[Task](query.build())
    }

  }

  implicit object SaveRuleDefinitionExecutable
      extends Executable[SaveRuleDefinition, Task] {

    override def apply(client: AlgoliaClient, query: SaveRuleDefinition)(
        implicit executor: ExecutionContext
    ): Future[Task] = {
      if (query.rule.objectID.isEmpty) {
        return Future.failed(
          new AlgoliaClientException(s"rule's 'objectID' cannot be empty")
        )
      }
      client.request[Task](query.build())
    }

  }

  implicit object BatchRulesDefinitionExecutable
      extends Executable[BatchRulesDefinition, Task] {

    override def apply(client: AlgoliaClient, query: BatchRulesDefinition)(
        implicit executor: ExecutionContext
    ): Future[Task] = {
      client.request[Task](query.build())
    }

  }

  implicit object SearchRulesDefinitionExecutable
      extends Executable[SearchRulesDefinition, SearchRuleResult] {

    override def apply(client: AlgoliaClient, query: SearchRulesDefinition)(
        implicit executor: ExecutionContext
    ): Future[SearchRuleResult] = {
      client.request[SearchRuleResult](query.build())
    }

  }

}
