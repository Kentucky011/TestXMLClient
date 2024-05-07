


case class MyResponseFormat(rest: Rest,
                          data: Data,
                          restOperationResult: RestOperationResult,
                          validate: Validate)
                          extends MyProtocol

case class Rest(
                 data: List[Data],
                 restId: String
               )

case class Data(
                 id: String,
                 values: List[String],
                 validate: Option[Validate]
               )

case class RestOperationResult(
                                data: Option[List[Rest]],
                                status: String,
                                error: Option[String]
                              )

case class Validate(
                     idValid: String,
                     isValid: Option[Boolean]
                   )