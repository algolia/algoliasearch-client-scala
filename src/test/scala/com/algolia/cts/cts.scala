//package com.algolia.cts
//
//import java.time.LocalDateTime
//import java.time.format.DateTimeFormatter
//
//import com.algolia.search.{Client, Index}
//
//object cts {
//
//  val client1: Client = Client(
//    sys.env.getOrElse("ALGOLIA_APPLICATION_ID_1", ""),
//    sys.env.getOrElse("ALGOLIA_ADMIN_KEY_1", ""),
//  )
//
//  val client2: Client = Client(
//    sys.env.getOrElse("ALGOLIA_APPLICATION_ID_2", ""),
//    sys.env.getOrElse("ALGOLIA_ADMIN_KEY_2", ""),
//  )
//
//  val clientMcm: Client = Client(
//    sys.env.getOrElse("ALGOLIA_APPLICATION_ID_MCM", ""),
//    sys.env.getOrElse("ALGOLIA_ADMIN_KEY_MCM", ""),
//  )
//
//  def initIndex1[T](testName: String): Index[T] = {
//    val indexName = generateTestingIndexName(testName)
//    client1.initIndex(indexName)
//  }
//
//  def generateTestingIndexName(testName: String): String = {
//    val scalaVersion = util.Properties.versionNumberString
//
//    val now = LocalDateTime.now()
//    val date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(now)
//    val time = DateTimeFormatter.ofPattern("HH:mm:ss").format(now)
//
//    val user = System.getProperty("user.name")
//    val instance = sys.env.getOrElse("CIRCLE_BUILD_NUM", user)
//
//    s"scala_${scalaVersion}_${date}_${time}_${instance}_${testName}"
//  }
//}
