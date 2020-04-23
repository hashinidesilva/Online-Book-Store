package obs.service

import obs.common.Utility
import obs.data.Data
import obs.enums.Criteria
import obs.model.Book

class BookService {

  def getBookList:String={
    Utility.objectListToJson(Data.getBookList.values)
  }

  def addBook(requestBody:String):String={
    val book=Utility.jsonToObject(requestBody)
    if(Utility.isbnChecker(book.isbn)){
      book.quantity=1
      val b: Option[(Int,Book)]=Data.getBookList.find(x => x._2.isbn==book.isbn)
      b match {
        case Some((key,_))=>
          Data.getBookList(key).quantity+=1
          book.quantity=Data.getBookList(key).quantity
        case None =>
          Data.addBook(book)
      }
      Utility.objectToJson(book)
    }else "Invalid ISBN"

  }

  def getBook(isbn:String):String={
    if(Utility.isbnChecker(isbn)) {
      Data.getBook(isbn) match {
        case Some(b) => Utility.objectToJson(b)
        case None => "No record found"
      }
    }else "Invalid ISBN"
  }

  def searchBook(criteria:String,param:String):String={
    val bookList=criteria match {
      case Criteria.Title => searchBookByTitle(param)
      case Criteria.Author=> searchBookByAuthor(param)
      case Criteria.Publisher=> searchBookByPublisher(param)
      case Criteria.Category=> searchBookByCategory(param)
    }
    Utility.objectListToJson(bookList)
  }

  private def searchBookByTitle(title:String):Iterable[Book]={
    Data.search(Criteria.Title,title)
  }

  private def searchBookByAuthor(author:String):Iterable[Book]={
    Data.search(Criteria.Author,author)
  }

  private def searchBookByPublisher(publisher:String):Iterable[Book]={
    Data.search(Criteria.Publisher,publisher)
  }

  private def searchBookByCategory(category:String):Iterable[Book]={
    Data.search(Criteria.Category,category)
  }
}
