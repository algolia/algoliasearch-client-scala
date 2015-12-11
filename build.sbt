organization := "com.algolia"

name := "scala-client"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

coverageHighlighting := false //setting to true crashes the coverage

coverageEnabled := true

val dispatchVersion = "0.11.3"
val json4sVersion = "3.2.11"

val scalaTestVersion = "2.2.4"
val scalaMockVersion = "3.2"

libraryDependencies += "net.databinder.dispatch" %% "dispatch-core" % dispatchVersion
libraryDependencies += "net.databinder.dispatch" %% "dispatch-json4s-native" % dispatchVersion

libraryDependencies += "org.json4s" %% "json4s-ast" % json4sVersion
libraryDependencies += "org.json4s" %% "json4s-core" % json4sVersion
libraryDependencies += "org.json4s" %% "json4s-native" % json4sVersion

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3"

//Testing
libraryDependencies += "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % scalaMockVersion % "test"


/** Publishing to Sonatype **/

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <name>Algolia Search Client</name>
  <description>Scala client for Algolia Search API</description>
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
)

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

