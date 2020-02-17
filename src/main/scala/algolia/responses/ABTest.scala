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

package algolia.responses

import java.time.LocalDateTime

import algolia.objects.Query

case class ABTestsResponse(abtests: Seq[ABTestResponse], count: Int, total: Int)

case class ABTestResponse(
    abTestID: Int,
    clickSignificance: Option[Int],
    conversionSignificance: Option[Float],
    createdAt: LocalDateTime,
    endAt: LocalDateTime,
    name: String,
    status: String,
    variants: Seq[VariantResponse]
)

case class VariantResponse(
    averageClickPosition: Option[Int],
    clickCount: Option[Int],
    clickThroughRate: Option[Float],
    conversionCount: Option[Int],
    conversionRate: Option[Float],
    description: String,
    index: String,
    noResultCount: Option[Int],
    searchCount: Option[Int],
    trafficPercentage: Int,
    userCount: Option[Int],
    customSearchParameters: Option[Query]
)
