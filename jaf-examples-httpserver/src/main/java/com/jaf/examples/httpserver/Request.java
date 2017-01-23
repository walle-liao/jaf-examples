package com.jaf.examples.httpserver;

import static java.util.stream.Collectors.toMap;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.jaf.examples.httpserver.common.Constants;
import com.jaf.examples.httpserver.simple.SimpleResponse;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年10月23日
 * @since 1.0
 */
public abstract class Request {
	
	private final String request;
	
	private String resourceUri;
	private final Map<String, Object> params = new HashMap<>();
	
	private String url;
	private String methodType;
	
	private Response response;
	
	public Request(String request) {
		this.request = request;
	}
	
	public Request decode() {
		try (LineNumberReader in = new LineNumberReader(new StringReader(request))) {
			Pattern pattern = Pattern.compile(Constants.REQUEST_HEAD_FIRST_LINE_PATTERN);
			String lineInput;
			while((lineInput = in.readLine()) != null) {
				Matcher matcher = pattern.matcher(lineInput);
				if(matcher.matches()) {
					url = lineInput.substring(lineInput.indexOf("/") + 1, lineInput.lastIndexOf(' '));
					methodType = matcher.group(1);
				}
				
				if(lineInput.isEmpty()) {
					if("POST".equals(methodType)) {
						// do post
					} else {
//						doGet(url, writer);  // 默认都使用GET方式
						this.response = buildGetResponse();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	protected Response buildGetResponse() {
		if(url == null || "".equals(url))
			return null;
		
		String[] urls = url.split("\\?");
		this.resourceUri = urls[0];
		if(urls.length > 1) {
			Map<String, String> params = Stream.of(urls[1].split("&"))
					.map(p -> p.split("="))
					.collect(toMap(x -> x[0], x -> x[1]));
			this.params.putAll(params);
		}
		return new SimpleResponse(this);
	}
	
	public String getResourceUri() {
		return resourceUri;
	}

	public Response getResponse() {
		return response;
	}
	
	
}
