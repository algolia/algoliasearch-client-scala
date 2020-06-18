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

import algolia.definitions.{WaitForTaskDefinition, WaitForTimeoutException}
import algolia.responses.{AlgoliaTask, TaskStatus}
import algolia.{AlgoliaClient, Executable}

import scala.concurrent.{ExecutionContext, Future}

trait WaitForTaskDsl {

  case object waitFor {
    def task(task: AlgoliaTask): WaitForTaskDefinition =
      WaitForTaskDefinition(task.idToWaitFor)

    def task(taskID: Long): WaitForTaskDefinition =
      WaitForTaskDefinition(taskID)
  }

  implicit object WaitForTaskDefinitionExecutable
      extends Executable[WaitForTaskDefinition, TaskStatus] {

    /**
      * Wait for the completion of a task
      * By default it waits `baseDelay` and multiple it by 2 each time until it's greater than `maxDelay`:
      *  100     =  100ms
      *  100 * 2 =  200ms
      *  200 * 2 =  400ms
      *  400 * 2 =  800ms
      *  800 * 2 = 1600ms
      * 1600 * 2 = 3200ms
      * 3200 * 2 = 6400ms
      * etc...
      *
      */
    override def apply(client: AlgoliaClient, query: WaitForTaskDefinition)(
        implicit executor: ExecutionContext
    ): Future[TaskStatus] = {
      val baseDelay = query.baseDelay
      val cumulatedDelay = 0L
      val maxDelay = query.maxDelay

      def request(
          delay: Long,
          cumulatedDelay: Long
      ): Future[TaskStatus] =
        if (maxDelay < cumulatedDelay) {
          Future.failed(
            WaitForTimeoutException(
              s"Waiting for task `${query.taskId}` on index `${query.index.get}` timeout after ${cumulatedDelay}ms"
            )
          )
        } else {
          client
            .request[TaskStatus](query.build())
            .flatMap { res =>
              if (res.status == "published") Future.successful(res)
              else {
                Thread.sleep(delay)
                request(delay * 2, cumulatedDelay + delay)
              }
            }
        }

      request(baseDelay, cumulatedDelay)
    }

  }

}
