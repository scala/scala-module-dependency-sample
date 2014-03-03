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
