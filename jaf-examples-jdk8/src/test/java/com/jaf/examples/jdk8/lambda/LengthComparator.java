package com.jaf.examples.jdk8.lambda;

import java.util.Comparator;

/**
 * 根据字符串的长度来排序字符串
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月16日
 * @since 1.0
 */
class LengthComparator implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		return Integer.compare(o1.length(), o2.length());
	}
	
}
