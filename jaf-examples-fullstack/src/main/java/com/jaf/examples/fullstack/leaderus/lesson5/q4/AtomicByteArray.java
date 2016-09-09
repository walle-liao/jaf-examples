package com.jaf.examples.fullstack.leaderus.lesson5.q4;

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
public class AtomicByteArray implements ByteArray {
	
	private static final long serialVersionUID = -3761514037304904162L;
	
	private static final Unsafe unsafe;
	private static final int BASE;  // 获取 byte[] 第一个元素的偏移量
	private static final int shift;
	private static final long CURPOS_OFFSET;
	private static final long ARRAYBUSY_OFFSET;
	
	private volatile int curPos = -1;
	private final byte[] array;
	private volatile int arrayBusy;
	
	static {
		try {
			Field singleoneInstanceField = Unsafe.class.getDeclaredField("theUnsafe");
			singleoneInstanceField.setAccessible(true);
			unsafe = (Unsafe) singleoneInstanceField.get(null);
			
			BASE = unsafe.arrayBaseOffset(byte[].class);
			int scale = unsafe.arrayIndexScale(byte[].class);
	        if ((scale & (scale - 1)) != 0)
	            throw new Error("data type scale not a power of two");
	        shift = 31 - Integer.numberOfLeadingZeros(scale);
			
	        Class<?> sk = AtomicByteArray.class;
	        CURPOS_OFFSET = unsafe.objectFieldOffset(sk.getDeclaredField("curPos"));
	        ARRAYBUSY_OFFSET = unsafe.objectFieldOffset(sk.getDeclaredField("arrayBusy"));
		} catch (Exception e) {
			throw new Error(e);
		}
	}
	
    private long itemOffset(int index) {
    	return BASE + ((long) index << shift);
//    	return base + i;  // 因为byte占1位，第i个元素，往后移i位即可
    }
    
    private boolean casArrayBusy() {
        return unsafe.compareAndSwapInt(this, ARRAYBUSY_OFFSET, 0, 1);
    }
    
    private boolean casCurPos(int o, int n) {
    	return unsafe.compareAndSwapInt(this, CURPOS_OFFSET, o, n);
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
			int cp = curPos, index = cp + 1;
			if(index < 0 || index >= array.length) {
				continue ;
			}
			
			if(arrayBusy == 0 && casArrayBusy()) {  // 实现锁的效果
				try {
					unsafe.putByteVolatile(array, itemOffset(index), b);
					casCurPos(cp, index);
					
					System.out.format("append success, curPos : %s, value : %s, array : %s \n", 
							index, b, Arrays.toString(array));
					return ;
				} finally {
					arrayBusy = 0;
				}
			}
		}
	}
	
	@Override
	public byte poll() {
		for(;;) {
			int cp = curPos;
			if(cp < 0) {
				continue ;
			}
			
			byte oldVal;
			if(arrayBusy == 0 && casArrayBusy()) {
				try {
					oldVal = array[cp];
					unsafe.putByteVolatile(array, itemOffset(cp), (byte) 0);
					casCurPos(cp, cp - 1);
					
					System.out.format("poll success, curPos : %s, value : %s, array : %s \n", 
							cp, oldVal, Arrays.toString(array));
				} finally {
					arrayBusy = 0;
				}
				return oldVal;
			}
		}
	}

	@Override
	public byte getAndUpdate(int index, byte update) {
		return 0;
	}
	
}
