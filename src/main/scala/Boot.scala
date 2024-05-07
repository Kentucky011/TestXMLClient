import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext

object Boot extends App {
  implicit val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "MySystem")
  implicit val executionContext: ExecutionContext = system.executionContext

  private val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = "http://localhost:9001"))

  responseFuture
    .onComplete {
      case Success(res) => println(res)
      case Failure(_) => sys.error("something wrong")
    }
}
