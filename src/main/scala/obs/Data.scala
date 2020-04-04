package obs

import scala.collection.mutable
import enums.Criteria._

object Data {
  private val bookList = mutable.HashMap[Int, Book]()
  private var id: Int = 0

  def getBookList:mutable.HashMap[Int,Book] = {
    bookList
  }

  def addBook(b:Book):Int={
    val book: Option[(Int,Book)]=bookList.find(x => x._2.isbn==b.isbn)
    book match {
      case Some((a,_))=>
        bookList(a).quantity+=1
        bookList(a).quantity
      case None =>
        id+=1
        bookList+=(id -> b)
        b.quantity
    }
  }

  def search(criteria: Value, param: String):Iterable[Book]={
    criteria match {
      case ISBN =>bookList.values.filter(book=> book.isbn==param)
      case Title =>bookList.values.filter(book=> book.title.toLowerCase==param.toLowerCase)
      case Author => bookList.values.filter(book=> book.author.toLowerCase==param.toLowerCase)
      case Publisher => bookList.values.filter(book=> book.publisher.toLowerCase==param.toLowerCase)
      case Category => bookList.values.filter(book=> book.category.toLowerCase==param.toLowerCase)
    }
  }

}