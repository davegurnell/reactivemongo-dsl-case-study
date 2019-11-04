name := "dsl"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.10"

scalacOptions ++= Seq(
  "-encoding", "UTF-8",   // source files are in UTF-8
  "-deprecation",         // warn about use of deprecated APIs
  "-unchecked",           // warn about unchecked type parameters
  "-feature",             // warn about misused language features
  "-language:higherKinds",// allow higher kinded types without `import scala.language.higherKinds`
  "-Xlint",               // enable handy linter warnings
  "-Ypartial-unification" // allow the compiler to unify type constructors of different arities
)

addCompilerPlugin("org.typelevel" % "kind-projector" % "0.11.0" cross CrossVersion.full)

libraryDependencies ++= Seq(
  "org.reactivemongo" %% "reactivemongo" % "0.18.7",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.18.7-play27",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.typelevel" %% "cats-core" % "1.4.0"
)
