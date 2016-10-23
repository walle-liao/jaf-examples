package com.jaf.examples.httpserver;

import static com.jaf.examples.httpserver.common.Constants.SPLIT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.jaf.examples.httpserver.simple.SimpleHttpServer;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年10月23日
 * @since 1.0
 */
public abstract class Response {
	
	private final Request request;
	
	private byte[] responseBytes;
	
	public Response(Request request) {
		this.request = request;
		this.buildResponseBytes();
	}
	
	private void buildResponseBytes() {
		try {
			String header = "";
			byte[] data = new byte[0];
			String requestPage = request.getResourceUri();
			if(requestPage.endsWith(".png")) {
				data = getResourceAsBytes(requestPage);
				header = buildResponseHeader("image/jpeg", data.length);
			} else {
				String body = getResourceAsString(requestPage);
				data = body.getBytes();
				header = buildResponseHeader("text/html; charset=utf-8", data.length);
			}
			
			byte[] headerBytes = header.getBytes();
			this.responseBytes = new byte[headerBytes.length + data.length];
			System.arraycopy(headerBytes, 0, responseBytes, 0, headerBytes.length);
			System.arraycopy(data, 0, responseBytes, headerBytes.length, data.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected String buildResponseHeader(String contentType, int contentLength) {
		StringBuilder responseHeader = new StringBuilder();
		SimpleDateFormat gmtFrmt = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		responseHeader.append("HTTP/1.1 200 OK").append(SPLIT)  // 状态行
			.append("Server: Tengine/2.1.1").append(SPLIT)   // 响应头部
			.append("Date: ").append(gmtFrmt.format(new Date())).append(SPLIT)
			.append("Content-Type: ").append(contentType).append(SPLIT)
			.append("Connection: close").append(SPLIT)  // 是否复用链接(keep-alive / close)
			.append("Content-Length: ").append(contentLength).append(SPLIT)
			.append(SPLIT);
		return responseHeader.toString();
	}
	
	protected String getResourceAsString(String requestPage) throws IOException {
		StringBuilder html = new StringBuilder();
		try(InputStream is = SimpleHttpServer.class.getClassLoader().getResourceAsStream(requestPage)) {
			if(is != null) {
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
					reader.lines().map(line -> line.trim()).forEach(line -> html.append(line));
				}
			} else {
				html.append("404! Page not found.");
			}
		}
		return html.toString();
	}
	
	protected byte[] getResourceAsBytes(String requestPage) throws IOException {
		try(InputStream is = SimpleHttpServer.class.getClassLoader().getResourceAsStream(requestPage)) {
			if(is == null)
				return new byte[0];
			
			byte[] bs = new byte[is.available()];
			is.read(bs);
			return bs;
		}
	}
	
	
	public byte[] getResponseBytes() {
		return this.responseBytes;
	}
	
}
