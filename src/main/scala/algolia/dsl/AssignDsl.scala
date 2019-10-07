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
import algolia.definitions.{AssignUserIDDefinition, AssignUserIDsDefinition}
import algolia.inputs.{UserIDAssignment, UserIDsAssignment}
import algolia.{AlgoliaClient, Executable}
import algolia.responses.Created
import org.json4s.Formats

import scala.concurrent.{ExecutionContext, Future}

trait AssignDsl {

  implicit val formats: Formats

  case object assign {

    def userID(assignment: UserIDAssignment): AssignUserIDDefinition =
      AssignUserIDDefinition(assignment)

    def userIDs(assignment: UserIDsAssignment): AssignUserIDsDefinition =
      AssignUserIDsDefinition(assignment)

  }

  implicit object AssignUserIDExecutable extends Executable[AssignUserIDDefinition, Created] {
    override def apply(client: AlgoliaClient, query: AssignUserIDDefinition)(
        implicit executor: ExecutionContext): Future[Created] = {
      client.request[Created](query.build())
    }
  }

  implicit object AssignUserIDsExecutable extends Executable[AssignUserIDsDefinition, Created] {
    override def apply(client: AlgoliaClient, query: AssignUserIDsDefinition)(
        implicit executor: ExecutionContext): Future[Created] = {
      client.request[Created](query.build())
    }
  }
}
