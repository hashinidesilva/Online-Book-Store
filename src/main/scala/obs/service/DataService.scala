package obs.service

import obs.data.Data
import obs.enums.Criteria
import obs.model.Book
import scala.collection.mutable

class DataService {
  def addBook(book:Book):Book= {
    val b: Option[(Int,Book)]=Data.getBookList.find(x => x._2.isbn==book.isbn)
      b match {
        case Some((key,_))=>
          Data.getBookList(key).quantity+=1
          book.quantity=Data.getBookList(key).quantity
        case None =>
          Data.addBook(book)
      }
    book
    }

  def getBook(isbn:String):Any ={
    Data.getBook(isbn) match {
      case Some(b) => b
      case None => "No record found"
    }
  }

  def viewBookList:mutable.HashMap[Int,Book]= {
    Data.getBookList
  }

  def searchBookByTitle(title:String):Iterable[Book]={
    Data.search(Criteria.Title,title)
  }

  def searchBookByAuthor(author:String):Iterable[Book]={
    Data.search(Criteria.Author,author)
  }

  def searchBookByPublisher(publisher:String):Iterable[Book]={
    Data.search(Criteria.Publisher,publisher)
  }

  def searchBookByCategory(category:String):Iterable[Book]={
    Data.search(Criteria.Category,category)
  }
}
