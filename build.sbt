name := "algolia-client-scala"

version := "1.0"

scalaVersion := "2.11.7"

val raptureVersion = "2.0.0-M1"

libraryDependencies ++= Seq("com.propensive" %% "rapture-core" % raptureVersion)
libraryDependencies ++= Seq("com.propensive" %% "rapture-json" % raptureVersion)
libraryDependencies ++= Seq("com.propensive" %% "rapture-net" % raptureVersion)

//Testing
libraryDependencies ++= Seq("org.scalatest" %% "scalatest" % "2.2.4" % "test")
