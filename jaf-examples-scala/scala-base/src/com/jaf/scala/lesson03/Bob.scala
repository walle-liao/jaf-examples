package com.jaf.scala.lesson03

object Bob {
  
  def main(args: Array[String]): Unit = {
    getLast(ask("WATCH OUT!"))
    getLast(ask("Do you speak English?"))
    getLast(ask("DO YOU HEAR ME?"))
    getLast(ask("What DID you do?"))
    getLast(ask("   	"))
    getLast(ask("ab cde"))
  }
  
  def ask(q : String) = {
    val qam = Map("WATCH OUT!" -> "Whoa, chill out!",
              		"DO YOU HEAR ME\\?" -> "Whoa, chill out!",
              		"^.*\\?$" -> "Sure.",
              		"\\s*" -> "Fine. Be that way.",  // 任何空字符
              		"^(.*)[^\\?\\s]$" -> "Whatever."  // 以任何字符开头，不以?结尾
                  );  
    for {
      k <- qam.keys
      if q.matches(k)
    } yield qam.get(k)
  }
  
  def getLast(s : Iterable[Option[String]]) {
    var result: String = "";
    val it = s.iterator;
    while(it.hasNext) {
      result = it.next().get;
    }
    println(result);
  }
  
}