package com.jaf.examples.jdk8.stream;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * 1、Stream 的基本概念介绍及一个简单的列表过滤和统计的示例
 * 
 * 一个 Stream 表面上看和集合很类似，允许你改变和获取数据，但实际上它与集合有很大的区别
 * a) Stream 自己不会存储元素，元素存储在底层的集合中
 * b) Stream 操作不会改变源对象
 * c) Stream 操作符可能是延迟执行的，这意味着它会等到需要结果的时候才执行
 * 
 * 当你使用一个 Stream 时，你会通过三个阶段来建立一个操作流水线
 * a) 创建一个 Stream <code> strs.stream(); </code>
 * b) 在一个或多个步骤中，将初始 Stream 转换为另一个 Stream 的中间操作  <code> stream.filter(s -> s.length() > 2); </code>
 * c) 使用一个终止操作来产生一个结果，该操作会强制它之前的延迟操作立即执行。并且在这之后，该 Stream 就不会被使用了 <code> stream.count(); </code>
 * 
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月17日
 * @since 1.0
 */
public class StreamSec1Tests {
	
	private final List<String> strs = Collections.unmodifiableList(Arrays.asList("aaaa", "bb", "cccc"));
	
	@Test
	public void countTest() {
		// 统计 list 中的字符串长度大于 2 的元素的个数
		// 与老的代码相比，这种方式更简洁，可读性更强
		long count = strs.stream().filter(s -> s.length() > 2).count();
		System.out.println(count);
	}
	
	@Test
	public void parallelCountTest() {
		// 只需要将 stream 改成 parallelStream 方法，就可以让 Stream API 并行执行过滤和统计操作
		long count = strs.parallelStream().filter(s -> s.length() > 2).count();
		System.out.println(count);
	}
	
	@Test
	public void countOldStyleTest() {
		int count = 0;
		for(String str : strs) {
			if(str.length() > 2)
				count++;
		}
		System.out.println(count);
	}
	
}
