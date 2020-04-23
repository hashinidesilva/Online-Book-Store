package obs.model

class Response(message:String,code:Int) {
  val errorMessage: String =message
  val errorCode: Int =code
}
