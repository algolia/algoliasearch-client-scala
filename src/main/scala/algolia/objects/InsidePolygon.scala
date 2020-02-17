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

case class InsidePolygon(
    p1Lat: String,
    p1Lng: String,
    p2Lat: String,
    p2Lng: String,
    p3Lat: String,
    p3Lng: String
) {

  override def toString = s"[$p1Lat,$p1Lng,$p2Lat,$p2Lng,$p3Lat,$p3Lng]"

}

object InsidePolygon {

  def apply(
      p1Lat: Float,
      p1Lng: Float,
      p2Lat: Float,
      p2Lng: Float,
      p3Lat: Float,
      p3Lng: Float
  ): InsidePolygon = {
    InsidePolygon(
      p1Lat.toString,
      p1Lng.toString,
      p2Lat.toString,
      p2Lng.toString,
      p3Lat.toString,
      p3Lng.toString
    )
  }

}
