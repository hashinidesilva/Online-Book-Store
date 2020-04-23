package rabbitmq.settings

import com.rabbitmq.client.ConnectionFactory

class ConnectionSettings(userName:String="guest",
                         password: String="guest",
                         host:String="localhost",
                         virtualHost:String="/",
                         port:Int=5672) {

  def buildConnectionFactory(): ConnectionFactory ={
    val factory: ConnectionFactory = new ConnectionFactory
    factory.setUsername(userName)
    factory.setPassword(password)
    factory.setHost(host)
    factory.setVirtualHost(virtualHost)
    factory.setPort(port)
    factory
  }

}
