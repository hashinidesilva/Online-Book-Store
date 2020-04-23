package rabbitmq.subscribers

import obs.enums.Queues
import obs.model.Book
import obs.model.message_model.CreateBook
import rabbitmq.configuration.ConnectionSettings

object CreateSubscriber {

  def main(args: Array[String]): Unit = {
    var createSubscriber:Subscriber=null
    var response:String =null
    try{
      val settings=new ConnectionSettings()
      val factory=settings.buildConnectionFactory()
      createSubscriber = new Subscriber(Queues.CREATE_QUEUE_NAME.toString,factory)
      val book=Book("9781617290657","fp Programming in Scala","Paul Chiusano", "Manning","Programming")
      response=createSubscriber.call(CreateBook(book))
      println(response)
    }catch {
      case e:Exception => e.printStackTrace()
    }finally {
      if(createSubscriber!=null){
        try{
          createSubscriber.close()
        }catch {
          case _:Exception =>
        }
      }
    }
  }
}
