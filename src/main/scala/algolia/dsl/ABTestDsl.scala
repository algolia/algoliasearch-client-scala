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
import algolia.responses._
import algolia.{AlgoliaClient, Executable}
import org.json4s.Formats

import scala.concurrent.{ExecutionContext, Future}

trait ABTestDsl {

  implicit val formats: Formats

  implicit object AddABTestDefinitionExecutable
      extends Executable[AddABTestDefinition, ABTestTask] {
    override def apply(client: AlgoliaClient, query: AddABTestDefinition)(
        implicit executor: ExecutionContext
    ): Future[ABTestTask] = {
      client.request[ABTestTask](query.build())
    }
  }

  implicit object GetABTestDefinitionExecutable
      extends Executable[GetABTestDefinition, ABTestResponse] {
    override def apply(client: AlgoliaClient, query: GetABTestDefinition)(
        implicit executor: ExecutionContext
    ): Future[ABTestResponse] = {
      client.request[ABTestResponse](query.build())
    }
  }

  implicit object StopABTestDefinitionExecutable
      extends Executable[StopABTestDefinition, ABTestTask] {
    override def apply(client: AlgoliaClient, query: StopABTestDefinition)(
        implicit executor: ExecutionContext
    ): Future[ABTestTask] = {
      client.request[ABTestTask](query.build())
    }
  }

  implicit object DeleteABTestDefinitionExecutable
      extends Executable[DeleteABTestDefinition, ABTestTask] {
    override def apply(client: AlgoliaClient, query: DeleteABTestDefinition)(
        implicit executor: ExecutionContext
    ): Future[ABTestTask] = {
      client.request[ABTestTask](query.build())
    }
  }

  implicit object GetABTestsDefinitionExecutable
      extends Executable[GetABTestsDefinition, ABTestsResponse] {
    override def apply(client: AlgoliaClient, query: GetABTestsDefinition)(
        implicit executor: ExecutionContext
    ): Future[ABTestsResponse] = {
      client.request[ABTestsResponse](query.build())
    }
  }

}
