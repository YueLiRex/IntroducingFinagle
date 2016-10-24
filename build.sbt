name := """finagle"""

version := "1.0"

scalaVersion := "2.11.7"

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
  "com.twitter" %% "finagle-http" % "6.38.0",
  "org.typelevel" %% "scodec-core" % "1.6.0",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)


