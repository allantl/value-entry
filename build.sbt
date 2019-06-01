import ReleaseTransformations._

name := "value-entry"
organization in ThisBuild := "com.github.allantl"
homepage in ThisBuild := Some(url("https://github.com/allantl/jira4s"))
licenses in ThisBuild := List("MIT" -> url("https://opensource.org/licenses/MIT"))

val scala211Version = "2.11.12"
val scala212Version = "2.12.8"

scalaVersion := scala212Version
crossScalaVersions := Seq(scala212Version, scala211Version)

lazy val publishSettings = Seq(
  releasePublishArtifactsAction := PgpKeys.publishSigned.value,
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := { _ =>
    false
  },
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
    else Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },
  autoAPIMappings := true,
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/allantl/value-entry"),
      "scm:git:git@github.com:allantl/value-entry.git"
    )
  ),
  developers := List(
    Developer(
      "allantl",
      "Allan Timothy Leong",
      "allan.timothy.leong@gmail.com",
      url("https://github.com/allantl")
    )
  )
)

releaseCrossBuild := true
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommandAndRemaining("+publishSigned"),
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeReleaseAll"),
  pushChanges
)

lazy val root = (project in file("."))
  .settings(
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.7" % "test"
    )
  )
  .settings(publishSettings)
