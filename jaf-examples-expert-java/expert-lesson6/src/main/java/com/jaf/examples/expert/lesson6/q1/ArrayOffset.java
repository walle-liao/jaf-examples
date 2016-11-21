package com.jaf.examples.expert.lesson6.q1;

import com.jaf.examples.expert.common.utils.UnsafeUtils;

import sun.misc.Unsafe;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年11月20日
 * @since 1.0
 */
public class ArrayOffset {
	
	int[] intArr;
	long[] longArr;
	Object[] objArr;
	
	
	public static void main(String[] args) {
		Unsafe unsafe = UnsafeUtils.getUnsafe();
		System.out.println(unsafe.arrayBaseOffset(int[].class));
		System.out.println(unsafe.arrayBaseOffset(long[].class));
		System.out.println(unsafe.arrayBaseOffset(Object[].class));
		System.out.println(unsafe.arrayBaseOffset(byte[].class));
	}
	
}
