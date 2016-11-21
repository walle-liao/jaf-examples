package com.jaf.examples.expert.lesson6.q1;

import com.jaf.examples.expert.common.utils.UnsafeUtils;

import sun.misc.Unsafe;

import java.util.ArrayList;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年11月20日
 * @since 1.0
 */
public class ArrayOffset {

    public static void main(String[] args) {
        Unsafe unsafe = UnsafeUtils.getUnsafe();
        System.out.println(unsafe.arrayBaseOffset(long[].class));
        System.out.println(unsafe.arrayBaseOffset(int[].class));
        System.out.println(unsafe.arrayBaseOffset(Object[].class));
        System.out.println(unsafe.arrayBaseOffset(byte[].class));

        System.out.println("==================");

        System.out.println("array scale "+unsafe.arrayIndexScale(ArrayList[].class));  // 4
        System.out.println("array scale "+unsafe.arrayIndexScale(MyObj[].class));  // 4
        System.out.println("array scale "+unsafe.arrayIndexScale(long[].class));  // 8
        System.out.println("array scale "+unsafe.arrayIndexScale(boolean[].class));  // 1
        System.out.println("array scale "+unsafe.arrayIndexScale(Boolean[].class));  // 4
        System.out.println("array scale "+unsafe.arrayIndexScale(int[].class));  // 4
        System.out.println("array scale "+unsafe.arrayIndexScale(Integer[].class));  // 4
        System.out.println("array scale "+unsafe.arrayIndexScale(byte[].class));   // 1
    }

}
