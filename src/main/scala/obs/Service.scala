package obs

import enums.Criteria._
import scala.collection.mutable.ListBuffer
import scala.collection.mutable
import obs.Book


object Service {

  def addBook(b:Book):Any={
    val list=Data.search(ISBN,b.isbn)
    if (list.isEmpty) Data.addBook(b) else Data.updateBook(b)
  }

  def viewBookList:Iterable[Book]= {
    Data.getBookList.values
  }

  def searchBookByISBN(isbn:String):ListBuffer[Book]={
    Data.search(ISBN,isbn)
  }

  def searchBookByTitle(title:String):ListBuffer[Book]={
    Data.search(Title,title)
  }

  def searchBookByAuthor(author:String):ListBuffer[Book]={
    Data.search(Author,author)
  }

  def searchBookByPublisher(publisher:String):ListBuffer[Book]={
    Data.search(Publisher,publisher)
  }

  def searchBookByCategory(category:String):ListBuffer[Book]={
    Data.search(Category,category)
  }
}