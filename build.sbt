lazy val root = (project in file(".")).
  settings(
    name := "AkkaHttpPrototype",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.11.4",
    assemblyJarName := s"akka-http-prototype.jar",
    mainClass in assembly := Some("org.awesome.akka.http.prototype.LowLevelPrototype")
  )

libraryDependencies ++= {
  val akkaV       = "2.3.10"
  val akkaStreamV = "1.0-RC2"
  val scalaTestV  = "2.2.4"
  val json4sV     = "3.2.11"
  Seq(
    "com.typesafe.akka" %% "akka-actor"                           % akkaV,
    "com.typesafe.akka" %% "akka-stream-experimental"             % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-core-experimental"          % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-scala-experimental"         % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental"    % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-testkit-scala-experimental" % akkaStreamV,
    "org.scalatest"     %% "scalatest"                            % scalaTestV % "test",
    "org.json4s"        %% "json4s-native"                        % json4sV,
    "org.json4s"        %% "json4s-jackson"                       % json4sV
  )
}
