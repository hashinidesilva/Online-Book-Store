package obs.server

import com.sun.net.httpserver.{HttpExchange, HttpHandler}

class Handler extends HttpHandler {

  override def handle(httpExchange: HttpExchange): Unit = {
    val response = Utility.UriParsing(httpExchange).getKey
    val status = Utility.UriParsing(httpExchange).getValue
    httpExchange.getResponseHeaders.add("Content-type", " application/json; charset=utf-8")
    httpExchange.sendResponseHeaders(status, response.length)
    val os = httpExchange.getResponseBody
    os.write(response.getBytes)
    os.close()
  }
}

