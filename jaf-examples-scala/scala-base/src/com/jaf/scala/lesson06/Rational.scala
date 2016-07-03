package com.jaf.scala.lesson06

class Rational(val numerator : Int, val denominator : Int) {
  
  def +(operand: Rational): Rational = {
    val newNumerator = numerator * operand.denominator + operand.numerator * denominator;
    val newDenominator = denominator * operand.denominator;
    new Rational(newNumerator, newDenominator);
  };
  
  def -(operand: Rational): Rational = {
    val newNumerator = numerator * operand.denominator - operand.numerator * denominator;
    val newDenominator = denominator * operand.denominator;
    new Rational(newNumerator, newDenominator);
  };
  
  def *(operand: Rational): Rational = {
    new Rational(numerator * operand.numerator, denominator * operand.denominator);
  }
  
  def /(operand: Rational): Rational = {
    new Rational(numerator * operand.denominator, denominator * operand.numerator);
  }
  
  // ------- 对整数的操作 -------
  def +(operand: Int): Rational = {
    this + new Rational(operand, 1);
  }
  
  def -(operand: Int): Rational = {
    this - new Rational(operand, 1);
  }
  
  def *(operand: Int): Rational = {
    this * new Rational(operand, 1);
  }
  
  def /(operand: Int): Rational = {
    this / new Rational(operand, 1);
  }
  
  def reduction(): Rational = {
    // 公因数 2-9, 2代替2,4,6,8；3代替3,6,9（能被6整除的一定可以被3整除，其他同理）
    val t: Array[Int] = Array(2, 3, 5, 7);
    var result = this;
    for(i <- t) {
      result = recursive(result, i);
    }
    result
  }
  
  // 递归调用，除于公因数
  def recursive(operand: Rational, factor: Int): Rational = {
    var t = operand;
    if(t.numerator % factor == 0 && t.denominator % factor == 0) {
      t = new Rational(t.numerator/factor, t.denominator/factor);
    } else {
      return t
    }
    recursive(t, factor);
  }
  
  override def toString() : String = {
    return numerator + "/" + denominator;
  }
  
}