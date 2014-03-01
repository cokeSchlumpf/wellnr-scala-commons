// list of available sbt plugins: http://www.scala-sbt.org/release/docs/Community/Community-Plugins.html

// Plugin Repositories
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases"

// sbt pluging for One-JAR https://github.com/sbt/sbt-onejar
addSbtPlugin("org.scala-sbt.plugins" % "sbt-onejar" % "0.8")

// plugin to create Eclipse projects https://github.com/typesafehub/sbteclipse
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.4.0")

// plugin which formats the scala code https://github.com/sbt/sbt-scalariform
addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform" % "1.2.1")

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.1")

// Auto refresh plugin ...
addSbtPlugin("com.jamesward" %% "play-auto-refresh" % "0.0.7")

// Plugin for Scala JS
addSbtPlugin("org.scala-lang.modules.scalajs" % "scalajs-sbt-plugin" % "0.3")
