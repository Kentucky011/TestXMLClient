import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import spray.json.enrichAny

import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.xml._

object Boot extends App with MyProtocol {
  implicit val system = ActorSystem("MyLocalSystem")
  implicit val executionContext = system.dispatcher
  implicit val materializer = ActorMaterializer()
  val getHttpRequest = RequestBuilding.Get(uri = "http://localhost:9001/rest")
  val responseFuture: Future[HttpResponse] = Http().singleRequest(getHttpRequest)


  responseFuture.onComplete {
    case Success(httpResponse) => Unmarshal(httpResponse.entity).to[RestOperationResult].onComplete {
      case Success(value) =>
        val json = value.toJson

        val result = json.convertTo[RestOperationResult]

        val xml =
          <root>
            <status>
              {result.status}
            </status>
            <data>
              {result.data.getOrElse(List.empty[Rest]).map { rest =>
              <rest>
                <restId>
                  {rest.restId}
                </restId>
                <data>
                  {rest.data.map { item =>
                  <id>
                    {item.id}
                  </id>
                    <values>
                      {item.values.map { values =>
                      <value>
                        {values}
                      </value>
                    }}
                    </values>
                    <validate>
                      <idValid>
                        {item.validate.map(_.idValid).getOrElse("")}
                      </idValid>
                      {if (item.validate.flatMap(_.isValid).nonEmpty) {<isValid>{item.validate.flatMap(_.isValid).getOrElse(false)}</isValid>}}
                    </validate>
                }}
                </data>
              </rest>
            }}
            </data>{if (result.error.nonEmpty) {
            <error>
              {result.error}
            </error>
          }}
          </root>

        val xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n" + xml

        println(xmlStr)

        system.terminate()
      case Failure(exception) => throw exception
        system.terminate()
    }
    case Failure(exception) => throw exception
      system.terminate()
  }

}



/* implicit val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "MySystem")
   implicit val executionContext: ExecutionContext = system.executionContext
   val route = path("rest") {
     Directives.get {
       complete("Success")
     }
   }
   val server = Http().newServerAt("localhost", 9001).bind(route)
   server.map { _ =>
     println("successfully started on localhost:9001 ")
   } recover { case ex =>
     println("Failed to start the server due to " + ex.getMessage)
      }

  /* private val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = "http://localhost:9001/rest"))

   responseFuture
     .onComplete {
       case Success(res) => println(res)
       case Failure(_) => sys.error("something wrong")*/
     }*/
