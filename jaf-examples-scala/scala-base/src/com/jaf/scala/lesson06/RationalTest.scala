package com.jaf.scala.lesson06

object RationalTest {
  
  def main(args: Array[String]): Unit = {
    
    println("========= 1. 对分数的加减乘除计算 ==========");
    
    var t1 = new Rational(1, 5);
    var t2 = new Rational(1, 4);
    println(t1 + t2)
    println(t1 - t2)
    println(t1 * t2)
    println(t1 / t2)
    
    println("========= 2. 分数对象的约分 ==========");
    
    println(new Rational(200, 400).reduction())
    println(new Rational(14, 98).reduction())
    println(new Rational(2*3*4*5*6*7*8*9, 2*3*4*5*6*7*8*9*9).reduction())
    
    println("========= 3. 分数对象和整数对象的加减乘除操作 ==========");
    
    println(t1 + 5)
    println(t1 - 5)
    println(t1 * 5)
    println(t1 / 5)
  }
  
  
}