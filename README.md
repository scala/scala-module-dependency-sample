# Depend on Scala modules like a pro

A sample project which demonstrates recommended configuration for sbt projects that depend on newly spawned scala modules:

  * scala-xml for all classes that live in `scala.xml` package
  * scala-parser-combinators for all classes that live in `scala.util.parsing` package

This sample demonstrates how to conditionally depend on scala-xml module. If you need to depend on scala-parser-combinators just edit the code snippet accordingly. If you are just looking for copy&paste snippet for your `build.sbt` file, here it is:

```scala
// add scala-xml dependency when needed (for Scala 2.11 and newer) in a robust way
// this mechanism supports cross-version publishing
libraryDependencies := {
  CrossVersion.partialVersion(scalaVersion.value) match {
    // if scala 2.11+ is used, add dependency on scala-xml module
    case Some((_, scalaMajor)) =>
      if (scalaMajor >= 11)
        libraryDependencies.value :+ "org.scala-lang.modules" %% "scala-xml" % "1.0.0"
      else
        libraryDependencies.value
    // ooops, we failed to parse scala version properly
    case None =>
      // fails to compile with: error: A setting cannot depend on a task
      //streams.value.log.warn(s"Couldn't parse scala version: ${scalaVersion.value}. The dependency on xml module hasn't been added.")
      libraryDependencies.value
  }
}
```
