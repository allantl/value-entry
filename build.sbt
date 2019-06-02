import ReleaseTransformations._
import sbt.Keys.{crossScalaVersions, licenses}
import sbt.file
import sbtcrossproject.CrossPlugin.autoImport.crossProject
import com.typesafe.sbt.pgp.PgpKeys.publishSigned

val scala211Version = "2.11.12"
val scala212Version = "2.12.8"

lazy val commonSettings = Seq(
  organization := "com.github.allantl",
  scalaVersion := scala212Version,
  crossScalaVersions := Seq(scala212Version, scala211Version)
)

lazy val noPublishSettings = {
  Seq(
    publish := {},
    publishLocal := {},
    publishSigned := {},
    publishArtifact := false
  )
}

lazy val releaseSettings = Seq(
  releaseCrossBuild := true,
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
  ),
  releasePublishArtifactsAction := PgpKeys.publishSigned.value,
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := { _ =>
    false
  },
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value) Some("snapshots".at(nexus + "content/repositories/snapshots"))
    else Some("releases".at(nexus + "service/local/staging/deploy/maven2"))
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
  ),
  homepage := Some(url("https://github.com/allantl/value-entry")),
  licenses := List("MIT" -> url("https://opensource.org/licenses/MIT"))
)

lazy val root = project.in(file("."))
  .aggregate(coreJS, coreJVM)
  .settings(noPublishSettings)
  .settings(commonSettings, releaseSettings)

lazy val core = crossProject(JSPlatform, JVMPlatform).in(file("core"))
  .settings(commonSettings, releaseSettings)
  .settings(
    name := "value-entry",
    libraryDependencies ++= Seq(
      "org.scalatest" %%% "scalatest" % "3.0.7" % "test"
    )
  )

lazy val coreJVM = core.jvm
lazy val coreJS = core.js