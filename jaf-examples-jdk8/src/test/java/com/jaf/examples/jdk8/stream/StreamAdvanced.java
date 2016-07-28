package com.jaf.examples.jdk8.stream;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月28日
 * @since 1.0
 */
public class StreamAdvanced {
	
	@Test
	public void test1() {
		// 如果想要返回的结果是 Set 直接使用 
		// .collect(Collectors.toSet())
		List<Person> filtered =  persons
				.stream()
		        .filter(p -> p.name.startsWith("P"))
		        .collect(Collectors.toList());
		
		System.out.println(filtered);    // [Peter, Pamela]
	}
	
	@Test
	public void test2() {
		// age 18: [Max]
		// age 23: [Peter, Pamela]
		// age 12: [David]
		
		Map<Integer, List<Person>> personsByAge = persons
		    .stream()
		    .collect(Collectors.groupingBy(p -> p.age));

		personsByAge
		    .forEach((age, p) -> System.out.format("age %s: %s\n", age, p));
	}
	
	
	class Person {
	    String name;
	    int age;

	    Person(String name, int age) {
	        this.name = name;
	        this.age = age;
	    }

	    @Override
	    public String toString() {
	        return name;
	    }
	}

	List<Person> persons = 
		Lists.newArrayList(
			new Person("Max", 18),
			new Person("Peter", 23),
	        new Person("Pamela", 23),
	        new Person("David", 12)
	    );
}
