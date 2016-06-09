package com.jaf.examples.dubbo;

import java.util.Date;
import java.util.Map;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年6月3日
 * @since 1.0
 */
public class MyCloneableObj {
	
	// https://github.com/kostaskougios/cloning
	// http://www.cnblogs.com/zc22/p/3484981.html

	private Map<String, MyInfo> myInfos;
	private String myId;
	private Date validDate;

	public Map<String, MyInfo> getMyInfos() {
		return myInfos;
	}

	public void setMyInfos(Map<String, MyInfo> myInfos) {
		this.myInfos = myInfos;
	}

	public String getMyId() {
		return myId;
	}

	public void setMyId(String myId) {
		this.myId = myId;
	}

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

}
