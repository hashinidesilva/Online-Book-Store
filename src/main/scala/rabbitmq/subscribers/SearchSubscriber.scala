package rabbitmq.subscribers

import obs.enums.Queues
import obs.model.message_model.SearchBooks
import rabbitmq.configuration.ConnectionSettings

object SearchSubscriber{

  def main(args: Array[String]): Unit = {
    var searchSubscriber:Subscriber=null
    var response:String =null
    try{
      val settings=new ConnectionSettings()
      val factory=settings.buildConnectionFactory()
      searchSubscriber = new Subscriber(Queues.SEARCH_QUEUE_NAME.toString,factory)
      response=searchSubscriber.call(SearchBooks("title","fp programming in scala"))
      println(response)
    }catch {
      case e:Exception => e.printStackTrace()
    }finally {
      if(searchSubscriber!=null){
        try{
          searchSubscriber.close()
        }catch {
          case _:Exception =>
        }
      }
    }
  }
}
