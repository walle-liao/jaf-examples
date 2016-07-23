package com.jaf.examples.redis.performance;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月23日
 * @since 1.0
 */
class JobWrapper implements Runnable {
	
	private static final Logger LOG = Logger.getLogger(JobWrapper.class);
	
	private final Job job;
	private final CountDownLatch latch;
	private final long totalCount;
	
	public JobWrapper(Job job, CountDownLatch latch, long totalCount) {
		this.job = job;
		this.latch = latch;
		this.totalCount = totalCount;
	}
	
	@Override
	public void run() {
		String threadName = Thread.currentThread().getName();
		try {
			long startTime = System.currentTimeMillis();
			long count = 0, st = startTime;
			for (long i = 0; i < totalCount; i++) {
				long currentTime = System.currentTimeMillis();
				if (currentTime - startTime >= 1000) {
					startTime = currentTime;
					LOG.debug(new StringBuilder().append(threadName).append(" tps: ").append(i - count).toString());
					count = i;
				}
				
				job.execute(i, threadName);
			}
			
			long endTime = System.currentTimeMillis();
			LOG.debug(new StringBuilder().append(threadName).append(" exit. use total time : ").append(endTime - st));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			latch.countDown();
		}
	}
	
}
