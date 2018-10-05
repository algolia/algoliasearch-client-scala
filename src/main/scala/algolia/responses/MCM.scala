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

case class UserData(userID: String, nbRecords: Int, dataSize: Int)

case class UserDataWithCluster(userID: String, clusterName: String, nbRecords: Int, dataSize: Int)

case class TopUserID(topUsers: Map[String, UserData])

case class ClusterData(clusterName: String, nbRecords: Int, nbUserIDs: Int, dataSize: Int)

case class ClusterList(clusters: Seq[ClusterData])

case class UserIDList(userIDs: Seq[UserDataWithCluster], page: Int, hitsPerPage: Int)

case class UserIDHit(userID: String,
                     clusterName: String,
                     nbRecords: Int,
                     dataSize: Int,
                     objectID: String,
                     _highlightResult: Map[String, HighlightResult])

// TODO Use timestamp
case class SearchUserID(hits: Seq[UserIDHit],
                        nbHits: Int,
                        page: Int,
                        hitsPerPage: Int,
                        updatedAt: Int)
