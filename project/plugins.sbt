//Coverage
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.0")
addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.1.0")

//Release
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "1.1")
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.0.1")

//Auto MIT Licence Header
addSbtPlugin("de.heikoseeberger" % "sbt-header" % "2.0.0")

//Auto version
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.7.0")
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")

//Formatter
addSbtPlugin("com.geirsson" % "sbt-scalafmt" % "0.6.8")
