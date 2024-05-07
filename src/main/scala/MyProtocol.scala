import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait MyJsonProtocol extends DefaultJsonProtocol {
  implicit val validateFormat: RootJsonFormat[Validate] = jsonFormat2(Validate.apply)
  implicit val dataFormat: RootJsonFormat[Data] = jsonFormat3(Data.apply)
  implicit val restFormat: RootJsonFormat[Rest] = jsonFormat2(Rest.apply)
  implicit val restOperationResultFormat: RootJsonFormat[RestOperationResult] = jsonFormat3(RestOperationResult.apply)
  implicit val responseFormat: RootJsonFormat[ResponseFormat] = jsonFormat4(ResponseFormat)
}
