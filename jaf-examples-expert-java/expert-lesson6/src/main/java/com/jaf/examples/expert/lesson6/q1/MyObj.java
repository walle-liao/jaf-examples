package com.jaf.examples.expert.lesson6.q1;


import com.jaf.examples.expert.common.utils.UnsafeUtils;

import sun.misc.Unsafe;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年11月17日
 * @since 1.0
 */
public class MyObj {

	int a;  // 12
	byte b;  // 32
	int c;   // 24
	boolean d;  // 33
	float e;  // 28
	long l;  // 16
	Object obj;  // 36
	boolean f; // 34
	Object obj1;  // 40
	
	public static void main(String[] args) throws NoSuchFieldException, SecurityException {
		Unsafe unsafe = UnsafeUtils.getUnsafe();
		System.out.format("a: %s, b: %s, c: %s, d: %s, e: %s, l: %s, obj: %s, f: %s, obj1: %s", 
				unsafe.objectFieldOffset(MyObj.class.getDeclaredField("a")),
				unsafe.objectFieldOffset(MyObj.class.getDeclaredField("b")),
				unsafe.objectFieldOffset(MyObj.class.getDeclaredField("c")),
				unsafe.objectFieldOffset(MyObj.class.getDeclaredField("d")),
				unsafe.objectFieldOffset(MyObj.class.getDeclaredField("e")),
				unsafe.objectFieldOffset(MyObj.class.getDeclaredField("l")),
				unsafe.objectFieldOffset(MyObj.class.getDeclaredField("obj")),
				unsafe.objectFieldOffset(MyObj.class.getDeclaredField("f")),
				unsafe.objectFieldOffset(MyObj.class.getDeclaredField("obj1"))
		);
	}

}
