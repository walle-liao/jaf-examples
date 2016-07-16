package com.jaf.examples.jdk8.lambda;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月16日
 * @since 1.0
 */
class Worker implements Runnable {

	@Override
	public void run() {
		for(int i = 0; i < 1000; i++) {
			System.out.println(i);
		}
	}
	
}
