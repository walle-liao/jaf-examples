package com.jaf.examples.jdk8.defaultmethod;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月16日
 * @since 1.0
 */
public interface Named {
	
	default String getName() {
		return "Named";
	}
	
}
