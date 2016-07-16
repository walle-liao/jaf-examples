package com.jaf.scala.lesson07

/**
 * 定义学生类，从 OrderPerson 继承
 * 实现 order 方法，该方法通过学生的 score 属性进行排序
 * 覆盖 toString 方法，方便排序后打印观察输出结果
 */
class Student(name:String, age:Int, val score:Int) 
    extends OrderPerson(name:String, age:Int) {
  
  override def order(target: OrderPerson): Boolean = {
    this.score > target.asInstanceOf[Student].score;
  }
  
  override def toString(): String = {
    "student: [name: " + this.name + ", age: " 
      + this.age + ", score: " + this.score + "] \n\t"
  }
  
}