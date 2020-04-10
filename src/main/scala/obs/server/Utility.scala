package obs.server

import java.net.URI
import com.google.gson.Gson
import obs.model.Book
import scala.jdk.javaapi.CollectionConverters

object Utility {
  def parseUri(uri:URI): List[String] ={
    val path=uri.getPath.split("/")
    val query=uri.getQuery
    path.length match {
      case 4 if query==null => List(path(2),path(3))                  // /books/book/isbn
      case 2 if query==null  => List("","")                           // /books
      case 2 if query!=null && query.split("=").length==2 =>  // /books?author=authorName
        val criteria=query.split("=")(0)
        val value=query.split("=")(1)
        List(criteria,value)
      case 3 if query==null => List(path(2),"")                      // /books/book
      case _ => List()
    }
  }

  def objectToJson(obj: Any): String = {
    new Gson().toJson(obj)
  }

  def objectListToJson(list: Iterable[Book]): String = {
    new Gson().toJson(CollectionConverters.asJava(list))
  }

  def jsonToObject(response: String): Book = {
    new Gson().fromJson(response, classOf[Book])
  }
}
