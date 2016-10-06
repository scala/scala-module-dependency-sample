name := "scala-sbt-cross-compile"

organization := "sample"

version := "1.0"

crossScalaVersions := Seq("2.12.0-RC1", "2.11.8", "2.10.6")

scalaVersion := "2.12.0-RC1"

// add scala-xml dependency when needed (for Scala 2.11 and newer) in a robust way
// this mechanism supports cross-version publishing
libraryDependencies := {
  CrossVersion.partialVersion(scalaVersion.value) match {
    // if scala 2.11+ is used, add dependency on scala-xml module
    case Some((2, scalaMajor)) if scalaMajor >= 11 =>
      libraryDependencies.value ++ Seq(
        "org.scala-lang.modules" %% "scala-xml" % "1.0.6",
        "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4",
        "org.scala-lang.modules" %% "scala-swing" % "2.0.0-M2")
    case _ =>
      // or just libraryDependencies.value if you don't depend on scala-swing
      libraryDependencies.value :+ "org.scala-lang" % "scala-swing" % scalaVersion.value
  }
}
