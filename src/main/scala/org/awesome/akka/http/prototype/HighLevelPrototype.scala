package org.awesome.akka.http.prototype

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorFlowMaterializer
import org.awesome.akka.http.prototype.domain.request.BidRequest

import scala.concurrent.Await
import scala.io.StdIn

import scala.concurrent.duration._

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

  Await.result(
    bindingFuture.flatMap(_.unbind()).flatMap(_ => Http().shutdownAllConnectionPools()), 30.seconds
  )
  actorSystem.shutdown()
  actorSystem.awaitTermination(5.seconds)
  println("Done.")
}

object Router {
  import HighLevelActorSystemContext._
  import JsonUtils._
  def routes = path("version") { get {
    complete(HttpResponse(entity = "1.0-SNAPSHOT"))
  }} ~
  path("openrtb") { post { entity(as[String]) { body =>
    complete {
      HttpResponse(entity = HttpEntity(
          ContentTypes.`application/json`,
          handlers.handleBidRequest(body.fromJson[BidRequest]).toJson
      ))
    }
  }}}
}
