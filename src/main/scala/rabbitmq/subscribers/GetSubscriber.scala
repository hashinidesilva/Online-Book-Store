package rabbitmq.subscribers

import obs.enums.Queues
import obs.model.message_model.GetBook
import rabbitmq.configuration.ConnectionSettings

object GetSubscriber {

  def main(args: Array[String]): Unit = {
    var getSubscriber:Subscriber =null
    var response:String =null
    try{
      val settings=new ConnectionSettings()
      val factory=settings.buildConnectionFactory()
      getSubscriber = new Subscriber(Queues.GET_QUEUE_NAME.toString,factory)
      val isbn="978-981-08-4451-6"
      response=getSubscriber.call(GetBook(isbn))
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
