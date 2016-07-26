package com.jaf.examples.jdk8.lambda;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月18日
 * @since 1.0
 */
public class LambadExamples {
	
	public static void main(String[] args) throws IOException {
//		Files.list(Paths.get("d:/temp")).filter(Files::isDirectory).forEach(System.out::println);
//		Files.list(Paths.get("."))
//	    .filter(Files::isDirectory)
//	    .forEach(System.out::println);
		
//		List<File> dirs = new ArrayList<>();
//		dirs.addAll(Arrays.asList(listDirectory(new File("d:/temp"))));
//		dirs.forEach(x -> System.out.println(x.getName()));
		
		listDirectory();
	}
	
	public static void listDirectory() {
//		List<File> files = Stream.of(new File("d:/temp").listFiles(File::isDirectory))
//				.flatMap(file -> file.listFiles(File::isDirectory) != null ? Stream.of(file.listFiles(File::isDirectory)) : Stream.of(file))
//				.collect(Collectors.toList());
//		files.forEach(f -> System.out.println(f.getName()));
		
		List<File> files = Stream.of(new File("d:/temp").listFiles())
				.flatMap(file -> file.listFiles() != null ? Stream.of(file.listFiles()) : Stream.of(file))
				.collect(Collectors.toList());
		files.forEach(f -> System.out.println(f.getName()));
	}
	
}
