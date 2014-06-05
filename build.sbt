name := "lolcode-dsl"

version := "0.1.0"

unmanagedResourceDirectories in Compile <+= baseDirectory{ _ / "src/main/resources"}

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.2.3")