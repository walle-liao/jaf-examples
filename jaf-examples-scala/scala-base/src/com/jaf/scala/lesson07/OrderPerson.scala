package com.jaf.scala.lesson07

/**
 * 定义一个抽象的 OrderPerson，该类从 Person 继承，并且实现 Orderable 接口
 */
abstract class OrderPerson(name:String, age:Int) 
  extends Person(name:String, age:Int) with Orderable[OrderPerson] {
  
  
}