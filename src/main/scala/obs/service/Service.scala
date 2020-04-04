package obs.service

import obs.data.Data
import obs.enums.Criteria
import obs.model.Book
import scala.collection.mutable

object Service {
  def addBook(newList:mutable.Buffer[Book]):mutable.Buffer[Book]= {
    newList.foreach(book => {
      val b: Option[(Int,Book)]=Data.getBookList.find(x => x._2.isbn==book.isbn)
      b match {
        case Some((a,_))=>
          Data.getBookList(a).quantity+=1
          book.quantity=Data.getBookList(a).quantity
        case None =>
          Data.addBook(book)
      }
    })
    newList
  }

  def viewBookList:mutable.HashMap[Int,Book]= {
    Data.getBookList
  }

  def searchBookByISBN(isbn:String):Iterable[Book]={
    Data.search(Criteria.ISBN,isbn)
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
