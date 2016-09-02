package com.jaf.examples.java8.dateTime;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

/**
 * java 8 里面新的日期和时间 API 很大程度上和 Joda-Time 的 API 类似
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月30日
 * @since 1.0
 */
public class Java8DateTimeTests {
	
	@Test
	public void baseTest() throws InterruptedException {
		LocalDateTime begin = LocalDateTime.now();
		System.out.println(begin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		
		Thread.sleep(1000);
		LocalDateTime end = LocalDateTime.now();
		
		Duration d = Duration.between(begin, end);
		System.out.println(d.getSeconds());
	}
	
}
