package com.jaf.examples.jdk8.stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Test;

/**
 * 并行流
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月26日
 * @since 1.0
 */
public class StreamSec8Tests {

	@Test
	public void test11() {
		List<String> myList = Lists.newArrayList("a1", "a2", "b1", "c12", "c1", "c");
		myList.stream()
			.filter(s -> s != null && s.startsWith("c"))
			.map(String::toUpperCase)
			.sorted((o1, o2) -> o1.length() - o2.length())
			.forEach(System.out::println);
	}

	@Test
	public void test12() {
		List<String> myList = Lists.newArrayList("a1", "a2", "b1", "c12", "c1", "c");
		List<String> filterList = new ArrayList<String>();
		for(String str : myList) {
			if(str != null && str.startsWith("c")) {
				filterList.add(str.toUpperCase());
			}
		}
		Collections.sort(filterList, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.length() - o2.length();
			}
		});
		for(String str : filterList) {
			System.out.println(str);
		}
	}
	
	
	
}
