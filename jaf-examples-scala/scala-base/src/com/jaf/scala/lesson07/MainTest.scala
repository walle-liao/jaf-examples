package com.jaf.scala.lesson07

object MainTest {
  
  def main(args: Array[String]): Unit = {
    val students: List[Student] = List(
      new Student("s1", 1, 2),
      new Student("s2", 1, 2),
      new Student("s3", 2, 4),
      new Student("s1", 5, 6),
      new Student("s1", 5, 3)
    )
    println(orderPerson(filterByName(students, "s1")));
    
    println("======= filter teacher and order =======")
    
    val teachers: List[Teacher] = List(
      new Teacher("t1", 20),
      new Teacher("t2", 30),
      new Teacher("t1", 25),
      new Teacher("t1", 21)
    )
    println(orderPerson(filterByName(teachers, "t1")));
  }
  
  /**
   * 通过名称筛选的方法
   */
  def filterByName(persons: List[OrderPerson], name: String) : List[OrderPerson] = {
    for {
      item <- persons
      if item.name == name
    } yield item
  }
  
  /**
   * 排序 list 的方法
   */
  def orderPerson(persons: List[OrderPerson]): List[OrderPerson] = {
    persons.sortWith((a, b) => a.order(b));
  }
  
}