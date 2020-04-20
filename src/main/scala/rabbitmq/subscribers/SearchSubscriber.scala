package rabbitmq.subscribers

import obs.enums.Queues

object SearchSubscriber{

  def main(args: Array[String]): Unit = {
    var searchBook:Subscriber=null
    var response:String =null
    try{
      searchBook = new Subscriber(Queues.SEARCH_QUEUE_NAME.toString)
      val query="title=fp programming in Scala"
      response=searchBook.call(query)
      println(response)
    }catch {
      case e:Exception => e.printStackTrace()
    }finally {
      if(searchBook!=null){
        try{
          searchBook.close()
        }catch {
          case _:Exception =>
        }
      }
    }
  }
}
