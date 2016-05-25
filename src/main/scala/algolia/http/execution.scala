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

package algolia.http

import java.util.{concurrent => juc}

import org.asynchttpclient.DefaultAsyncHttpClientConfig.Builder
import org.asynchttpclient._

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.Try

/** Http executor with defaults */
case class Http(client: AsyncHttpClient) extends HttpExecutor

object Http {

  def configure(withBuilder: Builder => Builder) =
    Http(client = new DefaultAsyncHttpClient(withBuilder(new DefaultAsyncHttpClientConfig.Builder()).build))

}

//object InternalDefaults {
//  def client = new DefaultAsyncHttpClient(config)
//
//  /** Sets a user agent, no timeout for requests  */
//  private object BasicDefaults {
//    lazy val timer = new HashedWheelTimer()
//
//  }
//
//}


trait HttpExecutor {
  self =>
  def client: AsyncHttpClient

  def apply[T](pair: (Request, AsyncHandler[T]))(implicit executor: ExecutionContext): Future[T] =
    apply(pair._1, pair._2)

  def apply[T](request: Request, handler: AsyncHandler[T])
              (implicit executor: ExecutionContext): Future[T] = {
    val lfut = client.executeRequest(request, handler)
    val promise = Promise[T]()
    val runnable = new java.lang.Runnable {
      def run() {
        promise.complete(Try(lfut.get()))
      }
    }
    val exec = new juc.Executor {
      def execute(runnable: Runnable) {
        executor.execute(runnable)
      }
    }

    lfut.addListener(runnable, exec)
    promise.future
  }

}
