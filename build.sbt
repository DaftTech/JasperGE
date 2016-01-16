name := "JasperGE"

version := "0.0.0"

enablePlugins(
  JavaAppPackaging,
  UniversalPlugin)

scalaVersion := "2.11.7"

resolvers ++= Seq(
  "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/",
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/maven-releases/"
)

libraryDependencies ++= Seq(
  "org.lwjgl" % "lwjgl" % "3.0.0b",
  "org.lwjgl" % "lwjgl-platform" % "3.0.0b"
    classifier "natives-windows"
    classifier "natives-linux"
    classifier "natives-osx",
  "org.joml" % "joml" % "1.6.8",
  "org.scala-lang" % "scala-compiler" % "2.11.7",
  "org.scala-lang" % "scala-reflect" % "2.11.7",
  "com.typesafe.akka" %% "akka-actor" % "2.4.0",
  "commons-io" % "commons-io" % "2.4",
  "com.chuusai" %% "shapeless" % "2.2.5",
  "org.apache.commons" % "commons-lang3" % "3.4"
)

mainClass in Compile := Some("com.dafttech.jasper.Main")