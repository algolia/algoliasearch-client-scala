/*
 * Copyright (c) 2015 Algolia
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

package algolia.inputs

case class BatchOperations(requests: Seq[BatchOperation[_ <: AnyRef]])

sealed trait BatchOperation[T <: AnyRef] {

  val action: String

}


case class AddObjectOperation[T <: AnyRef](body: T,
                                           indexName: Option[String] = None,
                                           action: String = "addObject") extends BatchOperation[T]

case class UpdateObjectOperation[T <: AnyRef](body: T,
                                              indexName: Option[String] = None,
                                              action: String = "updateObject") extends BatchOperation[T]

//case class PartialUpdateObjectOperation[T <: AnyRef](body: T, override val indexName: Option[String] = None, action: String = "partialUpdateObject") extends BatchOperation[T]

//case class PartialUpdateObjectNoCreateOperation[T <: AnyRef](body: T, override val indexName: Option[String] = None, action: String = "partialUpdateObjectNoCreate") extends BatchOperation[T]

case class DeleteObjectOperation[T <: AnyRef](indexName: String,
                                              objectID: String,
                                              action: String = "deleteObject") extends BatchOperation[T]

case class ClearIndexOperation[T <: AnyRef](indexName: String,
                                            action: String = "clear") extends BatchOperation[T]
