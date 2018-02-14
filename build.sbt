
name := "mon"

lazy val scalaV = "2.12.4"
lazy val scalaCheckVersion = "3.0.1"

scalaVersion in Global := scalaV

lazy val core = (project in file("core"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= commonDeps
  )
    /*.configs(Test)
    .settings(Defaults.testSettings: _*)*/


lazy val cloudwatch = (project in file("cloudwatch"))
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= commonDeps,
    libraryDependencies ++= Seq(
      "com.amazonaws.scala" % "aws-scala-sdk-cloudwatch" % "1.10.7"
    )
  )
  .configs(IntegrationTest.extend(Test))
  .settings(Defaults.itSettings: _*)
  /*.settings(Defaults.testSettings: _*)*/
  .dependsOn(core)

lazy val commonSettings = Seq(
  organization := "ml.milkov",
  scalaVersion := scalaV,
  version      := "0.1.0"
)

lazy val commonDeps = Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "io.estatico" %% "newtype" % "0.1.0",
  "org.typelevel" %% "cats-core" % "1.0.1",
  "org.typelevel" %% "cats-effect" % "0.8",
  "org.typelevel" %% "mouse" % "0.16",

  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

scalacOptions in Global ++= Seq(
  "-Xfatal-warnings",
  "-unchecked",
  "-feature",
  "-deprecation",
  "-language:higherKinds",
  "-language:implicitConversions"
)
