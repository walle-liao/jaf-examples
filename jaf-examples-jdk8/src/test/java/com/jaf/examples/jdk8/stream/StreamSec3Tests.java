package com.jaf.examples.jdk8.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * 
 * filter：过滤集合中的元素
 * map：转换集合，可以将一个 T 类型的集合转换成一个 R 类型的集合
 * flatMap：转换集合并且和并结果，和 map 的作用类似
 * concat：将两个流连接到一起
 * limit：limit(n) 取符合条件的前 n 条记录
 * skip：skip(n)，和 limit 的作用正好相反，这个是忽略前 n 条记录，即返回 n 条后面的记录
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月26日
 * @since 1.0
 */
public class StreamSec3Tests {
	
	protected static final String[] strs = {"aB", "ac", "Bc"};
	
	@Test
	public void filterTest() {
		Stream<String> stream = Arrays.stream(strs);
		assertThat(stream.filter(s -> s.startsWith("a")).count()).isEqualTo(2);
	}
	
	@Test
	public void mapTest() {
		// 给所有元素添加一个后缀
		Stream<String> streamSuffix = Stream.of(strs).map(s -> s + "_d");
		assertThat(streamSuffix.findFirst().get()).isEqualTo("aB_d");
		
		// 将所有元素转换成小写
		Stream<String> lowerCaseWords = Stream.of(strs).map(String::toLowerCase);
		assertThat(lowerCaseWords.findFirst().get()).isEqualTo("ab");
		
		Stream<Character> firstChars = Stream.of(strs).map(s -> s.charAt(0));
		assertThat(firstChars.peek(System.out::println).findFirst().get()).isEqualTo('a');
	}
	
	@Test
	public void flatMapTest() {
		// [['a', 'B'], ['a', 'c'], ['B', 'c']]
		Stream<Stream<Character>> stream = Stream.of(strs).map(s -> characterStream(s));
		stream.forEach(System.out::println);
		
		// ['a', 'B', 'a', 'c', 'B', 'c'] 将后面的数组展开并合并到第一个数组中
		Stream<Character> st2 = Stream.of(strs).flatMap(StreamSec3Tests::characterStream);
		st2.forEach(System.out::println);
	}
	
	@Test
	public void concat() {
		Stream<Character> stream = Stream.concat(characterStream("hello"), characterStream("world"));
		stream.forEach(System.out::println);
	}
	
	private static Stream<Character> characterStream(String s) {
		List<Character> result = new ArrayList<>();
		for(char c : s.toCharArray()) {
			result.add(c);
		}
		return result.stream();
	}
	
	@Test
	public void limitTest() {
		Supports.words().stream().limit(20).forEach(System.out::println);
	}
	
	@Test
	public void skipTest() {
		Stream.of(strs).skip(1).forEach(System.out::println);
	}
	
}
