package org.awesome.akka.http.prototype

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.stream.ActorFlowMaterializer
import akka.stream.scaladsl.Sink

import scala.io.StdIn

object LowLevelPrototype extends App {
  implicit val actorSystem = ActorSystem("low-level-akka-http-prototype")
  implicit val executor = actorSystem.dispatcher
  implicit val materializer = ActorFlowMaterializer()

  val serverSource = Http().bind("localhost", 8080)
  val bindingFuture = serverSource.to(Sink.foreach(connection =>
    connection handleWithSyncHandler SimpleSyncHandler
  )).run()

  println(s"Server is running on http://localhost:8080. Press RETURN to shutdown.")
  StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .flatMap(_ => Http().shutdownAllConnectionPools())
    .onComplete(_ => actorSystem.shutdown())
}

object SimpleSyncHandler extends (HttpRequest => HttpResponse) {
  override def apply(httpRequest: HttpRequest): HttpResponse = {
    case HttpRequest(GET, Uri.Path("/version"), _, _, _) =>
      HttpResponse(entity = HttpEntity("1.0-SNAPSHOT"))
    case _: HttpRequest => HttpResponse(status = 404)
  }
}
