import Dependencies._

lazy val scalaCheckVersion = "3.0.1"

lazy val mon = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "io.voklim",
      scalaVersion := "2.12.1",
      version      := "0.1.0"
    )),
    name := "mon",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats" % "0.9.0",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
      "com.amazonaws.scala" % "aws-scala-sdk-cloudwatch" % "1.10.7",
      "io.monix" %% "monix-eval" % "2.1.1",

      "org.scalatest" %% "scalatest" % scalaCheckVersion % "test"
    )
  ).configs(IntegrationTest)
  .settings(Defaults.itSettings: _*)
  .settings(
    scalaSource in IntegrationTest := baseDirectory.value / "src" / "it" / "scala"
  )

lazy val IntegrationTest = config("it").extend(Test)

scalacOptions in Global ++= Seq(
  "-Xfatal-warnings",
  "-unchecked",
  "-feature",
  "-deprecation",
  "-language:higherKinds",
  "-language:implicitConversions"
)
