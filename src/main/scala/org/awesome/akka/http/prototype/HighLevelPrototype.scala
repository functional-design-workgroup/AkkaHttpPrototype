package org.awesome.akka.http.prototype

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorFlowMaterializer

import scala.io.StdIn

object HighLevelActorSystemContext {
  implicit val actorSystem = ActorSystem("high-level-akka-http-prototype")
  implicit val executor = actorSystem.dispatcher
  implicit val materializer = ActorFlowMaterializer()
}

object HighLevelPrototype extends App {
  import HighLevelActorSystemContext._

  val bindingFuture = Http().bindAndHandle(Router.routes, "localhost", 8080)

  println(s"Server is running on http://localhost:8080. Press RETURN to shutdown.")
  StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .flatMap(_ => Http().shutdownAllConnectionPools())
    .onComplete(_ => actorSystem.shutdown())
}

object Router {
  import HighLevelActorSystemContext._
  def routes = path("version") { get {
    complete(HttpResponse(entity = "1.0-SNAPSHOT"))
  }} ~
  path("openrtb") { post { entity(as[String]) { body =>
    complete(HttpResponse(entity = s"Request's body is:\n$body"))
  }}}
}
