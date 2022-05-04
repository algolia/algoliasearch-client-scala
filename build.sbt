organization := "com.algolia"
name := "algoliasearch-scala"
description := "Scala client for Algolia Search API"
crossScalaVersions := Seq("2.11.12", "2.12.10", "2.13.8")
scalaVersion := "2.13.8"
Test / testOptions += Tests.Argument("-P10")
publishMavenStyle := true
Test / publishArtifact := false
licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
homepage := Some(url("https://github.com/algolia/algoliasearch-client-scala/"))
scmInfo := Some(
  ScmInfo(
    url("https://github.com/algolia/algoliasearch-client-scala"),
    "scm:git:git@github.com:algolia/algoliasearch-client-scala.git"
  )
)
pomIncludeRepository := { _ =>
  false
}
developers += Developer(
  "algolia",
  "Algolia SAS",
  "contact@algolia.com",
  url("https://github.com/algolia/algoliasearch-client-scala/")
)
publishTo := sonatypePublishToBundle.value
pgpSigningKey := Credentials
  .forHost(credentials.value, "pgp")
  .map(_.userName) // related to https://github.com/sbt/sbt-pgp/issues/170

lazy val root = project
  .in(file("."))
  .enablePlugins(AutomateHeaderPlugin, BuildInfoPlugin)
  .settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "algolia",
    update / evictionWarningOptions := EvictionWarningOptions.default
      .withWarnTransitiveEvictions(false)
      .withWarnDirectEvictions(false)
      .withWarnScalaVersionEviction(false)
      .withWarnEvictionSummary(false)
  )

// Project dependencies
libraryDependencies += "org.asynchttpclient" % "async-http-client" % "2.12.3"
libraryDependencies += "io.netty" % "netty-resolver-dns" % "4.1.45.Final"
libraryDependencies += "org.json4s" %% "json4s-ast" % "4.0.5"
libraryDependencies += "org.json4s" %% "json4s-core" % "4.0.5"
libraryDependencies += "org.json4s" %% "json4s-native" % "4.0.5"
libraryDependencies += "org.scala-lang.modules" %% "scala-collection-compat" % "2.7.0"
libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.36"

// Related to https://snyk.io/vuln/SNYK-JAVA-IONETTY-1584063,
// To be removed when 'async-http-client' updates dependency version of 'netty-codec'
libraryDependencies += "io.netty" % "netty-codec" % "4.1.68.Final"

// Testing dependencies
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.11" % Test
//libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.3" % Test
libraryDependencies += "org.scalamock" %% "scalamock" % "5.2.0" % Test
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % Test

scalacOptions ++= Seq(
  "-deprecation",
  "-Xfatal-warnings",
  "-feature", //Emit warning and location for usages of features that should be imported explicitly.
  "-encoding",
  "UTF-8",
  "-unchecked", //Enable additional warnings where generated code depends on assumptions
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-deprecation:false"
)

headerLicense := Some(
  HeaderLicense.Custom(
    """The MIT License (MIT)
      |
      |Copyright (c) 2016 Algolia
      |http://www.algolia.com/
      |
      |Permission is hereby granted, free of charge, to any person obtaining a copy
      |of this software and associated documentation files (the "Software"), to deal
      |in the Software without restriction, including without limitation the rights
      |to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
      |copies of the Software, and to permit persons to whom the Software is
      |furnished to do so, subject to the following conditions:
      |
      |The above copyright notice and this permission notice shall be included in
      |all copies or substantial portions of the Software.
      |
      |THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
      |IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
      |FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
      |AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
      |LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
      |OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
      |THE SOFTWARE.
      |""".stripMargin
  )
)
