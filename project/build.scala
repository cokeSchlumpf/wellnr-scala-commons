package com.ibm.ipsuite

import sbt._
import play.Project._
import Keys._
import play.Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys
import ScalaJSKeys._
import com.typesafe.sbt.packager.universal.UniversalKeys

object ScalaCommonsBuild extends Build with UniversalKeys {

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
  	.settings(commonsBlogSettings ++ Seq(
    compile in Compile <<= (compile in Compile) dependsOn (ScalaJSKeys.packageJS in (commonsBlogClient, Compile)),
    /* Use this line to make the optimized JS always available ...
     * We may add up to 1GB Memory to sbt launcher config, since optimizing eats a lot of memory ...
     *
     * compile in Compile <<= (compile in Compile) dependsOn (optimizeJS in (commonsBlogClient, Compile)),
     */
    dist <<= dist dependsOn (optimizeJS in (commonsBlogClient, Compile)),
    watchSources <++= (sourceDirectory in (commonsBlogClient, Compile)).map { path => (path ** "*.scala").get}
  ) ++ (
    // ask scalajs project to put its outputs in scalajsOutputDir
    Seq(packageExternalDepsJS, packageInternalDepsJS, packageExportedProductsJS, optimizeJS) map {
      packageJSKey =>
        crossTarget in (commonsBlogClient, Compile, packageJSKey) := scalajsOutputDir.value
    }
    ): _*)
    .settings(libraryDependencies ++= dependencies.wellnrBlogDependencies ++ List(play.Project.jdbc))
    .settings(Keys.fork in (Test) := false)

  lazy val commonsBlogClient = Project(
    id   = "play-commons-blog-client",
    base = file("play-commons-blog-client")
  ) settings (scalajsSettings: _*)
}
