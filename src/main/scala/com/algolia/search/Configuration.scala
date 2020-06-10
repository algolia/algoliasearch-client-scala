package com.algolia.search

import com.algolia.transport.{Compression, NoCompression}

import scala.concurrent.duration._

case class Configuration(
    appID: String,
    apiKey: String,
    hosts: Seq[String] = Seq.empty,
    batchSize: Int = 1000,
    connectTimeout: Duration = 2.second,
    readTimeout: Duration = 5.second,
    writeTimeout: Duration = 30.second,
    headers: Map[String, String] = Map.empty,
    compression: Compression = NoCompression,
)
