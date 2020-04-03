package obs

class Book(val isbn:String,val title:String,val author:String,val publisher:String,val category:String,var quantity:Int) {

    def print:String=
      "ISBN: "+isbn+" Title: "+title+" Author: "+author+" Publisher: "+publisher+" Category: "+category+" Q"+quantity
//  def this(isbn: String, title: String, author: String, publisher: String, category: String) =
//    this(isbn, title, author, publisher, category, 1)
}
//object Book{
////  def apply(isbn:String,title:String,author:String,publisher:String,category:String):Book=
////    Book(isbn,title,author,publisher,category,1)
////}