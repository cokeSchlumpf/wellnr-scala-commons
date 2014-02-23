package com.ibm.ipsuite

import sbt._
import Keys._

import com.typesafe.sbt._
import com.typesafe.sbteclipse.plugin.EclipsePlugin.{ EclipseKeys, EclipseCreateSrc }
import com.github.retronym.SbtOneJar

object projectSettings {
	
	lazy val buildSettings = Seq(
		organization 			:= "com.wellnr",
		version 				:= "1.0.0-RC3-SNAPSHOT",
		scalaVersion 			:= "2.10.3",
		logLevel 				:= Level.Info,
		exportJars				:= true,
		mainClass in Compile 	:= None)

	lazy val defaultSettings = Defaults.defaultSettings ++ buildSettings ++ eclipseSettings ++ scalariformSettings ++ onejarSettings ++ Seq(
		
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
			"-Ywarn-adapted-args"),

		javacOptions in Compile ++= Seq(
			"-source", "1.7",
			"-target", "1.7",
			"-Xlint:unchecked",
			"-Xlint:deprecation")) 

	lazy val eclipseSettings = Seq(
		EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource)
		
	lazy val playSettings = com.jamesward.play.BrowserNotifierPlugin.livereload ++ Seq(
	
		watchSources <++= baseDirectory map { path => ((path / "app" / "assets") ** "*.*").get })
	
	lazy val scalariformSettings = SbtScalariform.scalariformSettings ++ Seq(
		SbtScalariform.ScalariformKeys.preferences in Compile 	:= formattingPreferences,
		SbtScalariform.ScalariformKeys.preferences in Test		:= formattingPreferences)

	lazy val onejarSettings = SbtOneJar.oneJarSettings

	lazy val rootSettings = Defaults.defaultSettings ++ buildSettings ++ Seq(
		publishArtifact in Compile := false)
		
	lazy val publishSettings = Seq(
		publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository"))))

	/**
	 * Configure formatting preferences within this function...
	 */
	def formattingPreferences = {
		import scalariform.formatter.preferences._
		FormattingPreferences
	}
}
