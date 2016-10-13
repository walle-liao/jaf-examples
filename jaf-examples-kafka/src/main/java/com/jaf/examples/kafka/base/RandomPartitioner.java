package com.jaf.examples.kafka.base;

import kafka.producer.Partitioner;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年10月12日
 * @since 1.0
 */
public class RandomPartitioner implements Partitioner {

	@Override
	public int partition(Object arg0, int arg1) {
		return 0;
	}
	
}
