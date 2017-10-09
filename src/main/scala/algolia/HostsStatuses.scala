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

package algolia

import java.util.concurrent.ConcurrentHashMap

import org.slf4j.{Logger, LoggerFactory}

case class HostsStatuses(configuration: AlgoliaClientConfiguration,
                         utils: AlgoliaUtils,
                         queryHosts: Seq[String],
                         indexingHosts: Seq[String]) {

  private[algolia] val hostStatuses: ConcurrentHashMap[String, HostStatus] =
    new ConcurrentHashMap[String, HostStatus](5)

  val logger: Logger = LoggerFactory.getLogger("algoliasearch")

  def markHostAsUp(host: String): Unit = {
    logger.debug("Marking {} as `up`", host)
    hostStatuses.put(host, HostStatus.up(utils.now()))
  }

  def markHostAsDown(host: String): Unit = {
    logger.debug("Marking {} as `down`", host)
    hostStatuses.put(host, HostStatus.down(utils.now()))
  }

  def indexingHostsThatAreUp(): Seq[String] = hostsThatAreUp(indexingHosts)

  def queryHostsThatAreUp(): Seq[String] = hostsThatAreUp(queryHosts)

  private def hostsThatAreUp(hosts: Seq[String]): Seq[String] = {
    val filteredHosts = hosts.filter(h => isUpOrCouldBeRetried(getHostStatus(h)))
    if (filteredHosts.isEmpty) {
      hosts
    } else {
      filteredHosts
    }
  }

  def isUpOrCouldBeRetried(hostStatus: HostStatus): Boolean =
    hostStatus.up || (utils
      .now() - hostStatus.updatedAt) >= configuration.hostDownTimeoutMs

  private def getHostStatus(host: String): HostStatus =
    hostStatuses.getOrDefault(host, HostStatus.up(utils.now()))
}

private case class HostStatus(up: Boolean, updatedAt: Long)

private object HostStatus {

  def up(now: Long) = HostStatus(up = true, now)

  def down(now: Long) = HostStatus(up = false, now)

}
