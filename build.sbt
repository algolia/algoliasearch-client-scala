organization := "com.algolia"

name := "algoliasearch-scala"

description := "Scala client for Algolia Search API"

version := "1.32.0"

crossScalaVersions := Seq("2.11.11", "2.12.2")

scalaVersion := "2.12.2"

coverageEnabled := false

val asyncHttpClientVersion = "2.8.1"
val json4sVersion = "3.5.2"
val slf4jVersion = "1.7.25"
val scalaUriVersion = "0.4.16"

val logbackVersion = "1.2.3"
val scalaTestVersion = "3.0.1"
val scalaMockVersion = "3.4.2"
val scalacheckVersion = "1.12.6"

libraryDependencies += "org.asynchttpclient" % "async-http-client" % asyncHttpClientVersion

libraryDependencies += "org.json4s" %% "json4s-ast" % json4sVersion
libraryDependencies += "org.json4s" %% "json4s-core" % json4sVersion
libraryDependencies += "org.json4s" %% "json4s-native" % json4sVersion

libraryDependencies += "org.slf4j" % "slf4j-api" % slf4jVersion

libraryDependencies += "io.lemonlabs" %% "scala-uri" % scalaUriVersion

//Testing
libraryDependencies += "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
libraryDependencies += "org.scalacheck" %% "scalacheck" % scalacheckVersion % "test"
libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % scalaMockVersion % "test"
libraryDependencies += "ch.qos.logback" % "logback-classic" % logbackVersion % "test"

scalacOptions ++= Seq(
  "-deprecation",
  "-Xfatal-warnings",
  "-feature", //Emit warning and location for usages of features that should be imported explicitly.
  "-encoding",
  "UTF-8",
  "-unchecked", //Enable additional warnings where generated code depends on assumptions
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Xfuture"
)

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

lazy val myProject = project
  .in(file("."))
  .enablePlugins(AutomateHeaderPlugin, BuildInfoPlugin)
  .settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
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
  if (isSnapshot.value) {
    Some(Opts.resolver.sonatypeSnapshots)
  } else {
    Some(Opts.resolver.sonatypeStaging)
  }
}

addCommandAlias("formatAll", ";scalafmt")

addCommandAlias("checkAll", ";compile;test:compile;formatAll")
