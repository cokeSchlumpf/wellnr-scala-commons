package com.ibm.ipsuite

import sbt._
import Keys._

import com.typesafe.sbt._
import com.typesafe.sbteclipse.plugin.EclipsePlugin.{ EclipseKeys, EclipseCreateSrc }
import com.github.retronym.SbtOneJar

object projectSettings {
	
	lazy val buildSettings = Seq(
		organization 			:= "com.wellnr",
		version 				:= "1.0.0-RC5",
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
		EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource)
		
	lazy val playSettings = com.jamesward.play.BrowserNotifierPlugin.livereload ++ scalaCompilerOptions ++ Seq(
	
		watchSources <++= baseDirectory map { path => ((path / "app" / "assets") ** "*.*").get })
	
	lazy val scalariformSettings = SbtScalariform.scalariformSettings ++ Seq(
		SbtScalariform.ScalariformKeys.preferences in Compile 	:= formattingPreferences,
		SbtScalariform.ScalariformKeys.preferences in Test		:= formattingPreferences)

	lazy val onejarSettings = SbtOneJar.oneJarSettings

	lazy val rootSettings = Defaults.defaultSettings ++ buildSettings ++ Seq(
		publishArtifact in Compile := false)
		
	//lazy val publishSettings = Seq(
	//	publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository"))))
	
	lazy val publishSettings = Seq(
		publishMavenStyle 	:= true,
		publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/Documents/Workspaces/mvn-repo/"))))
	
	/*	
	lazy val publishSettings = githubRepoSettings ++ Seq(
		credentials 		:= Seq(Credentials("My Maven Repo", "wellner.de", "13103f1329u6", "repo2013")),
		publishMavenStyle 	:= true,
		publishTo 			:= Some(Resolver.url("My Maven Repo", "wellnr.de", "/") as ("13103f1329u6", "repo2013")),
		localRepo 			:= Path.userHome / "Documents" / "Workspaces" / "github" / "repo"
		githubRepo 			:= "git@github.com:cokeSchlumpf/repo.git"
	)
	*/

	/**
	 * Configure formatting preferences within this function...
	 */
	def formattingPreferences = {
		import scalariform.formatter.preferences._
		FormattingPreferences
	}
}
