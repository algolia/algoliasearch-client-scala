//Coverage
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.0.2")
addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.0.0")

//Release
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "1.0")
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.0.0")

//Auto MIT Licence Header
addSbtPlugin("de.heikoseeberger" % "sbt-header" % "1.5.1")

//Auto version
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.5.0")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")

//Formatter
addSbtPlugin("com.geirsson" % "sbt-scalafmt" % "0.6.8")