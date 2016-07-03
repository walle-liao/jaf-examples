package com.jaf.scala.lesson03

object Book {

  def main(args: Array[String]): Unit = {

    val books = List[Book](
      Book("Structure and Interpretation of Computer Programs",
        List("Abelson, Harald", "Sussman, Gerald J.")),
      Book("Introduction to Functional Programming",
        List("Bird, Richard", "Wadler, Phil")),
      Book("Effective Java",
        List("Bloch, Joshua")),
      Book("Java Puzzlers",
        List("Bloch, Joshua", "Gafter, Neal")),
      Book("Programming in Scala",
        List("Odersky, Martin", "Spoon, Lex", "Venners, Bill")))

    val a1 = find1(books)
    print("a1: "); println(a1)

    val a2 = find2(books)
    print("a2: "); println(a2)

    val a3 = find3(books)
    print("a3: "); println(a3)

    val a4 = find4(books)
    print("a4: "); println(a4)
  }

  def apply(t: String, a: List[String]) = {
    new Book(t, a)
  }

  def find1(books: List[Book]) = {

    for {
      b1 <- books
      b2 <- books
      if b1.title != b2.title
      a1 <- b1.authors
      a2 <- b2.authors
      if a1 == a2
    } yield a1
  }

  def find2(books: List[Book]) = {

    for {
      b1 <- books
      b2 <- books
      if b1.title < b2.title
      a1 <- b1.authors
      a2 <- b2.authors
      if a1 == a2
    } yield a1
  }

  def find3(books: List[Book]) = {

    (for {
      b1 <- books
      b2 <- books
      if b1.title != b2.title
      a1 <- b1.authors
      a2 <- b2.authors
      if a1 == a2
    } yield a1).distinct
  }

  def find4(books: List[Book]) = {

    def filteredBooks = books.toSet

    for {
      b1 <- filteredBooks
      b2 <- filteredBooks
      if b1.title != b2.title
      a1 <- b1.authors
      a2 <- b2.authors
      if a1 == a2
    } yield a1
  }

}

class Book(t: String, a: List[String]) {
  val title = t
  val authors = a
  def ==(book: Book) = {
    title == book.title && authors == book.authors
  }
}

