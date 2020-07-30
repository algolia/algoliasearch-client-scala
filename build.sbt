ThisBuild / organization := "com.algolia"
ThisBuild / name := "algoliasearch-client-scala"
ThisBuild / description := "Scala client for Algolia Search API"
ThisBuild / version := "2.0.0-alpha-1"

lazy val commonSettings = Seq(
  scalaVersion := "2.13.1",
  crossScalaVersions := Seq("2.11.12", "2.12.10", "2.13.1"),
  libraryDependencies ++= Seq(
    "org.json4s" %% "json4s-native" % "3.6.7",
    "org.slf4j" % "slf4j-api" % "2.0.0-alpha1",
    "org.scalatest" %% "scalatest" % "3.1.1" % "test"
  ),
  scalacOptions ++= Seq(
    "-language:higherKinds",
    "-language:implicitConversions"
  )
)

lazy val root = (project in file("."))
  .aggregate(algoliasearchScalaCore, algoliasearchScalaApache)

lazy val algoliasearchScalaCore = (project in file("algoliasearch-scala-core"))
  .settings(
    commonSettings
  )

lazy val algoliasearchScalaApache = (project in file("algoliasearch-scala-apache"))
  .dependsOn(algoliasearchScalaCore % "compile->compile;test->test")
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
      "commons-io" % "commons-io" % "2.7",
      "org.apache.httpcomponents" % "httpasyncclient" % "4.1.4",
      "ch.qos.logback" % "logback-classic" % "1.3.0-alpha5" % "test",
      "ch.qos.logback" % "logback-core" % "1.3.0-alpha5" % "test"
    )
  )

// testOptions in Test += Tests.Argument("-P10")

//lazy val pomSettings = Seq(
//  publishMavenStyle := true,
//  publishArtifact in Test := false,
//  licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
//  homepage := Some(url("https://github.com/algolia/algoliasearch-client-scala/")),
//  scmInfo := Some(
//    ScmInfo(
//      url("https://github.com/algolia/algoliasearch-client-scala"),
//      "scm:git:git@github.com:com.algolia/algoliasearch-client-scala.git",
//    ),
//  ),
//  pomIncludeRepository := { _ => false },
//  developers +=
//    Developer(
//      "algolia",
//      "Algolia SAS",
//      "contact@com.algolia.com",
//      url("https://github.com/algolia/algoliasearch-client-scala/"),
//    ),
//)

//lazy val root = (project in file("."))
//  .enablePlugins(BuildInfoPlugin)
//  .settings(
//    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
//    buildInfoPackage := "algolia",
//    evictionWarningOptions in update := EvictionWarningOptions.default
//      .withWarnTransitiveEvictions(false)
//      .withWarnDirectEvictions(false)
//      .withWarnScalaVersionEviction(false)
//      .withWarnEvictionSummary(false),
//  )
//
//lazy val core = (project in file("algoliasearch-scala-core"))
//  .settings(name := "algoliasearch-scala-core")
//
//// Project dependencies
//
//// Testing dependencies
//libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % "test"
//
//scalacOptions ++= Seq(
//  // This flag lets us use higher-kinded types such as F[_] in type parameters
//  "-language:higherKinds",
//  "-feature",
//)
