lazy val root = (project in file(".")).
  settings(
    name := "AkkaHttpPrototype",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.11.5",
    assemblyJarName := s"akka-http-prototype.jar",
    mainClass in assembly := Some("org.awesome.akka.http.prototype.LowLevelPrototype")
  )

libraryDependencies ++= {
  val akkaV       = "2.3.10"
  val akkaStreamV = "1.0-RC2"
  val scalaTestV  = "2.2.4"
  val json4sV     = "3.2.11"
  val kamonV      = "0.4.0"
  Seq(
    "ch.qos.logback" % "logback-classic" % "1.1.3",
    "com.typesafe.akka" %% "akka-actor"                           % akkaV,
    "com.typesafe.akka" %% "akka-slf4j"                           % akkaV,
    "com.typesafe.akka" %% "akka-stream-experimental"             % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-core-experimental"          % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-scala-experimental"         % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental"    % akkaStreamV,
    "com.typesafe.akka" %% "akka-http-testkit-scala-experimental" % akkaStreamV,
    "org.scalatest"     %% "scalatest"                            % scalaTestV % "test",
    "org.json4s"        %% "json4s-native"                        % json4sV,
    "org.json4s"        %% "json4s-jackson"                       % json4sV,
    "io.kamon"          %% "kamon-core"                           % kamonV,
    "io.kamon"          %% "kamon-akka"                           % kamonV,
    "io.kamon"          %% "kamon-log-reporter"                   % kamonV
  )
}
