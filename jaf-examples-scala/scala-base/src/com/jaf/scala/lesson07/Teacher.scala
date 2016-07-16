package com.jaf.scala.lesson07

/**
 * 定义老师类，从 OrderPerson 继承
 * 实现 order 方法，该方法通过老师的 age 属性进行排序
 * 覆盖 toString 方法，方便排序后打印观察输出结果
 */
class Teacher(name:String, age:Int) 
    extends OrderPerson(name:String, age:Int) {
  
  def order(target: OrderPerson): Boolean = {
    this.age > target.asInstanceOf[Teacher].age
  }

  
  override def toString(): String = {
    "teacher: [name: " + this.name + ", age: " 
      + this.age + "] \n\t"
  }
}
