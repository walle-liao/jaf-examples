package com.jaf.scala.lesson05

object School {
  
  var student = Map[String, Int]();
  
  def add(name: String, grade: Int) = {
    student = student.+(name -> grade)
  }
  
  def del(name: String) = {
    student = student.-(name);
  }
  
  def update(name: String, grade: Int) = {
    student = student.updated(name, grade);
  }
  
  def selectByName(name: String) = {
    val grade = student.getOrElse(name, "该学生不存在")
    println("学生：" + name + "，所在年级：" + grade);
  }
  
  def selectByGrade(grade: Int) = {
    println("年级：" + grade + "，所有的学生")
    student.foreach(s => {
      if(s._2 == grade) {
        println(s._1);
      }
    })
  }
  
  def main(args: Array[String]): Unit = {
    add("walle", 1);
    add("hello", 2);
    add("world", 2);
    
    selectByName("walle");
    selectByGrade(2);
    
    update("walle", 2);
    selectByGrade(2);
  }
  
}