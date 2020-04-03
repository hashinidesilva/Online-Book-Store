package obs

import enums.Criteria._
import scala.collection.mutable.ListBuffer
import scala.collection.mutable

object Service {

  def addBook(b:Book):Book={
    val list=Data.search(ISBN,b.isbn)
    if (list.isEmpty) Data.addBook(b) else Data.updateBookQuantity(b)
  }

  def viewBookList:mutable.HashMap[Int,Book]= {
    Data.getBookList
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