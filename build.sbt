organization := "com.algolia"
name := "algoliasearch-client-scala"
description := "Scala client for Algolia Search API"
version := "2.0.0-alpha-1"
crossScalaVersions := Seq("2.11.12", "2.12.10", "2.13.1")
scalaVersion := "2.13.1"
testOptions in Test += Tests.Argument("-P10")
publishMavenStyle := true
publishArtifact in Test := false
licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
homepage := Some(url("https://github.com/algolia/algoliasearch-client-scala/"))
scmInfo := Some(
  ScmInfo(
    url("https://github.com/algolia/algoliasearch-client-scala"),
    "scm:git:git@github.com:com.algolia/algoliasearch-client-scala.git",
  ),
)
pomIncludeRepository := { _ => false }
developers += Developer(
  "algolia",
  "Algolia SAS",
  "contact@com.algolia.com",
  url("https://github.com/algolia/algoliasearch-client-scala/"),
)

lazy val root = project
  .in(file("."))
  .enablePlugins(BuildInfoPlugin)
  .settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "algolia",
    evictionWarningOptions in update := EvictionWarningOptions.default
      .withWarnTransitiveEvictions(false)
      .withWarnDirectEvictions(false)
      .withWarnScalaVersionEviction(false)
      .withWarnEvictionSummary(false),
  )

// Project dependencies
libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.12"
libraryDependencies += "org.apache.httpcomponents" % "httpasyncclient" % "4.1.4"
libraryDependencies += "org.json4s" %% "json4s-native" % "3.6.7"

// Testing dependencies
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % "test"

scalacOptions ++= Seq(
  // This flag lets us use higher-kinded types such as F[_] in type parameters
  "-language:higherKinds",
  "-feature",
)
