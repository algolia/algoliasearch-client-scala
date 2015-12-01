name := "algolia-client-scala"

version := "1.0"

scalaVersion := "2.11.7"

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

//http://www.scala-sbt.org/0.13/docs/Using-Sonatype.html