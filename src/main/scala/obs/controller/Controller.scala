package obs.controller

import obs.enums.Criteria
import obs.model.Book
import obs.service.Service

object Controller {
  def getBookList:Iterable[Book]={
    Service.viewBookList.values
  }

  def addBook(b:Book):Book={
    b.quantity=1
    Service.addBook(b)
  }

  def getBook(isbn:String):Any={
    Service.getBook(isbn)
  }

  def searchBook(criteria:String,param:String):Iterable[Book]={
    criteria match {
      case Criteria.Title => Service.searchBookByTitle(param)
      case Criteria.Author=> Service.searchBookByAuthor(param)
      case Criteria.Publisher=> Service.searchBookByPublisher(param)
      case Criteria.Category=> Service.searchBookByCategory(param)
      case _ => Iterable[Book]()
    }
  }
}
