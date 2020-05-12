package rabbitmq.subscribers

import obs.enums.Queues
import rabbitmq.configuration.ConnectionSettings
import protobuf.subscriber.GetBook

object GetSubscriber {

  def main(args: Array[String]): Unit = {
    var getSubscriber:Subscriber =null
    var response:String =null
    try{
      val settings=new ConnectionSettings()
      val factory=settings.buildConnectionFactory()
      getSubscriber = new Subscriber(Queues.GET_QUEUE_NAME.toString,factory)
      val isbn=GetBook(
        isbn=Some("9781617290657")
      )
      response=getSubscriber.call(isbn.toByteArray)
      println(response)
    }catch {
      case e:Exception => e.printStackTrace()
    }finally {
      if(getSubscriber!=null){
        try{
          getSubscriber.close()
        }catch {
          case _:Exception =>
        }
      }
    }
  }
}
