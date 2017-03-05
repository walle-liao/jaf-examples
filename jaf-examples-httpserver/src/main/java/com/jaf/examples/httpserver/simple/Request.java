package com.jaf.examples.httpserver.simple;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.jaf.examples.httpserver.common.Constants.SPLIT;
import static java.util.stream.Collectors.toMap;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年10月23日
 * @since 1.0
 */
public final class Request {
	
	private final InputStream in;

	private String url;  // index.html?a=b
	private String uri;  // index.html
	private String methodType;  // GET,POST,PUT,DELETE
	private final Map<String, Object> params = new HashMap<>();
	
	public Request(InputStream in) {
		this.in = in;
	}
	
	public Request decode() {
		try {
			LineNumberReader reader = new LineNumberReader(new InputStreamReader(in));
			StringBuilder requestStr = new StringBuilder();
			String lineInput;
			boolean firstLine = true;
			while((lineInput = reader.readLine()) != null) {
				if(firstLine) {
					this.url = lineInput.substring(lineInput.indexOf("/") + 1, lineInput.lastIndexOf(' '));
					this.methodType = lineInput.substring(0, lineInput.indexOf(' '));
					firstLine = false;
				}

				requestStr.append(lineInput).append(SPLIT);
				if(lineInput.isEmpty())
					break;
			}
			System.out.println(requestStr);

			if("POST".equals(this.methodType)) {

			} else if("GET".equals(this.methodType)) {
				parseUriAndParams();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	protected void parseUriAndParams() {
		if(StringUtils.isEmpty(url))
			return;

		String[] urls = url.split("\\?");
		this.uri = urls[0];
		if(urls.length > 1) {
			Map<String, String> params = Stream.of(urls[1].split("&"))
					.map(p -> p.split("="))
					.collect(toMap(x -> x[0], x -> x[1]));
			this.params.putAll(params);
		}
	}

	public String getUri() {
		return uri;
	}

	public Map<String, Object> getParams() {
		return params;
	}
}
