name := "algolia-client-scala"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "com.ning" % "async-http-client" % "1.9.31"

libraryDependencies += "net.databinder.dispatch" %% "dispatch-core" % "0.11.3"
libraryDependencies += "net.databinder.dispatch" %% "dispatch-json4s-native" % "0.11.2"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3"

//Testing
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"
libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % "3.2" % "test"
