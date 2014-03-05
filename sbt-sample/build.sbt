name := "scala-sbt-cross-compile"

organization := "sample"

version := "1.0"

crossScalaVersions := Seq("2.11.0-RC1", "2.10.3")

scalaVersion := "2.11.0-RC1"

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
