package com.jaf.examples.redis.performance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月23日
 * @since 1.0
 */
public class PerformanceTestBase {
	
	/**
	 * 
	 * @param job 执行的具体任务
	 * @param concurrentSize 并发的级别，多少个线程同时执行
	 * @param perCount 每个线程执行多少次操作
	 * @throws InterruptedException 
	 */
	public static void executeTest(Job job, int concurrentSize, long perCount) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(concurrentSize);
		CountDownLatch latch = new CountDownLatch(concurrentSize);
		for(int i = 0; i < concurrentSize; i++) {
			executorService.submit(new JobWrapper(job, latch, perCount));
		}
		
		latch.await();
		executorService.shutdown();
	}
	
}
