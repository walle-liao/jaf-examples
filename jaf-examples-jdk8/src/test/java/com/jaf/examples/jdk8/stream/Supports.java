package com.jaf.examples.jdk8.stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月26日
 * @since 1.0
 */
public class Supports {
	
	public static List<String> words() {
		List<String> words = new ArrayList<String>();
		try {
			String pathName = Supports.class.getClassLoader().getResource("WindsOfWar.txt").getPath();
			String contents = new String(Files.readAllBytes(Paths.get(pathName.replaceFirst("/", ""))), StandardCharsets.UTF_8);
			words = Arrays.asList(contents.split("[\\P{L}]+"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return words;
	}
	
}
