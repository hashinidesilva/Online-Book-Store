package rabbitmq.subscribers

import obs.enums.Queues
import rabbitmq.configuration.ConnectionSettings
import protobuf.subscriber.CreateBook

object CreateSubscriber {

  def main(args: Array[String]): Unit = {
    var createSubscriber:Subscriber=null
    var response:String =null
    try{
      val settings=new ConnectionSettings()
      val factory=settings.buildConnectionFactory()
      createSubscriber = new Subscriber(Queues.CREATE_QUEUE_NAME.toString,factory)
      val book:CreateBook=CreateBook(
        isbn = Some("9781617290657"),
        title = Some("fp Programming in Scala"),
        author = Some("Paul Chiusano"),
        publisher = Some("Manning"),
        category = Some("Programming")
      )
      response=createSubscriber.call(book.toByteArray)
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
