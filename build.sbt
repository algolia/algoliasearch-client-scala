name := "algolia-client-scala"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq("com.ning" % "async-http-client" % "1.9.31")

//http://dispatch.databinder.net/Dispatch.html
//http://www.flotsam.nl/dispatch-periodic-table.html
libraryDependencies ++= Seq("net.databinder.dispatch" %% "dispatch-core" % "0.11.3")
libraryDependencies ++= Seq("net.databinder.dispatch" %% "dispatch-json4s-native" % "0.11.2")

libraryDependencies ++= Seq("ch.qos.logback" % "logback-classic" % "1.1.3")

//libraryDependencies ++= Seq("org.json4s" %% "json4s-native" % "3.3.0")

//Testing
libraryDependencies ++= Seq("org.scalatest" %% "scalatest" % "2.2.4" % "test")
libraryDependencies ++= Seq("org.scalamock" %% "scalamock-scalatest-support" % "3.2" % "test")
