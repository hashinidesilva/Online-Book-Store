package obs.controller

import obs.common.Utility
import obs.enums.Request
import obs.service.Service

class Controller {
  val service = new Service()

  def getResponse(uriList:List[String], request:String, requestBody:String): (String,Int) ={
    val HTTP_METHOD_NOT_ALLOWED = 405
    val HTTP_SUCCESS = 200
    val HTTP_CREATED = 201
    val criteriaList=List("title","publisher","category","author")
    (request,uriList.length) match {
      case (Request.GET,2) if uriList.head=="book" =>  (service.getBook(uriList(1)),HTTP_SUCCESS)
      case (Request.GET,0) => (service.getBookList,HTTP_SUCCESS)
      case (Request.GET,2) if criteriaList.contains(uriList.head.toLowerCase) =>
        (service.searchBook(uriList.head.toLowerCase,uriList(1).toLowerCase),HTTP_SUCCESS)
      case (Request.POST,1) if uriList.head =="book" => (service.addBook(Utility.jsonToObject(requestBody)),HTTP_CREATED)
      case _ => ("Invalid Request",HTTP_METHOD_NOT_ALLOWED)
    }
  }
}
