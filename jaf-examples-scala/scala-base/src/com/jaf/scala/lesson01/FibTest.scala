package com.scala.examples

object FibTest {

  def main(args: Array[String]): Unit = {
    println(fib(10))
  }

  def fib(x: Int): Int = {
    if (x == 1)
      1
    else if (x == 2)
      1
    else
      fib(x - 2) + fib(x - 1);
  }

}