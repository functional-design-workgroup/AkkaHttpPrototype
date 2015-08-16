package org.awesome.akka.http.prototype

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorFlowMaterializer
import org.json4s.{NoTypeHints, DefaultFormats}
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.write

import scala.io.StdIn
import domain._

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
  implicit val readFormats = DefaultFormats
  implicit val writeFormats = Serialization.formats(NoTypeHints)
  def routes = path("version") { get {
    complete(HttpResponse(entity = "1.0-SNAPSHOT"))
  }} ~
  path("openrtb") { post { entity(as[String]) { body =>
    val bidRequest = parse(body).camelizeKeys.extract[request.BidRequest]
    val bidResponse = handlers.handleBidRequest(bidRequest)
    complete(HttpResponse(entity = HttpEntity(ContentTypes.`application/json`, write(bidResponse))))
  }}}
}
