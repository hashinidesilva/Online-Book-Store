package obs.server

import java.io.{BufferedReader, InputStreamReader}
import java.nio.charset.StandardCharsets

import com.sun.net.httpserver.{HttpExchange, HttpHandler}
import obs.common.Utility
import obs.controller.Controller
import obs.service.Service

class Handler extends HttpHandler {

  override def handle(httpExchange: HttpExchange): Unit = {
    val request = httpExchange.getRequestMethod
    val uri = httpExchange.getRequestURI
    val controller=new Controller()

    //RequestBody
    val isr = new InputStreamReader(httpExchange.getRequestBody, StandardCharsets.UTF_8)
    val query = new BufferedReader(isr).lines
    val stringBuilder = new StringBuilder
    query.forEach((s: String) => stringBuilder.append(s).append("\n"))
    val requestBody=stringBuilder.toString()

    //Get response and status
    val (response,status)=controller.getResponse(Utility.parseUri(uri),request,requestBody)

    httpExchange.getResponseHeaders.add("Content-type", " application/json; charset=utf-8")
    httpExchange.sendResponseHeaders(status, response.length)
    val os = httpExchange.getResponseBody
    os.write(response.getBytes)
    os.close()
  }
}