package com.jaf.examples.fullstack.leaderus.lesson5;

import java.util.stream.LongStream;

import com.jaf.examples.fullstack.leaderus.lesson5.AtomicLongArrayCounter.IndexHolder;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年9月9日
 * @since 1.0
 */
public class LongArrayCounter implements MyCounter {
	
	private long[] array;
	
	public LongArrayCounter(int length) {
		array = new long[length];
	}

	@Override
	public void incr() {
		int index = IndexHolder.get();
		long o = array[index];
		array[index] = o + 1;
	}

	@Override
	public long getCurValue() {
		return LongStream.of(array).sum();
	}

}
