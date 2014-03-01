package com.ibm.ipsuite

import sbt._

/**
 * Definition of project dependencies.
 */
object dependencies {

  private def compile(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "compile")
  private def provided(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "provided")
  private def test(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")
  private def testDefault(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test->default")
  private def runtime(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "runtime")
  private def container(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "container")
  
  // format: OFF
  private lazy val bootstrap	  	  = "org.webjars" 				            			% 	"bootstrap" 		    	  % "3.0.1"
  private lazy val c10n				      = "c10n" 								                	% 	"c10n-core" 	    		  % "1.1"
  private lazy val commonslang3    	= "org.apache.commons" 			        			% 	"commons-lang3" 	  	  % "3.1"
  private lazy val config 			    = "com.typesafe" 					            		% 	"config" 				        % "1.0.2"
  private lazy val eclipselink 		  = "org.eclipse.persistence"	      				% 	"eclipselink" 			    % "2.5.1"
  private lazy val h2jdbc 			    = "com.h2database" 						          	% 	"h2" 				           	% "1.3.170"
  private lazy val knockout			    = "org.webjars" 							            % 	"knockout" 				      % "3.0.0"
  private lazy val markdown4j 		  = "org.commonjava.googlecode.markdown4j" 	% 	"markdown4j" 			      % "2.2-cj-1.0"
  private lazy val playSlick		    = "com.typesafe.play" 			        			%% 	"play-slick" 			      % "0.5.0.8"
  private lazy val scalareflect 	  = "org.scala-lang" 					            	% 	"scala-reflect" 		    % "2.10.3"
  private lazy val slf4jnop 		    = "org.slf4j" 							          	  % 	"slf4j-nop" 			      % "1.6.4"
  private lazy val slick 			      = "com.typesafe.slick" 			        			%% 	"slick" 				        % "1.0.1"
  private lazy val stackcontroller	= "jp.t2v" 							              		%% "stackable-controller" 	% "0.3.0"
  private lazy val twitter4j		    = "org.twitter4j"				          			  %	"twitter4j-core"		      % "3.0.5"
  private lazy val twittertext		  = "com.twitter"				            				% 	"twitter-text"			    % "1.6.1"
  private lazy val utillogging 		  = "com.twitter" 			            				%% 	"util-logging" 			    % "6.5.0"
  private lazy val webjarsplay		  = "org.webjars" 			            				%% 	"webjars-play" 			    % "2.2.0"
  // format: ON

  /**
   * Here we define our 3rd party repositories. We should remove this section, when we are using Artifactory.
   */
  lazy val repositories = List(
    "C10N" at "https://raw.github.com/rodionmoiseev/c10n/master/c10n-mvn-repo/releases/",
    "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
    "twitter4j" at "http://twitter4j.org/maven2")

  lazy val scalaCommonsDependencies = compile(
    c10n,
    commonslang3,
    config,
    eclipselink,
    h2jdbc,
    utillogging,
    scalareflect,
    slick)

  lazy val playCommonsDependencies = compile(
    bootstrap,
    knockout,
    playSlick,
    slick,
    stackcontroller,
    webjarsplay)
    
  lazy val playSampleDependencies = compile(
  	bootstrap)
  	
  lazy val wellnrBlogDependencies = compile(
  	markdown4j,
  	twitter4j,
  	twittertext)
}
