package com.jaf.scala.lesson07

/**
 * 定义一个接口，该接口用于排序
 */
trait Orderable[T] {
  
  def order(target: T): Boolean;
  
}