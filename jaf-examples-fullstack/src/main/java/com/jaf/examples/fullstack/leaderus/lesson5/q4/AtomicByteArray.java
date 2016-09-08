package com.jaf.examples.fullstack.leaderus.lesson5.q4;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;

import sun.misc.Unsafe;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年9月8日
 * @since 1.0
 */
public class AtomicByteArray implements Serializable {
	
	private static final long serialVersionUID = -3761514037304904162L;
	
	private static final Unsafe unsafe;
	private static final int base;  // 获取 byte[] 第一个元素的偏移量
//	private static final int shift;
	private static final long curPosOffset;
	
	private volatile int curPos;
	
	private final byte[] array;
	
	static {
		try {
			Field singleoneInstanceField = Unsafe.class.getDeclaredField("theUnsafe");
			singleoneInstanceField.setAccessible(true);
			unsafe = (Unsafe) singleoneInstanceField.get(null);
			
			base = unsafe.arrayBaseOffset(byte[].class);
//			int scale = unsafe.arrayIndexScale(byte[].class);
//	        if ((scale & (scale - 1)) != 0)
//	            throw new Error("data type scale not a power of two");
//	        shift = 31 - Integer.numberOfLeadingZeros(scale);
			
			curPosOffset = unsafe.objectFieldOffset(AtomicByteArray.class.getDeclaredField("curPos"));
		} catch (Exception e) {
			throw new Error(e);
		}
	}
	
	private long checkedByteOffset(int i) {
        if (i < 0 || i >= array.length)
            throw new IndexOutOfBoundsException("index " + i);

        return byteOffset(i);
    }

    private static long byteOffset(int i) {
//    	return ((long) i << shift) + base;  // 因为byte占1位，第i个元素，往后移i位即可
    	return base + i;
    }

    public int getCurPos() {
    	return this.curPos;
    }
    
    public byte[] getArray() {
    	return this.array;
    }
    
	
	public AtomicByteArray(int length) {
		this.array = new byte[length];
	}
	
	public void append(byte b) {
		for(;;) {
			int index = curPos;
			long itemOffset = checkedByteOffset(index);
			
			if(unsafe.compareAndSwapInt(this, curPosOffset, index, index + 1)) {
				unsafe.putByte(array, itemOffset, b);
//				unsafe.compareAndSwapObject(array, itemOffset, 0, b)
				System.out.format("append data success, index : %s, value : %s, array : %s \n", index, b, Arrays.toString(array));
				return ;
			}
		}
	}
	
	public byte pop() {
		
		return 0;
	}
	
}
