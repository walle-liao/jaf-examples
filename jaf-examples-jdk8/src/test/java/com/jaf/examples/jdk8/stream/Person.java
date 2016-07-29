package com.jaf.examples.jdk8.stream;

import java.util.List;

import org.assertj.core.util.Lists;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年7月29日
 * @since 1.0
 */
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

	public static List<Person> persons() {
		return Lists.newArrayList(
			new Person("Max", 18),
	        new Person("Peter", 23),
	        new Person("Pamela", 23),
	        new Person("David", 12)
		);
	}
	
}
