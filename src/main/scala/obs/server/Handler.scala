package obs.server

import java.io.{BufferedReader, InputStreamReader}
import java.nio.charset.StandardCharsets
import com.google.gson.Gson
import com.sun.net.httpserver.{HttpExchange, HttpHandler}
import javafx.util.Pair
import obs.controller.Controller
import obs.model.Book
import scala.jdk.javaapi.CollectionConverters

class Handler extends HttpHandler {

  override def handle(httpExchange: HttpExchange): Unit = {
    val pair = UriParsing(httpExchange)
    val response = pair.getKey
    val status = pair.getValue
    httpExchange.getResponseHeaders.add("Content-type", " application/json; charset=utf-8")
    httpExchange.sendResponseHeaders(status, response.length)
    val os = httpExchange.getResponseBody
    os.write(response.getBytes)
    os.close()
  }

  def UriParsing(httpExchange: HttpExchange): Pair[String, Integer] = {
    val HTTP_METHOD_NOT_ALLOWED = 405
    val HTTP_SUCCESS = 200
    val HTTP_CREATED = 201
    val request = httpExchange.getRequestMethod
    request match {
      case "GET" =>
        val path = httpExchange.getRequestURI.getPath.split("/")
        if (path.length == 2) new Pair[String, Integer](bookList, HTTP_SUCCESS)
        else new Pair[String, Integer](searchBook(path(2), path(3)), HTTP_SUCCESS)
      case "POST" =>
        new Pair[String, Integer](addBook(httpExchange), HTTP_CREATED)
      case _ =>
        new Pair[String, Integer]("Invalid request", HTTP_METHOD_NOT_ALLOWED)
    }
  }

  def bookList: String = {
    val bookList = Controller.getBookList
    objectListToJson(bookList)
  }

  def searchBook(criteria: String, value: String): String = {
    val searchList =Controller.searchBook(criteria.toLowerCase, value.toLowerCase)
    objectListToJson(searchList)
  }

  def addBook(httpExchange: HttpExchange): String = {
    val isr = new InputStreamReader(httpExchange.getRequestBody, StandardCharsets.UTF_8)
    val query = new BufferedReader(isr).lines
    val stringBuilder = new StringBuilder
    query.forEach((s: String) => stringBuilder.append(s).append("\n"))
    objectToJson(Controller.addBook(jsonToObject(stringBuilder.toString)))
  }

  def objectToJson(list: Book): String = {
    new Gson().toJson(list)
  }

  def objectListToJson(list: Iterable[Book]): String = {
    new Gson().toJson(CollectionConverters.asJava(list))
  }

  def jsonToObject(response: String): Book = {
    new Gson().fromJson(response, classOf[Book])
  }
}
