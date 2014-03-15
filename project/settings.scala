package com.ibm.ipsuite

import sbt._
import Keys._

import com.typesafe.sbt._
import com.typesafe.sbteclipse.plugin.EclipsePlugin.{ EclipseKeys, EclipseCreateSrc }
import com.github.retronym.SbtOneJar
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import scala.Some
import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys
import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._
import scala.Some
import play.Project._
import scala.Some

object projectSettings {
	
	lazy val buildSettings = Seq(
		organization 			:= "com.wellnr",
		version 				:= "0.1-SNAPSHOT",
		scalaVersion 			:= "2.10.3",
		logLevel 				:= Level.Info,
		exportJars				:= true,
		mainClass in Compile 	:= None)

  lazy val scalaCompilerOptions = Seq(

    scalacOptions in Compile ++= Seq(
      "-g:none",
      "-encoding", "UTF-8",
      "-target:jvm-1.7",
      "-deprecation",
      "-feature",
      "-unchecked",
      "-optimise",
      "-Ydead-code",
      "-Yinline",
      "-Yinline-handlers",
      "-Yinline-warnings",
      "-Ywarn-adapted-args"))

	lazy val defaultSettings = Defaults.defaultSettings ++ buildSettings ++ eclipseSettings ++ scalariformSettings ++ onejarSettings ++ scalaCompilerOptions ++ Seq(

		javacOptions in Compile ++= Seq(
			"-source", "1.7",
			"-target", "1.7",
			"-Xlint:unchecked",
			"-Xlint:deprecation"))

	lazy val eclipseSettings = Seq(
		EclipseKeys.createSrc := (EclipseCreateSrc.Default + EclipseCreateSrc.Resource))
		
	lazy val playSettings = com.jamesward.play.BrowserNotifierPlugin.livereload ++ scalaCompilerOptions ++ Seq(
	
		watchSources <++= baseDirectory map { path => ((path / "app" / "assets") ** "*.*").get })

  val scalajsOutputDir = Def.settingKey[File]("directory for javascript files output by scalajs")

  lazy val commonsBlogSettings =
    buildSettings ++ play.Project.playScalaSettings ++ publishSettings ++ Seq(
      scalajsOutputDir     := baseDirectory.value / "public" / "javascripts" / "scalajs",
      unmanagedSourceDirectories in Compile += baseDirectory.value / "shared"
    )
	
	lazy val scalariformSettings = SbtScalariform.scalariformSettings ++ Seq(
		SbtScalariform.ScalariformKeys.preferences in Compile 	:= formattingPreferences,
		SbtScalariform.ScalariformKeys.preferences in Test		:= formattingPreferences)

	lazy val onejarSettings = SbtOneJar.oneJarSettings

	lazy val rootSettings = Defaults.defaultSettings ++ buildSettings ++ Seq(
		publishArtifact in Compile := false)

  // TODO: Make mvn-repo path configurable via environment variable ...
	lazy val publishSettings = Seq(
		publishMavenStyle 	:= true,
		publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/Documents/Workspaces/mvn-repo/"))))

  lazy val scalajsSettings =
    scalaJSSettings ++ Seq(
      name := "scalajs-example",
      version := "0.1.0-SNAPSHOT",
      // Specify additional .js file to be passed to package-js and optimize-js
      unmanagedSources in (Compile, ScalaJSKeys.packageJS) += baseDirectory.value / "src" / "main" / "js" / "startup.js",
      unmanagedSourceDirectories in Compile += baseDirectory.value / ".." / "play-commons-blog" / "shared",
      libraryDependencies ++= Seq(
        "org.scala-lang.modules.scalajs" %% "scalajs-jasmine-test-framework" % scalaJSVersion % "test",
        "org.scala-lang.modules.scalajs" %% "scalajs-dom" % "0.3-SNAPSHOT"
      )
    )

	/**
	 * Configure formatting preferences within this function...
	 */
	def formattingPreferences = {
		import scalariform.formatter.preferences._
		FormattingPreferences
	}
}
