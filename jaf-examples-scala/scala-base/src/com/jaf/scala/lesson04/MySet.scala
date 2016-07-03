package com.jaf.scala.lesson04


object MySet {
  
  def main(args: Array[String]): Unit = {
    var intSet = Set(1, 2, 3);
    println(contains(intSet, 3));
    
    println(singletonSet(4));
    
    println(union(Set(1, 2), Set(2, 3, 4)));
    
    println(intersect(Set(1, 2), Set(1, 3)));
    
    println(diff(Set(1, 2, 3), Set(2)));
  }
  
  def contains(s: Set[Int], elem: Int) : Boolean = {
    s.foreach { x => {
      if(x == elem) return true;
    }}
    false;
  }
  
  def singletonSet(elem: Int): Set[Int] = {
    return Set(elem);
  }
  
  def union(s: Set[Int], t: Set[Int]): Set[Int] = {
    return s ++ t;
  }
  
  def intersect(s: Set[Int], t: Set[Int]): Set[Int] = {
    return s.intersect(t);
  }
  
  def diff(s: Set[Int], t: Set[Int]): Set[Int] = {
    return s.diff(t);
  }
  
}