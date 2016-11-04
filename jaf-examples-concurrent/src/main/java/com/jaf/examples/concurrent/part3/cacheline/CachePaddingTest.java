package com.jaf.examples.concurrent.part3.cacheline;

import sun.misc.Contended;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年11月4日
 * @since 1.0
 */
public class CachePaddingTest {

	
	@Contended
	private class VolatileLong {
		
		private volatile long v;
		
		
	}
	
	
}
