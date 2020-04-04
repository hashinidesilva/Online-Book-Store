package obs

import enums.Criteria._
import scala.collection.mutable

object Service {

  def addBook(newList:mutable.Buffer[Book]):mutable.Buffer[Book]={

    newList.foreach(book=> {
      val bookList=Data.getBookList.values.toList
      if (!bookList.exists(b => b.isbn==book.isbn)) Data.addBook(book)
      else {
        book.quantity= Data.update(book)
      }
    })
    newList
  }

  def viewBookList:mutable.HashMap[Int,Book]= {
    Data.getBookList
  }

  def searchBookByISBN(isbn:String):Iterable[Book]={
    Data.search(ISBN,isbn)
  }

  def searchBookByTitle(title:String):Iterable[Book]={
    Data.search(Title,title)
  }

  def searchBookByAuthor(author:String):Iterable[Book]={
    Data.search(Author,author)
  }

  def searchBookByPublisher(publisher:String):Iterable[Book]={
    Data.search(Publisher,publisher)
  }

  def searchBookByCategory(category:String):Iterable[Book]={
    Data.search(Category,category)
  }
}