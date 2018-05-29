name := "bash-org-crawler"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies += "org.jsoup" % "jsoup" % "1.8.3"
libraryDependencies += "io.circe" %% "circe-core" % "0.9.3"
libraryDependencies += "io.circe" %% "circe-generic" % "0.9.3"
libraryDependencies += "io.circe" %% "circe-parser" % "0.9.3"
libraryDependencies += "com.typesafe" % "config" % "1.2.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test
