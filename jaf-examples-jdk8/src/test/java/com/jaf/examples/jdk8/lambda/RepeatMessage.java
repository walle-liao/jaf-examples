package com.jaf.examples.jdk8.lambda;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月16日
 * @since 1.0
 */
public class RepeatMessage {
	
	public static void execute(String text, int count) {
		Runnable r = () -> {
			for(int i = 0; i < count; i++) {
				System.out.println(text);
			}
		};
		new Thread(r).start();
	}
	
}
