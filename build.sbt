
name := "mon"

lazy val scalaV = "2.12.1"
lazy val scalaCheckVersion = "3.0.1"

scalaVersion in Global := scalaV

lazy val mon = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "io.voklim",
      scalaVersion := scalaV,
      version      := "0.1.0"
    )),
    libraryDependencies ++= Seq(
      "com.amazonaws.scala" % "aws-scala-sdk-cloudwatch" % "1.10.7",
      "org.typelevel" %% "cats" % "0.9.0",
      "io.monix" %% "monix-eval" % "2.1.1",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",

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
