# Depend on Scala modules like a pro

Two sample projects (Sbt and Maven) which demonstrate recommended configuration for projects that depend on newly spawned scala modules:

  * scala-xml for all classes that live in `scala.xml` package
  * scala-parser-combinators for all classes that live in `scala.util.parsing` package

## Sbt sample

This sample demonstrates how to conditionally depend on scala-xml module. If you need to depend on scala-parser-combinators just edit the code snippet accordingly. If you are just looking for copy&paste snippet for your `build.sbt` file, here it is:

```scala
// add scala-xml dependency when needed (for Scala 2.11 and newer) in a robust way
// this mechanism supports cross-version publishing
libraryDependencies := {
  CrossVersion.partialVersion(scalaVersion.value) match {
    // if scala 2.11+ is used, add dependency on scala-xml module
    case Some((2, scalaMajor)) if scalaMajor >= 11 =>
        libraryDependencies.value :+ "org.scala-lang.modules" %% "scala-xml" % "1.0.0"
    case _ =>
      libraryDependencies.value
  }
}
```
## Maven sample

This to depend on scala-xml module with assumption that you have `scalaVersion` property defined in your pom.xml file. If you need to depend on scala-parser-combinators just edit the code snippet accordingly. If you are just looking for copy&paste snippet for your `pom.xml` file, here it is:

```xml
<dependency>
  <groupId>org.scala-lang.modules</groupId>
  <artifactId>scala-xml_${scalaVersion}</artifactId>
  <version>1.0.0</version>
</dependency>
```

*NOTE*: Due to an [issue](https://issues.scala-lang.org/browse/SI-8358) in Scala compiler, your project that depends on scala-xml will compile with Scala 2.11 even if you do not declare the dependency on `scala-xml` module. However, it will fail at runtime due to missing dependency. In order to prevent that mistake we offer a workaround. Add `-nobootcp` Scala compiler option which will make scala-xml invisible to compilation classpath and your code will fail to compile when the dependency on `scala-xml` is missing. Check sample pom.xml for details.
