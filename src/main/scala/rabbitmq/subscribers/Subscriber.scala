package rabbitmq.subscribers

import java.util.UUID
import java.util.concurrent.{ArrayBlockingQueue, BlockingQueue}
import com.rabbitmq.client.AMQP.BasicProperties
import com.rabbitmq.client.{CancelCallback, Channel, Connection, ConnectionFactory, DeliverCallback, Delivery}

class ResponseCallback(corrId: String) extends DeliverCallback{
  val response:BlockingQueue[String] = new ArrayBlockingQueue[String](1)

  override def handle(consumerTag: String, message: Delivery): Unit = {
    if(message.getProperties.getCorrelationId.equals(corrId)) {
      response.offer(new String(message.getBody,"UTF-8"))
    }
  }
  def take():String={
    response.take()
  }
}

class Subscriber(requestQueue:String) {
  val factory = new ConnectionFactory
  factory.setHost("localhost")
  val connection: Connection = factory.newConnection()
  val channel: Channel = connection.createChannel()
  val replyQueueName: String = channel.queueDeclare().getQueue
  val requestQueueName:String =requestQueue

  def call(message:String):String={
    val corrId = UUID.randomUUID().toString
    val props = new BasicProperties.Builder()
      .correlationId(corrId)
      .replyTo(replyQueueName).build()
    channel.basicPublish("",requestQueueName,props,
      message.getBytes("UTF-8"))
    val responseCallback = new ResponseCallback(corrId)
    val cancel:CancelCallback = _=> {}
    channel.basicConsume(replyQueueName,true,responseCallback,cancel)
    responseCallback.take()
  }

  def close(): Unit ={
    connection.close()
  }
}
