package com.jaf.examples.httpserver;

import com.jaf.examples.httpserver.simple.Request;
import com.jaf.examples.httpserver.simple.Response;

import java.io.IOException;
import java.util.HashMap;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年10月22日
 * @since 1.0
 */
public abstract class HttpServer {
	
	public abstract void start() throws IOException;
	
	protected Response handlerRequest(Request request) {
		request.decode();
//		response.setUri(request.getUri());
//		response.setParams(request.getParams());
		return new Response("", new HashMap<String, Object>());
	}
	
}
