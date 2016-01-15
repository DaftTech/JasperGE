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
  "org.scala-lang" % "scala-compiler" % "2.11.7",
  "org.scala-lang" % "scala-reflect" % "2.11.7",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4",
  "org.scala-lang.modules" %% "scala-pickling" % "0.10.1",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.4",
  "com.typesafe.slick" %% "slick" % "3.1.0",
  "com.typesafe.slick" %% "slick-extensions" % "3.1.0",
  "com.typesafe.akka" %% "akka-actor" % "2.4.0",
  "com.typesafe.akka" %% "akka-remote" % "2.4.0",
  "com.thoughtworks.xstream" % "xstream" % "1.4.8",
  "commons-io" % "commons-io" % "2.4",
  "com.chuusai" %% "shapeless" % "2.2.5",
  "com.h2database" % "h2" % "1.4.188",
  "net.ceedubs" %% "ficus" % "1.1.2",
  "org.slf4j" % "slf4j-simple" % "1.7.12",
  "org.apache.commons" % "commons-lang3" % "3.4"
)

mainClass in Compile := Some("com.dafttech.jasper.Main")