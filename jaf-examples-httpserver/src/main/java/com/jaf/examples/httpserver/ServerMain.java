package com.jaf.examples.httpserver;

import java.io.IOException;

import com.jaf.examples.httpserver.simple.NioHttpServer;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年10月23日
 * @since 1.0
 */
public class ServerMain {
	
	public static void main(String[] args) throws IOException {
		HttpServer server = new NioHttpServer();
		server.start();
	}
	
}
