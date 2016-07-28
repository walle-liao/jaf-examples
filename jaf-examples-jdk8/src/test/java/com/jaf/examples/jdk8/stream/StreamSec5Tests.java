package com.jaf.examples.jdk8.stream;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * 聚合 count max min findFirst：返回非空集合的第一个值 findAny： anyMatch allMatch noneMatch
 * reduce
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月26日
 * @since 1.0
 */
public class StreamSec5Tests {

	@Test
	public void countTest() {
		Integer[] nums = new Integer[] { 1, 5, null, 3, null, 10, 7 };
		System.out.println(Stream.of(nums).count());
		System.out.println(Stream.of(nums).max(Integer::compareTo));
	}

	@Test
	public void findFirstTest() {
		// findFirst: 它总是返回 Stream 的第一个元素，或者空。
		Arrays.asList("a1", "a2", "a3").stream().findFirst()
				.ifPresent(System.out::println);
	}

}
