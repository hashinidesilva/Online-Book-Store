package obs

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import enums.Criteria._
import scala.annotation.tailrec



object Data {
  private val bookList = mutable.HashMap[Int, Book]()
  private var id: Int = 0

  def getBookList:mutable.HashMap[Int,Book] = {
    bookList
  }

  def addBook(b:Book):Book={
    id+=1
    bookList+=(id->b)
    b
  }
  def update(b:Book): Int ={
    var count:Int=0
    for (book <- bookList.values.toList)
      if(b.isbn==book.isbn) {
        book.quantity+=1
        count=book.quantity
      }
    count
  }

  def updateBookQuantity(b:Book):Book=
    incrementQuantity(0,b)

  @tailrec
  private def incrementQuantity(i:Int,b:Book):Book= {
    if(bookList.values.toList(i).isbn==b.isbn) {
      bookList.values.toList(i).quantity+=1
      bookList.values.toList(i)
    }
    else incrementQuantity(i+1,b)
  }

  def search(criteria: Value, param: String):List[Book]={
    criteria match {
      case ISBN =>bookList.values.toList.filter(book=> book.isbn.toLowerCase==param.toLowerCase)
      case Title =>bookList.values.toList.filter(book=> book.title.toLowerCase==param.toLowerCase)
      case Author => bookList.values.toList.filter(book=> book.author.toLowerCase==param.toLowerCase)
      case Publisher => bookList.values.toList.filter(book=> book.publisher.toLowerCase==param.toLowerCase)
      case Category => bookList.values.toList.filter(book=> book.category.toLowerCase==param.toLowerCase)
    }

  }
}