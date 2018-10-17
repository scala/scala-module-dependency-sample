# Depend on Scala modules like a pro

This repository shows how to use these build tools:

  * sbt
  * Maven

to depend on Scala standard modules such as:

  * scala-xml, containing the `scala.xml` package
  * scala-parser-combinators, containing the `scala.util.parsing` package
  * scala-swing, containing the `scala.swing` package

These modules were split out from the Scala standard library, beginning with Scala 2.11.

## Sbt sample

This sample demonstrates how to conditionally depend on all modules. If use only on some of the modules just edit the `libraryDependencies` definition accordingly. If you are just looking for a copy&paste snippet for your `build.sbt` file, here it is:

```scala
// add dependencies on standard Scala modules, in a way
// supporting cross-version publishing
// taken from: http://github.com/scala/scala-module-dependency-sample
libraryDependencies := {
  CrossVersion.partialVersion(scalaVersion.value) match {
    // if Scala 2.12+ is used, use scala-swing 2.x
    case Some((2, scalaMajor)) if scalaMajor >= 12 =>
      libraryDependencies.value ++ Seq(
        "org.scala-lang.modules" %% "scala-xml" % "1.1.1",
        "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.1",
        "org.scala-lang.modules" %% "scala-swing" % "2.0.3")
    case Some((2, scalaMajor)) if scalaMajor >= 11 =>
      libraryDependencies.value ++ Seq(
        "org.scala-lang.modules" %% "scala-xml" % "1.1.1",
        "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.1",
        "org.scala-lang.modules" %% "scala-swing" % "1.0.2")
    case _ =>
      // or just libraryDependencies.value if you don't depend on scala-swing
      libraryDependencies.value :+ "org.scala-lang" % "scala-swing" % scalaVersion.value
  }
}
```

## Maven sample

The following `pom.xml` snippet assumes you define a `scala.compat.version` property in your pom.xml file for scala-maven-plugin 3.1.6 or later. For example, the `scala.compat.version` should be set to `2.11` for any Scala 2.11.x version.

```xml
<!-- taken from: http://github.com/scala/scala-module-dependency-sample -->
<dependency>
  <groupId>org.scala-lang.modules</groupId>
  <artifactId>scala-xml_${scala.compat.version}</artifactId>
  <version>1.1.1</version>
</dependency>
<dependency>
  <groupId>org.scala-lang.modules</groupId>
  <artifactId>scala-parser-combinators_${scala.compat.version}</artifactId>
  <version>1.1.1</version>
</dependency>
<dependency>
  <groupId>org.scala-lang.modules</groupId>
  <artifactId>scala-swing_${scala.compat.version}</artifactId>
  <version>2.0.3</version>
</dependency>
```

*NOTE*: Due to an [issue](https://issues.scala-lang.org/browse/SI-8358) in the Scala compiler, a project that uses scala-xml will compile successfully on Scala 2.11 even without an explicit dependency on the `scala-xml` module. However, it will fail at runtime due to missing dependency. In order to prevent that mistake we offer a workaround. Add `-nobootcp` Scala compiler option which will make scala-xml invisible to compilation classpath and your code will fail to compile when the dependency on `scala-xml` is missing. Check sample pom.xml for details.

### Scala cross-versioning with Maven

The snippet provided above allows you to declare dependencies on modules shipped against Scala 2.11. If you would like to
support building your project with both Scala 2.10, 2.11 and 2.12 at the same time you'll need to use [Maven profiles](http://maven.apache.org/guides/introduction/introduction-to-profiles.html). Check the `pom.xml` file in the sample project for details how to set up Maven profiles for supporting different Scala versions.
