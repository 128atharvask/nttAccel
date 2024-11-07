name := "nttAccel"

scalaVersion := "2.13.14"

scalacOptions ++= Seq(
  "-feature",
  "-language:reflectiveCalls",
)

libraryDependencies ++= Seq(
  "edu.berkeley.cs" %% "chisel3" % "3.6.0",
  "edu.berkeley.cs" %% "chiseltest" % "0.6.0",
  "edu.berkeley.cs" %% "firrtl" % "1.5.0"
)

addCompilerPlugin("edu.berkeley.cs" % "chisel3-plugin" % "3.6.1" cross CrossVersion.full)

libraryDependencySchemes += "com.lihaoyi" %% "upickle" % VersionScheme.Always
