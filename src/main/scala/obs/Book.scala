package obs

case class Book(isbn:String,title:String,author:String,publisher:String,category:String,var quantity:Int) {

//  def print:String=
//    "ISBN: "+isbn+" Title: "+title+" Author: "+author+" Publisher: "+publisher+" Category: "+category
}

object Book{
  def apply(isbn:String,title:String,author:String,publisher:String,category:String):Book=
    Book(isbn,title,author,publisher,category,1)

}