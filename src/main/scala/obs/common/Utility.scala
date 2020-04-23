package obs.common

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}
import java.net.URI
import com.google.gson.Gson
import obs.model.Book
import scala.jdk.javaapi.CollectionConverters
import java.util.regex.Pattern

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

  def isbnChecker(isbn:String): Boolean ={
    val regex = "^(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]" +
      "{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$"

    if (Pattern.compile(regex).matcher(isbn).matches()) {
      val id=isbn.replace("-","")
      if(id.length==10) {
        val seq: Seq[Int] = for {
          (x, y) <- id zip (10 until 0 by -1)
        } yield x.asDigit * y
        if (seq.sum % 11 == 0) true else false
      }
      else if(id.length==13){
        val seq: Seq[Int] = for {
          (x, y) <- id zip LazyList.continually(Seq(1,3)).flatten
        } yield x.asDigit * y
        if (seq.sum % 10 == 0) true else false
      }else false
    }else false
  }

  def serialize(value: Any): Array[Byte] = {
    val stream: ByteArrayOutputStream = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(stream)
    oos.writeObject(value)
    oos.close()
    stream.toByteArray
  }

  def deserialize(value:Array[Byte]):Any={
    val stream=new ByteArrayInputStream(value)
    val ois = new ObjectInputStream(stream)
    ois.readObject
  }

}
