package com.jaf.examples.jdk8.dateTime;

import java.time.Instant;
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
	public void baseTest() {
		Instant now = Instant.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		System.out.println(formatter.format(now));
		
		
	}
	
}
