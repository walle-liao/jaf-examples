package com.jaf.examples.httpserver;

import java.io.IOException;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年10月22日
 * @since 1.0
 */
public abstract class HttpServer {
	
	public abstract void start() throws IOException;
	
	protected abstract Response doService(Request request);
	
}
