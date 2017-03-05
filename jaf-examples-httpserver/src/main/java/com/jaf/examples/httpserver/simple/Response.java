package com.jaf.examples.httpserver.simple;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.jaf.examples.httpserver.common.Constants.SPLIT;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年10月23日
 * @since 1.0
 */
public final class Response {
	
//	private final OutputStream out;

	private String uri;
	private Map<String, Object> params = new HashMap<>();

	public Response(String uri, Map<String, Object> params) {
		this.uri = uri;
		this.params = params;
	}

//	public Response(OutputStream out) {
//		this.out = out;
//	}

	public void write(OutputStream out) {
		try {
			byte[] responseBytes = buildResponseBytes();
			out.write(responseBytes);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private byte[] buildResponseBytes() throws IOException {
		if(StringUtils.isEmpty(uri))
			throw new IllegalArgumentException("找不到对应的 uri 路径");

		String header;
		byte[] data;
		if(uri.endsWith(".png")) {
			data = getResourceAsBytes(uri);
			header = buildResponseHeader("image/jpeg", data.length);
		} else {
			String body = getResourceAsString(uri);
			data = body.getBytes();
			header = buildResponseHeader("text/html; charset=utf-8", data.length);
		}

		byte[] headerBytes = header.getBytes();
		byte[] responseBytes = new byte[headerBytes.length + data.length];
		System.arraycopy(headerBytes, 0, responseBytes, 0, headerBytes.length);
		System.arraycopy(data, 0, responseBytes, headerBytes.length, data.length);
		return responseBytes;
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

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}