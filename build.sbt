organization := "com.algolia"

name := "algoliasearch-scala"

description := "Scala client for Algolia Search API"

version := "1.19.0-SNAPSHOT"

scalaVersion := "2.11.7"

//Wait fix in sbt-coveralls
ScoverageSbtPlugin.ScoverageKeys.coverageHighlighting := false //setting to true crashes the coverage
//coverageEnabled := true

val asyncHttpClientVersion = "2.0.4"

val json4sVersion = "3.2.11" //3.2.X is used by spark, so we keep this version

val scalaTestVersion = "2.2.6"
val scalaMockVersion = "3.2"
val scalacheckVersion = "1.12.1"

libraryDependencies += "org.asynchttpclient" % "async-http-client" % asyncHttpClientVersion

libraryDependencies += "org.json4s" %% "json4s-ast" % json4sVersion
libraryDependencies += "org.json4s" %% "json4s-core" % json4sVersion
libraryDependencies += "org.json4s" %% "json4s-native" % json4sVersion

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3"

libraryDependencies += "com.netaporter" %% "scala-uri" % "0.4.14"

//Testing
libraryDependencies += "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
libraryDependencies += "org.scalacheck" %% "scalacheck" % scalacheckVersion % "test"
libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % scalaMockVersion % "test"

scalacOptions ++= Seq("-feature", "-unchecked")

/**
  * Publishing to Sonatype & Maven Central
  *
  * > gem install github_changelog_generator
  * > github_changelog_generator -t YOUR_GITHUB_TOKEN --no-unreleased
  * > sbt publishSigned
  * > sbt sonatypeRelease
  *
  * see: http://www.scala-sbt.org/0.13/docs/Using-Sonatype.html
  * see: https://github.com/xerial/sbt-sonatype
  *
  **/
headers := Map(
  "scala" -> (
    de.heikoseeberger.sbtheader.HeaderPattern.cStyleBlockComment,
    """|/*
      | * The MIT License (MIT)
      | *
      | * Copyright (c) 2016 Algolia
      | * http://www.algolia.com/
      | *
      | * Permission is hereby granted, free of charge, to any person obtaining a copy
      | * of this software and associated documentation files (the "Software"), to deal
      | * in the Software without restriction, including without limitation the rights
      | * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
      | * copies of the Software, and to permit persons to whom the Software is
      | * furnished to do so, subject to the following conditions:
      | *
      | * The above copyright notice and this permission notice shall be included in
      | * all copies or substantial portions of the Software.
      | *
      | * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
      | * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
      | * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
      | * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
      | * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
      | * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
      | * THE SOFTWARE.
      | */
      |
      |""".stripMargin
  )
)

lazy val myProject = project
  .in(file("."))
  .enablePlugins(AutomateHeaderPlugin, BuildInfoPlugin)
  .settings(
    buildInfoKeys := Seq[BuildInfoKey](name,
                                       version,
                                       scalaVersion,
                                       sbtVersion),
    buildInfoPackage := "algolia"
  )

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ =>
  false
}

pomExtra := {
  <url>https://github.com/algolia/algoliasearch-client-scala</url>
  <licenses>
    <license>
      <name>The MIT License</name>
      <url>http://www.opensource.org/licenses/mit-license.php</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <connection>scm:git:git@github.com:algolia/algoliasearch-client-scala.git</connection>
    <url>scm:git:git@github.com:algolia/algoliasearch-client-scala.git</url>
    <developerConnection>scm:git:git@github.com:algolia/algoliasearch-client-scala.git</developerConnection>
    <tag>HEAD</tag>
  </scm>
  <developers>
    <developer>
      <id>algolia</id>
      <name>Algolia SAS</name>
      <email>contact@algolia.com</email>
      <url>https://github.com/algolia/algoliasearch-client-scala</url>
    </developer>
  </developers>
}

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (version.value.trim.endsWith("SNAPSHOT")) {
    Some("snapshots" at nexus + "content/repositories/snapshots")
  } else {
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
  }
}

addCommandAlias("formatAll", ";scalafmt;test:scalafmt")

addCommandAlias("checkAll", ";compile;test:compile;formatAll")
