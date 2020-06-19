package com.algolia.cts

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import com.algolia.search.ObjectID

object Utils {

  case class Record(objectID: String) extends ObjectID

  def appId1: String = sys.env.getOrElse("ALGOLIA_APPLICATION_ID_1", "")
  def adminKey1: String = sys.env.getOrElse("ALGOLIA_ADMIN_KEY_1", "")
  def searchKey1: String = sys.env.getOrElse("ALGOLIA_SEARCH_KEY_1", "")

  def appId2: String = sys.env.getOrElse("ALGOLIA_APPLICATION_ID_2", "")
  def adminKey2: String = sys.env.getOrElse("ALGOLIA_ADMIN_KEY_2", "")

  def appIdMcm: String = sys.env.getOrElse("ALGOLIA_APPLICATION_ID_MCM", "")
  def adminKeyMcm: String = sys.env.getOrElse("ALGOLIA_ADMIN_KEY_MCM", "")

  def generateTestingIndexName(testName: String): String = {
    val scalaVersion = util.Properties.versionNumberString

    val now = LocalDateTime.now()
    val date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(now)
    val time = DateTimeFormatter.ofPattern("HH:mm:ss").format(now)

    val user = System.getProperty("user.name")
    val instance = sys.env.getOrElse("CIRCLE_BUILD_NUM", user)

    s"scala_${scalaVersion}_${date}_${time}_${instance}_${testName}"
  }
}
