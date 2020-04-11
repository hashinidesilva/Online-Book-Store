package obs.service

import obs.common.Utility
import obs.enums.{Criteria, Request}
import obs.model.Book

class Service {

  val dataService= new DataService()

  def getResponse(uriList:List[String], request:String, requestBody:String): (String,Int) ={
    val HTTP_METHOD_NOT_ALLOWED = 405
    val HTTP_SUCCESS = 200
    val HTTP_CREATED = 201
    val criteriaList=List("title","publisher","category","author")
    (request,uriList.length) match {
      case (Request.GET,2) if(uriList(0)=="book") =>  (getBook(uriList(1)),HTTP_SUCCESS)
      case (Request.GET,0) => (getBookList,HTTP_SUCCESS)
      case (Request.GET,2) if criteriaList.contains(uriList(0).toLowerCase) =>
        (searchBook(uriList(0).toLowerCase,uriList(1).toLowerCase),HTTP_SUCCESS)
      case (Request.POST,1) if uriList(0) =="book" => (addBook(Utility.jsonToObject(requestBody)),HTTP_CREATED)
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
