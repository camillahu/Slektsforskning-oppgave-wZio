ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.15"

lazy val root = (project in file("."))
  .settings(
    name := "Slektsforskning-oppgave"
  )

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % "2.1.4",
  "dev.zio" %% "zio-http" % "3.0.0-RC9"
)
