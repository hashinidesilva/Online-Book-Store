package rabbitmq.subscribers

import obs.enums.Queues

object GetSubscriber {

  def main(args: Array[String]): Unit = {
    var getBook:Subscriber =null
    var response:String =null
    try{
      getBook = new Subscriber(Queues.GET_QUEUE_NAME.toString)
      val isbn="978-981-08-4451-6"
      response=getBook.call(isbn)
      println(response)
    }catch {
      case e:Exception => e.printStackTrace()
    }finally {
      if(getBook!=null){
        try{
          getBook.close()
        }catch {
          case _:Exception =>
        }
      }
    }
  }
}
