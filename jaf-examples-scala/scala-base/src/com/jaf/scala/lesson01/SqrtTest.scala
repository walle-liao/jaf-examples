package com.scala.examples

object SqrtTest {

  def main(args: Array[String]): Unit = {
    val t = sqrt(10, 1);
    println(t);
  }

  def sqrt(x: Double, y: Double): Double = {
    var t = y;
    t = (y + x / y) / 2
    if ((t * t - x).abs > 0.00001) {  // 精度 0.00001
      sqrt(x, t);   // 如果精度不够，递归调用
    } else {
      t
    }
  }

}