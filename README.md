wellnr-scala-commons
====================

This project contains two common libraries: `scala-commons` and `play-commons`. The `scala-commons` library only contains some general methods and base classes I'm using in several different projects. Just feel free to use it or copy some code for your projects. The play-commons project contains, as the name suggests, several common stuff for play-projects, which will be explained in more detail in the following sections.

In addition to the library projects there is a third project `play-commons-blog` and a related project `play-commons-blog-client` which can be seen as an example project which uses the `play-commons` library, whereas `play-commons-blog-client` is just an project to play arround with ScalaJS.

Dependency Management
---------------------

To use the libraries, add the repository to your build.sbt:

```
"wellnr" at "https://raw.github.com/cokeSchlumpf/mvn-repo/master"
```

Then you can add the libraries to your dependencies:

```
libraryDependencies ++= Seq(
  "com.wellnr" 	%% "play-commons" % "1.0.0-RC8" changing()
)
```

play-commons
============

The intention of the `play-commons` library is to abstract common use cases and patterns of many Play! applications. Since the library is build as modular as possible you are not restricted in your application architecture when using `play-commons`. But when you are going to use it as a framework your application is based on, you'll end up with an application architecture as illustrated in the following model:

WE NEED A NICE PICTURE HERE!


