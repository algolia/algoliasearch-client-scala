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

package algolia.integration

import java.time.format.DateTimeFormatter
import java.time.{LocalDateTime, ZoneOffset, ZonedDateTime}
import algolia.AlgoliaDsl._
import algolia.inputs.{UserIDAssignment, UserIDsAssignment}
import algolia.responses.{ClusterData, UserDataWithCluster}
import algolia.{
  AlgoliaClient,
  AlgoliaClientConfiguration,
  AlgoliaClientException,
  AlgoliaHttpClient,
  AlgoliaTest,
  SkipInCI
}

import scala.annotation.tailrec
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps

class MCMIntegrationTest extends AlgoliaTest {

  lazy val mcmClient: AlgoliaClient =
    new AlgoliaClient(
      System.getenv("ALGOLIA_APPLICATION_ID_MCM"),
      System.getenv("ALGOLIA_ADMIN_KEY_MCM")
    ) {
      override val httpClient: AlgoliaHttpClient =
        AlgoliaHttpClient(
          AlgoliaClientConfiguration(100000, 100000, 100000, 100000, 100000)
        )
    }

  // As we use the same application ID for integration testing for all API clients, the
  // way we differentiate userIDs is to prepend a `LANG-client-` prefix to each userID.
  val date: String =
    DateTimeFormatter
      .ofPattern("yyyy-MM-dd")
      .format(ZonedDateTime.now(ZoneOffset.UTC))
  val userIDPrefix =
    s"scala-client-$date-${System.getProperty("user.name")}"

  def hasScalaUserIDPrefix(u: UserDataWithCluster): Boolean =
    u.userID.startsWith(userIDPrefix)

  it("should assign userID properly", SkipInCI) {
    // Make sure we have at least 2 clusters and retrieve the first one
    val listClusters = mcmClient.execute(list clusters)
    val cluster: ClusterData = whenReady(listClusters) { res =>
      res.clusters.size should be > 1
      res.clusters.head
    }

    // Delete any preexisting user
    var listUsersFuture = mcmClient.execute(list userIDs)
    val existingUserIDs =
      whenReady(listUsersFuture)(_.userIDs).filter(hasScalaUserIDPrefix)
    val removeExistingUsersFutures =
      existingUserIDs.map(id => mcmClient.execute(remove userID id.userID))
    Await
      .result(
        Future.sequence(removeExistingUsersFutures),
        10 seconds
      )
    Thread.sleep(3000)

    listUsersFuture = mcmClient.execute(list userIDs)
    whenReady(listUsersFuture)(_.userIDs).count(hasScalaUserIDPrefix) should be(
      0
    )

    // Assign one user to the first cluster and make sure it is assigned
    val travisBuildID = System.getenv("TRAVIS_BUILD_NUMBER")
    val userIDSuffix = "-" + LocalDateTime.now.toEpochSecond(ZoneOffset.UTC)
    val userID = userIDPrefix + (if (travisBuildID == null) "local"
                                 else travisBuildID) + userIDSuffix
    Await
      .result(
        mcmClient.execute(
          assign userID UserIDAssignment(userID, cluster.clusterName)
        ),
        10 seconds
      )

    Await
      .result(
        mcmClient.execute(
          assign userIDs UserIDsAssignment(
            Seq(userID + "-1", userID + "-2"),
            cluster.clusterName
          )
        ),
        10 seconds
      )

    waitUserID(mcmClient, userID)

    val hits = Await
      .result(
        mcmClient.execute(search userIDs userID cluster cluster.clusterName),
        10 seconds
      )
      .hits

    Seq(userID, userID + "-1", userID + "-2").foreach(u =>
      hits.exists(_.userID == u) should be(true)
    )

    removeUserId(mcmClient, userID)

    val mappingFuture = mcmClient execute has.pendingMappings()
    noException should be thrownBy whenReady(mappingFuture) { res =>
      res.pending should not be None
    }

  }

  @tailrec
  final def waitUserID(client: AlgoliaClient, id: String): Unit = {
    try Await.result(client.execute(get userID id), 10 seconds)
    catch {
      case e: AlgoliaClientException =>
        if (e.getMessage.contains("Mapping does not exist for this userID")) {
          Thread.sleep(1000)
          waitUserID(client, id)
        }
    }
  }

  @tailrec
  final def removeUserId(client: AlgoliaClient, id: String): Unit = {
    try Await.result(client.execute(remove userID id), 10 seconds)
    catch {
      case e: AlgoliaClientException =>
        if (e.getMessage.contains(
              "Another mapping operation is already running for this userID"
            )) {
          Thread.sleep(1000)
          removeUserId(client, id)
        }
    }
  }
}
