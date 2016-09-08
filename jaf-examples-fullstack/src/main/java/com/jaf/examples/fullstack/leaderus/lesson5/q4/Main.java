package com.jaf.examples.fullstack.leaderus.lesson5.q4;

import java.util.Arrays;

import org.apache.commons.lang3.RandomUtils;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年9月8日
 * @since 1.0
 */
public class Main {
	
	public static void main(String[] args) {
		final AtomicByteArray byteArray = new AtomicByteArray(3);
//		List<Thread> ts = IntStream.range(0, 12).mapToObj(x -> {
//			return new Thread(new AppendJob(byteArray));
//		}).peek(Thread::start).collect(toList());
//		
//		ts.stream().forEach(t -> {
//			try {
//				t.join();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		});
		
		byteArray.append((byte) -5);
		byteArray.append((byte) 15);
		byteArray.append((byte) 25);
//		byteArray.append((byte) 35);
		
		System.out.println(byteArray.getCurPos());
		System.out.println(Arrays.toString(byteArray.getArray()));
	}
	
	
	static class AppendJob implements Runnable {
		
		private final AtomicByteArray byteArray;
		
		private AppendJob(AtomicByteArray byteArray) {
			this.byteArray = byteArray;
		}

		@Override
		public void run() {
			byte b = RandomUtils.nextBytes(1)[0];
			byteArray.append(b);
		}
		
	}
	
	
}
