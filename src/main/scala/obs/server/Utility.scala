package obs.server

import java.io.{BufferedReader, InputStreamReader}
import java.nio.charset.StandardCharsets
import com.google.gson.Gson
import com.sun.net.httpserver.HttpExchange
import javafx.util.Pair
import obs.controller.Controller
import obs.enums.Request
import obs.model.Book
import scala.jdk.javaapi.CollectionConverters

object Utility {
  def UriParsing(httpExchange: HttpExchange): Pair[String, Integer] = {
    val HTTP_METHOD_NOT_ALLOWED:Int = 405
    val HTTP_SUCCESS = 200
    val HTTP_CREATED = 201
    val HTTP_NOT_FOUND=404
    val request = httpExchange.getRequestMethod
    request match {
      case Request.GET =>
        val path = httpExchange.getRequestURI.getPath.split("/")
        if(path.length == 4 && path(2)=="book") new Pair[String,Integer](getBook(path(3)),HTTP_SUCCESS)
        else {
          val path = httpExchange.getRequestURI.getQuery.split("[/?=]")
          if(path.length == 2) new Pair[String, Integer](searchBooks(path(0), path(1)), HTTP_SUCCESS)
          else new Pair[String, Integer]("Invalid URL", HTTP_NOT_FOUND)
        }
      case Request.POST =>
        new Pair[String, Integer](addBook(httpExchange), HTTP_CREATED)
      case _ =>
        new Pair[String, Integer]("Invalid request", HTTP_METHOD_NOT_ALLOWED)
    }
  }

  private def bookList: String = {
    val bookList = Controller.getBookList
    objectListToJson(bookList)
  }

  private def searchBooks(criteria: String, value: String): String = {
    val searchList =Controller.searchBook(criteria.toLowerCase, value.toLowerCase)
    objectListToJson(searchList)
  }

  private def addBook(httpExchange: HttpExchange): String = {
    val isr = new InputStreamReader(httpExchange.getRequestBody, StandardCharsets.UTF_8)
    val query = new BufferedReader(isr).lines
    val stringBuilder = new StringBuilder
    query.forEach((s: String) => stringBuilder.append(s).append("\n"))
    objectToJson(Controller.addBook(jsonToObject(stringBuilder.toString)))
  }

  private def getBook(isbn:String):String ={
    objectToJson(Controller.getBook(isbn))
  }

  private def objectToJson(list: Any): String = {
    new Gson().toJson(list)
  }

  private def objectListToJson(list: Iterable[Book]): String = {
    new Gson().toJson(CollectionConverters.asJava(list))
  }

  private def jsonToObject(response: String): Book = {
    new Gson().fromJson(response, classOf[Book])
  }

}
