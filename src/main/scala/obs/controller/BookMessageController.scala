package obs.controller

import obs.common.Utility
import obs.model.Book
import obs.service.BookService

class BookMessageController {
  val bookService=new BookService

  def createBook(book:Book):String={
    bookService.addBook(Utility.objectToJson(book))
  }

  def getBook(isbn:String): String ={
    bookService.getBook(isbn)
  }
  def searchBook(criteria:String,param:String): String ={
    bookService.searchBook(criteria,param)
  }
}
