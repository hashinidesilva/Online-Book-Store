package rabbitmq.server

import java.util.concurrent.CountDownLatch
import com.rabbitmq.client.AMQP.BasicProperties
import com.rabbitmq.client._
import obs.enums.Queues
import obs.controller.BookMessageController
import obs.common.Utility
import obs.model.message_model.{CreateBook, GetBook, SearchBooks}
import rabbitmq.configuration.ConnectionSettings
import scala.collection.mutable.ListBuffer

class ServerCallback(channel: Channel, latch: CountDownLatch) extends DeliverCallback{
  override def handle(consumerTag:String,delivery:Delivery): Unit ={
    var response:String=null
    val replyProps = new BasicProperties.Builder()
      .correlationId(delivery.getProperties.getCorrelationId)
      .build()
    val message=Utility.deserialize(delivery.getBody)
    response=getResponse(channel,message)
    channel.basicPublish("",delivery.getProperties.getReplyTo,replyProps,response.getBytes("UTF-8"))
    channel.basicAck(delivery.getEnvelope.getDeliveryTag,false)
    latch.countDown()
  }

  def getResponse(channel:Channel,message:Any):String ={
    val controller =new BookMessageController
    channel.getChannelNumber match {
      case 1 =>
        val get=message.asInstanceOf[GetBook]
        controller.getBook(get.isbn)
      case 2 =>
        val create=message.asInstanceOf[CreateBook]
        controller.createBook(create.book)
      case 3 =>
        val search=message.asInstanceOf[SearchBooks]
        controller.searchBook(search.criteria,search.value)
      case _ => "Channel not found"
    }
  }
}

object Server {
  val queueList: List[Queues.Value] =Queues.values.toList
  def main(args: Array[String]): Unit = {
    var connection:Connection=null
    val channelGet: Channel = null
    val channelCreate: Channel = null
    val channelSearch: Channel = null
    val channelList=ListBuffer(channelGet,channelCreate,channelSearch)
    try{
      val settings=new ConnectionSettings()
      val factory=settings.buildConnectionFactory()
      connection=factory.newConnection()
      val latch = new CountDownLatch(1)
      val cancel:CancelCallback=_=> {}
      for (i <- queueList.indices){
        channelList(i)=connection.createChannel()
        channelList(i).queueDeclare(queueList(i).toString,false,false,false,null)
        channelList(i).basicQos(1)
        channelList(i).basicConsume(queueList(i).toString,false,new ServerCallback(channelList(i),latch),cancel)
      }
      latch.await()
    }catch {
      case e:Exception => e.printStackTrace()
    }
  }
}
