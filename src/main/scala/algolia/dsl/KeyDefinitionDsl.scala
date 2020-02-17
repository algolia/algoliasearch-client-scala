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
import algolia.objects.ApiKey
import algolia.responses.{AllKeys, CreateUpdateKey, DeleteKey}
import algolia.{AlgoliaClient, Executable}
import org.json4s.Formats

import scala.concurrent.{ExecutionContext, Future}

trait KeyDefinitionDsl {

  implicit val formats: Formats

  implicit object GetApiKeyDefinitionExecutable
      extends Executable[GetKeyDefinition, ApiKey] {
    override def apply(client: AlgoliaClient, query: GetKeyDefinition)(
        implicit executor: ExecutionContext
    ): Future[ApiKey] =
      client.request[ApiKey](query.build())
  }

  implicit object AddApiKeyDefinitionExecutable
      extends Executable[AddKeyDefinition, CreateUpdateKey] {
    override def apply(client: AlgoliaClient, query: AddKeyDefinition)(
        implicit executor: ExecutionContext
    ): Future[CreateUpdateKey] =
      client.request[CreateUpdateKey](query.build())
  }

  implicit object DeleteApiKeyDefinitionExecutable
      extends Executable[DeleteKeyDefinition, DeleteKey] {
    override def apply(client: AlgoliaClient, query: DeleteKeyDefinition)(
        implicit executor: ExecutionContext
    ): Future[DeleteKey] =
      client.request[DeleteKey](query.build())
  }

  implicit object UpdateApiKeyDefinitionExecutable
      extends Executable[UpdateKeyDefinition, CreateUpdateKey] {
    override def apply(client: AlgoliaClient, query: UpdateKeyDefinition)(
        implicit executor: ExecutionContext
    ): Future[CreateUpdateKey] =
      client.request[CreateUpdateKey](query.build())
  }

  implicit object GetAllApiKeyDefinitionExecutable
      extends Executable[ListKeysDefinition, AllKeys] {
    override def apply(client: AlgoliaClient, query: ListKeysDefinition)(
        implicit executor: ExecutionContext
    ): Future[AllKeys] =
      client.request[AllKeys](query.build())
  }

}
