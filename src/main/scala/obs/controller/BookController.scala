package obs.controller

import obs.enums.Request
import obs.service.BookService
import obs.model.Response

class BookController {
  val service = new BookService()

  def getResponse(uriList:List[String], request:String, requestBody:String): Response ={
    val HTTP_METHOD_NOT_ALLOWED = 405
    val HTTP_SUCCESS = 200
    val HTTP_CREATED = 201
    val criteriaList=List("title","publisher","category","author")
    (request,uriList.length) match {
      case (Request.GET,2) if uriList.head=="book" => new Response(service.getBook(uriList(1)),HTTP_SUCCESS)
      case (Request.GET,0) => new Response(service.getBookList,HTTP_SUCCESS)
      case (Request.GET,2) if criteriaList.contains(uriList.head.toLowerCase) =>
        new Response(service.searchBook(uriList.head.toLowerCase,uriList(1).toLowerCase),HTTP_SUCCESS)
      case (Request.POST,1) if uriList.head =="book" => new Response(service.addBook(requestBody),HTTP_CREATED)
      case _ => new Response("Invalid Request",HTTP_METHOD_NOT_ALLOWED)
    }
  }
}
