package obs.common

import java.net.URI
import com.google.gson.Gson
import obs.model.Book
import scala.jdk.javaapi.CollectionConverters

object Utility {

  def parseUri(uri:URI): List[String] ={
    val path=uri.getPath.split("/").toList
    val query=uri.getQuery
    if(query!=null) path.drop(2) ::: query.split("=").toList
    else path.drop(2)
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
