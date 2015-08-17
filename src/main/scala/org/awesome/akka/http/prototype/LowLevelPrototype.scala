package org.awesome.akka.http.prototype

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.stream.ActorFlowMaterializer
import akka.stream.scaladsl.Sink
import akka.util.ByteString
import org.awesome.akka.http.prototype.domain.request.BidRequest

import scala.io.StdIn

import scala.concurrent._

object LowLevelActorSystemContext {
  implicit val actorSystem = ActorSystem("low-level-akka-http-prototype")
  implicit val executor = actorSystem.dispatcher
  implicit val materializer = ActorFlowMaterializer()
}

object LowLevelPrototype extends App {
  import LowLevelActorSystemContext._

  val serverSource = Http().bind("localhost", 8080)
  val bindingFuture = serverSource.to(Sink.foreach(connection =>
    connection handleWithAsyncHandler SimpleAsyncHandler
  )).run()

  println(s"Server is running on http://localhost:8080. Press RETURN to shutdown.")
  StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .flatMap(_ => Http().shutdownAllConnectionPools())
    .onComplete(_ => actorSystem.shutdown())
}

object SimpleAsyncHandler extends (HttpRequest => Future[HttpResponse]) {
  import HighLevelActorSystemContext._
  import RequestEntityConversions._
  import JsonUtils._
  override def apply(httpRequest: HttpRequest): Future[HttpResponse] = httpRequest match {
    case HttpRequest(GET, Uri.Path("/version"), _, _, _) => Future(HttpResponse(entity = HttpEntity("1.0-SNAPSHOT")))
    case HttpRequest(POST, Uri.Path("/openrtb"), _, entity, _) => entity.toUtf8StringFuture.map { json =>
      HttpResponse(entity = HttpEntity(
        ContentTypes.`application/json`,
        handlers.handleBidRequest(json.fromJson[BidRequest]).toJson
      ))
    }
    case _ => Future(HttpResponse(status = 404))
  }
}

object RequestEntityConversions {
  implicit class requestEntity2Utf8StringFuture(val requestEntity: RequestEntity) extends AnyVal {
    def toUtf8StringFuture(implicit flowMaterializer: ActorFlowMaterializer, executionContext: ExecutionContext) =
      requestEntity.dataBytes
      .runFold(ByteString())((byteString, byte) => byteString.concat(byte))
      .map(_.decodeString("UTF-8"))
  }
}
