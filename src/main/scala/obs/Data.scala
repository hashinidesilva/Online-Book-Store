package obs

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import enums.Criteria._
import scala.annotation.tailrec



object Data {
  private val bookList = mutable.HashMap[Int, Book]()
  private var id: Int = 0

  def getBookList:mutable.HashMap[Int,Book] = {
    bookList
  }

  def addBook(b:Book):Book={
    id+=1
    bookList+=(id->b)
    b
  }

  def updateBookQuantity(b:Book):Book=
//    bookList.foreach {case (_,value)=> if(value.isbn==b.isbn){
//      value.quantity+=1
//    } }
    incrementQuantity(0,b)

  @tailrec
  private def incrementQuantity(i:Int,b:Book):Book= {
    if(bookList.values.toList(i).isbn==b.isbn) {
      bookList.values.toList(i).quantity+=1
      bookList.values.toList(i)
    }
    else incrementQuantity(i+1,b)
  }


  def search(criteria: Value, param: String):ListBuffer[Book]= {
    val filtered = new ListBuffer[Book]()
    val valList = bookList.values.toList
    criteria match {
      case Author => searchFrom(0, valList, filtered)(x => valList(x).author.toLowerCase == param.toLowerCase)
      case ISBN => searchFrom(0, valList, filtered)(x => valList(x).isbn.toLowerCase == param.toLowerCase)
      case Title => searchFrom(0, valList, filtered)(x => valList(x).title.toLowerCase == param.toLowerCase)
      case Publisher => searchFrom(0, valList, filtered)(x => valList(x).publisher.toLowerCase == param.toLowerCase)
      case Category => searchFrom(0, valList, filtered)(x => valList(x).category.toLowerCase == param.toLowerCase)
    }
  }

  @tailrec
  private def searchFrom(i: Int, l: List[Book], filtered: ListBuffer[Book])(f: Int => Boolean): ListBuffer[Book] = {
    if (i > l.length - 1) filtered
    else if (f(i)) {
      filtered += l(i)
      searchFrom(i + 1, l, filtered)(f)
    }
    else searchFrom(i + 1, l, filtered)(f)
  }
}