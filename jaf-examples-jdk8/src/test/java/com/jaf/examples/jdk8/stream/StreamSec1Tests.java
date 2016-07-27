package com.jaf.examples.jdk8.stream;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.Test;

import com.google.common.collect.Lists;

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
	
	@Test
	public void countTest() {
		List<Integer> nums = Lists.newArrayList(1, null, 3, 4, null, 6);
		nums.stream().filter(x -> x != null).count();
		
		// 统计 list 中的字符串长度大于 2 的元素的个数
		// 与老的代码相比，这种方式更简洁，可读性更强
		long count = Supports.words().stream().filter(s -> s.length() > 5).count();
		System.out.println(count);
	}
	
	@Test
	public void parallelCountTest() {
		// 只需要将 stream 改成 parallelStream 方法，就可以让 Stream API 并行执行过滤和统计操作
		long count = Supports.words().parallelStream().filter(s -> s.length() > 5).count();
		System.out.println(count);
		
		// 或者可以这样  .stream().parallel() 将这个流转换成并行流
		// 只要在终止方法之前调用了 parallel() 方法都可以将一个串行流转换成一个并行流
		// Supports.words().stream().parallel().filter(s -> s.length() > 5).count();
	}
	
	@Test(expected = IllegalStateException.class)
	public void streamTest() {
		Stream<String> stream = Supports.words().stream();
		long count = stream.filter(s -> s.startsWith("a")).count();
		System.out.println(count);
		
		// 当一个流对象碰到结束操作（如上面的 count 操作）后，这个流就不能再被使用了
		// 这里将抛出 IllegalStateException
		stream.forEach(System.out::println);
		
		// 如果想要实现上面的效果，既要打印出来每个元素（方便调试），又要进行统计
		// 这里 foEach 和 count 两个操作都是终止操作，如何实现在一个流上面进行两个终止操作
		// 可以使用 peek 方法，复制一个与原始流一样的流，见下面的 peekTest
	}
	
	@Test
	public void peekTest() {
		Stream<String> stream = Supports.words().stream();
		stream.filter(s -> s.startsWith("a")).peek(System.out::println).count();
		
		// 注意这两个的差别，上面的是对过滤后的流进行复制，而下面的是对过滤前的流进行复制
		// stream.peek(System.out::println).filter(s -> s.startsWith("a")).count(); 
	}
	
	@Test
	public void streamSupplierTest() {
		// 通过 lambda 表达式返回一个函数接口
		Supplier<Stream<String>> streamSupplier = () -> Supports.words().stream().filter(s -> s.startsWith("a"));
		
		// 在这个函数接口上每次调用 get 方法，都将获得新的流
		System.out.println(streamSupplier.get().count());;
		System.out.println(streamSupplier.get().anyMatch(x -> x.equals("an")));;
	}
	
	@Test
	public void countOldStyleTest() {
		List<String> words = Supports.words();
		int count = 0;
		for(String str : words) {
			if(str.length() > 5)
				count++;
		}
		System.out.println(count);
	}
	
}
