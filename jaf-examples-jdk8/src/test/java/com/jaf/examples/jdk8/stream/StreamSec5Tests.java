package com.jaf.examples.jdk8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.assertj.core.util.Lists;
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

	@Test
	public void reduceTest() {
		List<Integer> nums = Lists.newArrayList(1, 2, 3, 4, 5);
		int sums = nums.stream().reduce(0, (sum, item) -> {
			return sum + item;
		});
		System.out.println(sums);
		
		// 对象的列表，使用 reduce
		Person result = Person.persons().stream().reduce(new Person("", 0), (p1, p2) -> {
			p1.name += (p2.name + ",");
			p1.age += p2.age;
			return p1;
		});	
		System.out.format("name=%s; age=%s", result.name, result.age);
		System.out.println();
		
		// 使用 reduce 查找 age 最大的用户
		Person.persons()
		    .stream()
		    .reduce((p1, p2) -> p1.age > p2.age ? p1 : p2)
		    .ifPresent(System.out::println);    // Pamela
	}
	
}
