organization := "com.algolia"
name := "algoliasearch-scala"
description := "Scala client for Algolia Search API"
version := "1.35.1-beta-1"
crossScalaVersions := Seq("2.11.12", "2.12.10", "2.13.1")
scalaVersion := "2.13.1"
coverageEnabled := false
testOptions in Test += Tests.Argument("-P10")
publishMavenStyle := true
publishArtifact in Test := false
licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
homepage := Some(url("https://github.com/algolia/algoliasearch-client-scala/"))
scmInfo := Some(
  ScmInfo(url("https://github.com/algolia/algoliasearch-client-scala"),
          "scm:git:git@github.com:algolia/algoliasearch-client-scala.git"))
pomIncludeRepository := { _ =>
  false
}
developers += Developer("algolia",
                        "Algolia SAS",
                        "contact@algolia.com",
                        url("https://github.com/algolia/algoliasearch-client-scala/"))

lazy val root = project
  .in(file("."))
  .enablePlugins(AutomateHeaderPlugin, BuildInfoPlugin)
  .settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "algolia"
  )

// Project dependencies
val asyncHttpClientVersion = "2.10.4"
val json4sVersion = "3.6.7"
val slf4jVersion = "1.7.30"
val scalaUriVersion = "1.4.10"
val scalaCollectionCompat = "2.1.3"
libraryDependencies += "org.asynchttpclient" % "async-http-client" % asyncHttpClientVersion
libraryDependencies += "org.json4s" %% "json4s-ast" % json4sVersion
libraryDependencies += "org.json4s" %% "json4s-core" % json4sVersion
libraryDependencies += "org.json4s" %% "json4s-native" % json4sVersion
libraryDependencies += "org.slf4j" % "slf4j-api" % slf4jVersion
libraryDependencies += "io.lemonlabs" %% "scala-uri" % scalaUriVersion
libraryDependencies += "org.scala-lang.modules" %% "scala-collection-compat" % scalaCollectionCompat

// Testing dependencies
val scalaTestVersion = "3.1.0"
val scalacheckVersion = "1.14.3"
val scalaMockVersion = "4.4.0"
val logbackVersion = "1.2.3"
libraryDependencies += "org.scalatest" %% "scalatest" % scalaTestVersion % Test
libraryDependencies += "org.scalacheck" %% "scalacheck" % scalacheckVersion % Test
libraryDependencies += "org.scalamock" %% "scalamock" % scalaMockVersion % Test
libraryDependencies += "ch.qos.logback" % "logback-classic" % logbackVersion % Test

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
  ))
