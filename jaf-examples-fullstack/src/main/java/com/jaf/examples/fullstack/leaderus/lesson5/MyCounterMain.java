package com.jaf.examples.fullstack.leaderus.lesson5;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.IntStream;

import com.jaf.examples.fullstack.leaderus.lesson5.AtomicLongArrayCounter.IndexHolder;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年9月8日
 * @since 1.0
 */
public class MyCounterMain {
	
	public static void main(String[] args) {
		final int threadCount = 10;
		final MyCounter counter = new ReentrantLockCounter();
		
		long start = System.currentTimeMillis();
		
		List<Thread> ts = IntStream.range(0, threadCount)
		.mapToObj(index -> {
			return new Thread(new CounterJob(counter, index));
		})
		.peek(Thread::start)
		.collect(toList());
		
		ts.forEach(t -> {
			try {
				t.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		long elapsedTime = System.currentTimeMillis() - start;
		System.out.format("elapsed time : %s ms, current value : %s", elapsedTime, counter.getCurValue());
	}
	
	private static class CounterJob implements Runnable {

		private final MyCounter counter;
		private final int index;
		
		CounterJob(MyCounter counter, int index) {
			this.counter = counter;
			this.index = index;
		}
		
		@Override
		public void run() {
			if(counter instanceof AtomicLongArrayCounter) {
				new IndexHolder(index);
			}
			
			IntStream.range(0, 1000000).forEach(x -> counter.incr());
		}
		
	}

}
