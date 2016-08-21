package leaderus.study.q3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年8月11日
 * @since 1.0
 */
class MyPersonCaclByArrayList {
	
	private static final List<Person> persons = new ArrayList<Person>();
	
	public static void loadFromDb() throws IOException {
		long start = System.currentTimeMillis();
		
		File dbFile = new File(Config.DB_FILE_NAME);
		LineIterator it = FileUtils.lineIterator(dbFile, "UTF-8");
		while(it.hasNext()) {
			String line = it.next();
			String[] props = line.split(",");
			Person person = new Person(Integer.valueOf(props[0]), props[1], Integer.valueOf(props[2]));
			persons.add(person);
		}
		
		System.out.println("load data elapsed time : " + (System.currentTimeMillis() - start));
	}
	
	public static void main(String[] args) throws IOException {
		loadFromDb();
		
		groupBy();
//		groupByConcurrent();
	}
	
	
	public static void groupBy() {
		long start = System.currentTimeMillis();
		Map<String, Long> result = persons.stream()
				.filter(Config::match)
				.collect(
					Collectors.groupingBy(
							p -> (p.age >= 6 && p.age <= 11 ? "[6-11]-" : "[16-18]-") + p.name.charAt(0), 
							Collectors.counting()
					)
				);
		long end = System.currentTimeMillis();
		System.out.println(result);
		System.out.println("elapsed time : " + (end - start));
	}
	
	public static void groupByConcurrent() {
		long start = System.currentTimeMillis();
		ConcurrentMap<String, Long> result = persons.parallelStream()
				.filter(Config::match)
				.collect(
					Collectors.groupingByConcurrent(
							p -> (p.age >= 6 && p.age <= 11 ? "[6-11]-" : "[16-18]-") + p.name.charAt(0), 
							Collectors.counting()
					)
				);
		long end = System.currentTimeMillis();
		System.out.println(result);
		System.out.println("elapsed time : " + (end - start));
	}

}
