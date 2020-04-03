package obs

import scala.collection.mutable.ListBuffer
object Controller {
  def getBookList:List[Book]={
    Service.viewBookList.values.toList
  }

  def addBook(b:Book):Book={
    b.quantity=1
    Service.addBook(b)
  }

  def searchBook(criteria:String,param:String):ListBuffer[Book]={
    criteria match {
      case "isbn" => Service.searchBookByISBN(param)
      case "title" => Service.searchBookByTitle(param)
      case "author"=> Service.searchBookByAuthor(param)
      case "publisher"=> Service.searchBookByPublisher(param)
      case "category"=> Service.searchBookByCategory(param)
    }
  }

}
