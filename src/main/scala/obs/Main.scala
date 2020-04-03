package obs
import com.google.gson.Gson

object Main {
  def main(args: Array[String]):Unit = {
    ////add book
    val x= Book("1","HarryPotter","JK Rowling","ABC","fiction")
    val y= Book("2","HarryPotter2","JK Rowling","ABC","fiction")
    val z= Book("1","HarryPotter","JK Rowling","ABC","fiction")
    Service.addBook(x)
    Service.addBook(y)
    Service.addBook(z)

    val gson=new Gson()

    ////view book
//    Service.viewBookList.foreach(book => println(gson.toJson(book)))

    ////search book
    val list=Service.searchBookByCategory("Fiction")
    if (list.isEmpty) println("No record found")
    else list.foreach(book=> println(gson.toJson(book)))

  }

}
