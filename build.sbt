ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.15"
javacOptions ++= Seq("-source", "23", "-target", "23")

lazy val root = (project in file("."))
  .settings(
    name := "Slektsforskning-oppgave"
  )

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % "2.1.4",
  "dev.zio" %% "zio-http" % "3.0.0-RC9",
  "dev.zio" %% "zio-json" % "0.6.2"
)
