package com.scala.examples.lesson02

import java.util.PrimitiveIterator

object Question2 {
  
  def main(args: Array[String]): Unit = {
    var strs = List("(if (zero? x) max (/ 1 x))",
                    "I told him (that it’s not (yet) done). (But he wasn’t listening)",
                    "--)",
                    "(()))",
                    ")(()");
    
    strs.foreach { str => printBalance(str) }
  }
  
  def printBalance(str: String): Unit = {
    val chars:List[Char] = str.toList;
    println(chars)
    
    println(balance(chars))
  }
  
  def balance(chars: List[Char]): Boolean = {
    // 定义一个初始变量0，碰到左括号+1，碰到右括号-1，最终判断i是否=0
    var i:Int = 0;
    for(c <- chars) {
      if(c == '(') {
        i = i + 1;
      } else if(c == ')') {
        i = i - 1;
      } else {
        // ignore
      }
      
      // 这里i一定不能小于0，如果小于0，说明碰到了右括号在前的情况
      // 例如 ')(()'，虽然最终i=0，但是这个不是合理的表达式
      if(i < 0)
        return false;
    }
    return i == 0;
  }
  
}