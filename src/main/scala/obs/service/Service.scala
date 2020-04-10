package obs.service

import obs.enums.{Criteria, Request}
import obs.model.Book
import obs.server.Utility

class Service {

  val dataService= new DataService()

  def getResponse(uriList:List[String], request:String, requestBody:String): (String,Int) ={
    val HTTP_METHOD_NOT_ALLOWED = 405
    val HTTP_SUCCESS = 200
    val HTTP_CREATED = 201
    val criteriaList=List("title","publisher","category","author")
    (request,uriList) match {
      case (Request.GET ,List("","") ) => (getBookList,HTTP_SUCCESS)
      case (Request.GET ,List(book,isbn)) if book=="book" && isbn !="" => (getBook(isbn),HTTP_SUCCESS)
      case (Request.GET ,List(criteria,value)) if criteriaList.contains(criteria.toLowerCase) =>
        (searchBook(criteria.toLowerCase,value.toLowerCase),HTTP_SUCCESS)
      case (Request.POST ,List(book,""))  if book =="book" => (addBook(Utility.jsonToObject(requestBody)),HTTP_CREATED)
      case _ => ("Invalid Request",HTTP_METHOD_NOT_ALLOWED)
    }
  }

  private def getBookList:String={
    Utility.objectListToJson(dataService.viewBookList.values)
  }

  private def addBook(book:Book):String={
    book.quantity=1
    Utility.objectToJson(dataService.addBook(book))
  }

  private def getBook(isbn:String):String={
    Utility.objectToJson(dataService.getBook(isbn))
  }

  private def searchBook(criteria:String,param:String):String={
    val bookList=criteria match {
      case Criteria.Title => dataService.searchBookByTitle(param)
      case Criteria.Author=> dataService.searchBookByAuthor(param)
      case Criteria.Publisher=> dataService.searchBookByPublisher(param)
      case Criteria.Category=> dataService.searchBookByCategory(param)
    }
    Utility.objectListToJson(bookList)
  }
}
