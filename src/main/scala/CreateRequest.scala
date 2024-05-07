/*import CreateRequest.Event
import akka.actor.Actor
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.server.Route
import akka.util.Timeout

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration.DurationInt


class CreateRequest extends Actor {
  implicit val timeout: Timeout = Timeout(30.seconds)
  implicit val ec: ExecutionContextExecutor = context.dispatcher

  val http = Http(context.system)
  val uri = "http://localhost:9001"

  override def preStart(): Unit = {
    self ! Event.Start

  }

  override def receive: Receive = {
    case Event.Start =>
      println("Отправляю запрос на локальный сервер")
      RequestBuilding.Get("http://localhost:9001/rest")

    case _ =>
      println("test")
  }

}

object CreateRequest {
  val name = "CreateRequest"
  val route = Route

 // def props(uri): Props = Props(new CreateRequest(uri))

  import akka.http.scaladsl.client.RequestBuilding.Get

  Get("http://localhost:9001/rest")

  sealed trait Event

  // внутреннее состояние актора(внутренние команды)
  object Event {
    case object Start extends Event
  }

  sealed trait Command

  // Внешнее состояние актора, на которое он реагирует(внешние команды)
  object Command {
    case object Get extends Command
  }
}
*/