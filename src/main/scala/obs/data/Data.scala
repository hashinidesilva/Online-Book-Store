package obs.data

import obs.enums.Criteria
import obs.model.Book
import scala.collection.mutable

object Data {
  private val bookList = mutable.HashMap[Int, Book]()
  private var id: Int = 0

  def getBookList:mutable.HashMap[Int,Book] = {
    bookList
  }

  def addBook(b:Book): Unit ={
    id+=1
    bookList+=(id -> b)
  }

  def getBook(isbn:String):Option[Book]={
    bookList.values.find(book=> book.isbn==isbn)
  }
  def search(criteria:String, param: String):Iterable[Book]={
    criteria match {
      case Criteria.Title =>bookList.values.filter(book=> book.title.toLowerCase==param)
      case Criteria.Author => bookList.values.filter(book=> book.author.toLowerCase==param)
      case Criteria.Publisher => bookList.values.filter(book=> book.publisher.toLowerCase==param)
      case Criteria.Category => bookList.values.filter(book=> book.category.toLowerCase==param)

    }
  }

}
