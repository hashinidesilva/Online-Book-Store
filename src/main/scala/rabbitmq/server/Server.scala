package rabbitmq.server

import java.util.concurrent.CountDownLatch
import com.rabbitmq.client.AMQP.BasicProperties
import com.rabbitmq.client._
import obs.enums.Queues
import obs.enums.Request
import obs.controller.Controller
import scala.collection.mutable.ListBuffer

class ServerCallback(channel: Channel, latch: CountDownLatch) extends DeliverCallback{
  override def handle(consumerTag:String,delivery:Delivery): Unit ={
    val controller =new Controller
    var response:String=null
    val replyProps = new BasicProperties.Builder()
      .correlationId(delivery.getProperties.getCorrelationId)
      .build()
    val message = new String(delivery.getBody,"UTF-8")
    channel.getChannelNumber match {
      case 1 => response=controller.getResponse(List("book",message),Request.GET,"")._1
      case 2 => response=controller.getResponse(List("book"),Request.POST,message)._1
      case 3 => response=controller.getResponse(List(message.split("=")(0),message.split("=")(1)),Request.GET,"")._1
      case _ => response="Channel not found"
    }
    channel.basicPublish("",delivery.getProperties.getReplyTo,replyProps,response.getBytes("UTF-8"))
    channel.basicAck(delivery.getEnvelope.getDeliveryTag,false)
    latch.countDown()
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
      val factory= new ConnectionFactory
      factory.setHost("localhost")
      connection=factory.newConnection()
      val latch = new CountDownLatch(1)
      val cancel:CancelCallback=_=> {}
      for (i <- queueList.indices){
        channelList(i)=connection.createChannel()
        channelList(i).queueDeclare(queueList(i).toString,false,false,false,null)
        channelList(i).basicConsume(queueList(i).toString,false,new ServerCallback(channelList(i),latch),cancel)
      }
      latch.await()
    }catch {
      case e:Exception => e.printStackTrace()
    }
  }
}
