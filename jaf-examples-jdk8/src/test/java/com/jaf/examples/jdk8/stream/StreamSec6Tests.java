package com.jaf.examples.jdk8.stream;

import java.util.stream.Stream;

import org.junit.Test;

/**
 * collect 收集结果 toMap 将结果收集到 Map 中
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月26日
 * @since 1.0
 */
public class StreamSec6Tests {
	
	
	@Test
	public void test1() {
		// 该语句到底是下面哪种输出呢？
		// d2, a2, b1, b3, c, d2, a2, b1, b3, c  循环多次，每次进行一个操作，效率更低
		// d2, d2, a2, a2, b1, b1, b3, b3, c, c  一次循环，处理所有操作，效率更高
		Stream.of("d2", "a2", "b1", "b3", "c")
			.filter(s -> {
				System.out.println("filter: " + s);
				return true;
			})
			.forEach(s -> System.out.println("forEach: " + s));
	}
	
	@Test
	public void test2() {
		// 因为 anyMatch 的影响，只要找到一个匹配的元素，整个循环立即就结束，不会循环所有的元素
		// d2, D2, a2, A2
		Stream.of("d2", "a2", "b1", "b3", "c")
		    .map(s -> {
		        System.out.println("map: " + s);
		        return s.toUpperCase();
		    })
		    .anyMatch(s -> {
		        System.out.println("anyMatch: " + s);
		        return s.startsWith("A");
		    });
	}
	
	@Test
	public void test3() {
		Stream.of("d2", "a2", "b1", "b3", "c")
		    .map(s -> {
		        System.out.println("map: " + s);
		        return s.toUpperCase();
		    })
		    .filter(s -> {
		        System.out.println("filter: " + s);
		        return s.startsWith("A");
		    })
		    .forEach(s -> System.out.println("forEach: " + s));
		
		// map:     d2
		// filter:  D2
		// map:     a2
		// filter:  A2
		// forEach: A2
		// map:     b1
		// filter:  B1
		// map:     b3
		// filter:  B3
		// map:     c
		// filter:  C
	}
	
	@Test
	public void test4() {
		// test3 + test4 说明当有多个中间操作时，应该讲 filter 这类操作放在前面，先把流的元素过滤出来
		
		Stream.of("d2", "a2", "b1", "b3", "c")
		    .filter(s -> {
		        System.out.println("filter: " + s);
		        return s.startsWith("a");
		    })
		    .map(s -> {
		        System.out.println("map: " + s);
		        return s.toUpperCase();
		    })
		    .forEach(s -> System.out.println("forEach: " + s));

		// filter:  d2
		// filter:  a2
		// map:     a2
		// forEach: A2
		// filter:  b1
		// filter:  b3
		// filter:  c
	}
	
	@Test
	public void test5() {
		Stream.of("d2", "a2", "b1", "b3", "c")
		    .sorted((s1, s2) -> {
		        System.out.printf("sort: %s; %s\n", s1, s2);
		        return s1.compareTo(s2);
		    })
		    .filter(s -> {
		        System.out.println("filter: " + s);
		        return s.startsWith("a");
		    })
		    .map(s -> {
		        System.out.println("map: " + s);
		        return s.toUpperCase();
		    })
		    .forEach(s -> System.out.println("forEach: " + s));
	}
	
	@Test
	public void test6() {
		// 变换写法之后，排序一次都没有了，map 也只有一次
		Stream.of("d2", "a2", "b1", "b3", "c")
		    .filter(s -> {
		        System.out.println("filter: " + s);
		        return s.startsWith("a");
		    })
		    .sorted((s1, s2) -> {
		        System.out.printf("sort: %s; %s\n", s1, s2);
		        return s1.compareTo(s2);
		    })
		    .map(s -> {
		        System.out.println("map: " + s);
		        return s.toUpperCase();
		    })
		    .forEach(s -> System.out.println("forEach: " + s));

		// filter:  d2
		// filter:  a2
		// filter:  b1
		// filter:  b3
		// filter:  c
		// map:     a2
		// forEach: A2
	}
	
}
