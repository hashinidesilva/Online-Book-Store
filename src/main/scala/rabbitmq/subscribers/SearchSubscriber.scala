package rabbitmq.subscribers

import obs.enums.Queues
import rabbitmq.configuration.ConnectionSettings
import protobuf.subscriber.SearchBook

object SearchSubscriber{

  def main(args: Array[String]): Unit = {
    var searchSubscriber:Subscriber=null
    var response:String =null
    try{
      val settings=new ConnectionSettings()
      val factory=settings.buildConnectionFactory()
      searchSubscriber = new Subscriber(Queues.SEARCH_QUEUE_NAME.toString,factory)
      val book=SearchBook(
        criteria = Some("title"),
        value = Some("fp programming in scala")
      )
      response=searchSubscriber.call(book.toByteArray)
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
