name := "NTT_Accel"

version := "0.1"

scalaVersion := "2.13.12" // Ensure Scala version matches

libraryDependencies ++= Seq(
  "edu.berkeley.cs" %% "chisel3" % "3.5.5",  // Stable version of Chisel
  "edu.berkeley.cs" %% "firrtl" % "1.5.5"    // Compatible FIRRTL version
)

// Specify the latest compatible version of the chisel-plugin
addCompilerPlugin("org.chipsalliance" %% "chisel-plugin" % "3.5.5")
