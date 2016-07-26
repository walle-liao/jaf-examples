package com.jaf.examples.jdk8.stream;

import java.util.Comparator;

import org.junit.Test;

/**
 * 有状态的流转换
 * 之前介绍的流转换都是无状态的（元素之间不需要互相依赖）
 * 当从一个已过滤或已映射的流中获取某个元素时，结果并不依赖之前的元素
 * 但像 distinct, sort 这样的方法，流必须记住之前已读取的元素
 * distinct, sort
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月26日
 * @since 1.0
 */
public class StreamSec4Tests {
	
	@Test
	public void distinctTest() {
		Supports.words().stream().filter(s -> s.length() > 12).distinct().forEach(System.out::println);
	}
	
	@Test
	public void sortTest() {
//		Supports.words().stream().filter(s -> s.length() > 12).sorted().forEach(System.out::println);
		Supports.words()
			.stream()
			.filter(s -> s.length() > 12)
			.sorted(Comparator.comparing(String::length))
			.forEach(System.out::println);
	}
	
}
