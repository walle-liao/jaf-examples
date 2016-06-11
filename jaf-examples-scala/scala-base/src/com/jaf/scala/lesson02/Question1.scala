package com.scala.examples.lesson02

object Question1 {
  
  def main(args: Array[String]): Unit = {
    print(pascal(10, 5));
  }
  
  def pascal(row: Int, col: Int): Int = {
    if(col > row)
      throw new IllegalArgumentException("列不能大于行数")
    
    var result = new Array[Array[Int]](row);  // 创建一个不规则的二维数组
    
    for(i <- intWrapper(0) until row) {
      val theRow = i + 1;  // 当前行数
    	var rowArray = new Array[Int](theRow);
    	rowArray(0) = 1;  // 每行的第一个元素总是为1
      for(j <- intWrapper(1) until theRow) {
        if(result(i - 1).length > j) {
          rowArray(j) = result(i - 1)(j) + result(i - 1)(j - 1); // 如果不是该行最后一个元素，为上一行当前列元素+上一行当前列前一个元素
        } else {
          rowArray(j) = 1;  // 每行最后一个元素总是为1
        }
      }
      result(i) = rowArray;
    }
    
    printPascal(result);
    
    return result(row - 1)(col - 1);
  }
  
  def printPascal(pascal : Array[Array[Int]]): Unit = {
    for(i <- intWrapper(0) until(pascal.length)) {
      val row = pascal(i);
      for(j <- intWrapper(0) until(row.length)) {
        print(row(j) + " ")
      }
      println()
    }
  }
  
}