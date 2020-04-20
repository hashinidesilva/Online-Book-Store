package rabbitmq.subscribers

import obs.enums.Queues
import obs.model.Book
import obs.common.Utility._

object CreateSubscriber {

  def main(args: Array[String]): Unit = {
    var createBook:Subscriber=null
    var response:String =null
    try{
      createBook = new Subscriber(Queues.CREATE_QUEUE_NAME.toString)
      val book=Book("978-981-08-4451-6","fp Programming in Scala","gh", "Man","Programming")
      response=createBook.call(objectToJson(book))
      println(response)
    }catch {
      case e:Exception => e.printStackTrace()
    }finally {
      if(createBook!=null){
        try{
          createBook.close()
        }catch {
          case _:Exception =>
        }
      }
    }
  }
}
