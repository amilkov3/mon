import ReleaseTransformations._

organization in ThisBuild := "ml.milkov"

coverageEnabled in ThisBuild := true

lazy val root = project.in(file("."))
  .settings(noPublishSettings: _*)
  .aggregate(cloudwatch, core)

lazy val core = project.in(file("core"))
  .settings(releasePublishSettings: _*)
  .settings(name := "mon-core")
  .settings(
    libraryDependencies ++= commonDeps
  )

lazy val cloudwatch = project.in(file("cloudwatch"))
  .settings(releasePublishSettings: _*)
  .settings(name := "mon-cloudwatch")
  .settings(
    libraryDependencies ++= commonDeps,
    libraryDependencies ++= Seq(
      "com.amazonaws.scala" % "aws-scala-sdk-cloudwatch" % "1.10.7"
    )
  )
  .configs(IntegrationTest.extend(Test))
  .settings(Defaults.itSettings: _*)
  .dependsOn(core % "compile->compile;test->test")

lazy val commonDeps = Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",
  "io.estatico" %% "newtype" % "0.4.0",
  "org.typelevel" %% "cats-core" % "1.1.0",
  "org.typelevel" %% "cats-effect" % "0.10",
  "org.typelevel" %% "mouse" % "0.17",

  "org.scalacheck" %% "scalacheck" % "1.14.0" % "test",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

scalacOptions in Global ++= Seq(
  "-Xfatal-warnings",
  "-unchecked",
  "-feature",
  "-deprecation",
  "-language:higherKinds",
  "-language:implicitConversions"
)

lazy val noPublishSettings = Seq(
  publish := (),
  publishLocal := (),
  publishArtifact := false
)

lazy val releasePublishSettings = Seq(
  releaseCrossBuild := true,
  releasePublishArtifactsAction := PgpKeys.publishSigned.value,
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    publishArtifacts,
    setNextVersion,
    commitNextVersion,
    ReleaseStep(action = Command.process("sonatypeReleaseAll", _)),
    pushChanges
  ),
  homepage := Some(url("https://github.com/amilkov3/mon")),
  licenses := Seq("Apache 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },
  sonatypeProfileName := "ml.milkov",
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  },
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/amilkov3/mon"),
      "https://github.com/amilkov3/mon.git"
    )
  ),
  developers := List(
    Developer("amilkov3",  "Alex Milkov", "amilkov3@gmail.com", url("http://milkov.ml"))
  )
)

