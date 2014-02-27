package com.ibm.ipsuite

import sbt._
import play.Project._
import Keys._

object ScalaCommonsBuild extends Build {

  import projectSettings._

  /**
   * Definition of 3rd party repositories. Should be removed when using Artifactory.
   */
  resolvers ++= dependencies.repositories

  lazy val main = Project("main", file("."))
    .aggregate(scalaCommons, playCommons, commonsBlog)
    .settings(rootSettings ++ publishSettings: _*)

  lazy val scalaCommons = Project("scala-commons", file("scala-commons"))
    .settings(defaultSettings ++ publishSettings: _*)
    .settings(libraryDependencies ++= dependencies.scalaCommonsDependencies)
    
  lazy val playCommons = play.Project("play-commons", path = file("play-commons"))
  	.dependsOn(scalaCommons)
  	.settings(buildSettings ++ playSettings ++ playScalaSettings ++ publishSettings: _*)
    .settings(libraryDependencies ++= dependencies.playCommonsDependencies)
    
  lazy val commonsBlog = play.Project("play-commons-blog", path = file("play-commons-blog"))
  	.dependsOn(playCommons)
  	.settings(buildSettings ++ playSettings ++ playScalaSettings  ++ publishSettings: _*)
    .settings(libraryDependencies ++= dependencies.wellnrBlogDependencies ++ List(jdbc))
    .settings(Keys.fork in (Test) := false)
    
}
