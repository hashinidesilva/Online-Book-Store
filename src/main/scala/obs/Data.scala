package obs

import scala.collection.mutable
import obs.enums.Criteria

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

  def search(criteria:String, param: String):Iterable[Book]={
    criteria match {
      case Criteria.ISBN =>bookList.values.filter(book=> book.isbn==param)
      case Criteria.Title =>bookList.values.filter(book=> book.title.toLowerCase==param)
      case Criteria.Author => bookList.values.filter(book=> book.author.toLowerCase==param)
      case Criteria.Publisher => bookList.values.filter(book=> book.publisher.toLowerCase==param)
      case Criteria.Category => bookList.values.filter(book=> book.category.toLowerCase==param)

    }
  }

}