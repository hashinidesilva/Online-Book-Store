package obs.service

import obs.enums.{Criteria, Request}
import obs.model.Book
import obs.server.Utility

class Service {

  def getResponse(uriList:List[String], request:String, requestBody:String): (String,Int) ={
    val HTTP_METHOD_NOT_ALLOWED = 405
    val HTTP_SUCCESS = 200
    val HTTP_CREATED = 201
    val criteriaList=List("title","publisher","category","author")
    (request,uriList) match {
      case (Request.GET ,List("","") ) => (getBookList,HTTP_SUCCESS)
      case (Request.GET ,List(book,isbn)) if(book=="book" && isbn !="") => (getBook(isbn),HTTP_SUCCESS)
      case (Request.GET ,List(criteria,value)) if(criteriaList.contains(criteria.toLowerCase)) =>
        (searchBook(criteria.toLowerCase,value.toLowerCase),HTTP_SUCCESS)
      case (Request.POST ,List(book,""))  if(book =="book") => (addBook(Utility.jsonToObject(requestBody)),HTTP_CREATED)
      case _ => ("Invalid Request",HTTP_METHOD_NOT_ALLOWED)
    }
  }

  private def getBookList:String={
    Utility.objectListToJson(DataService.viewBookList.values)
  }

  private def addBook(b:Book):String={
    b.quantity=1
    Utility.objectToJson(DataService.addBook(b))
  }

  private def getBook(isbn:String):String={
    Utility.objectToJson(DataService.getBook(isbn))
  }

  private def searchBook(criteria:String,param:String):String={
    val bookList=criteria match {
      case Criteria.Title => DataService.searchBookByTitle(param)
      case Criteria.Author=> DataService.searchBookByAuthor(param)
      case Criteria.Publisher=> DataService.searchBookByPublisher(param)
      case Criteria.Category=> DataService.searchBookByCategory(param)
    }
    Utility.objectListToJson(bookList)
  }
}
