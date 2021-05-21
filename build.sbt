organization := "com.algolia"
name := "algoliasearch-scala"
description := "Scala client for Algolia Search API"
version := "1.39.0"
crossScalaVersions := Seq("2.11.12", "2.12.10", "2.13.6")
scalaVersion := "2.13.6"
testOptions in Test += Tests.Argument("-P10")
publishMavenStyle := true
publishArtifact in Test := false
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

lazy val root = project
  .in(file("."))
  .enablePlugins(AutomateHeaderPlugin, BuildInfoPlugin)
  .settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "algolia",
    evictionWarningOptions in update := EvictionWarningOptions.default
      .withWarnTransitiveEvictions(false)
      .withWarnDirectEvictions(false)
      .withWarnScalaVersionEviction(false)
      .withWarnEvictionSummary(false)
  )

// Project dependencies
libraryDependencies += "org.asynchttpclient" % "async-http-client" % "2.10.5"
libraryDependencies += "io.netty" % "netty-resolver-dns" % "4.1.45.Final"
libraryDependencies += "org.json4s" %% "json4s-ast" % "3.6.7"
libraryDependencies += "org.json4s" %% "json4s-core" % "3.6.7"
libraryDependencies += "org.json4s" %% "json4s-native" % "3.6.7"
libraryDependencies += "org.scala-lang.modules" %% "scala-collection-compat" % "2.1.4"
libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.30"

// Testing dependencies
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3" % Test
//libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.3" % Test
libraryDependencies += "org.scalamock" %% "scalamock" % "4.4.0" % Test
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0" % Test

scalacOptions ++= Seq(
  "-deprecation",
  "-Xfatal-warnings",
  "-feature", //Emit warning and location for usages of features that should be imported explicitly.
  "-encoding",
  "UTF-8",
  "-unchecked", //Enable additional warnings where generated code depends on assumptions
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen"
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
