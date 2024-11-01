name := "NTT_Accel"

version := "0.1"

scalaVersion := "2.13.10" // Use the installed Scala version

libraryDependencies ++= Seq(
  "edu.berkeley.cs" %% "chisel3" % "3.5.1"
)


fork in run := true // Ensure sbt can run the Verilog generation
