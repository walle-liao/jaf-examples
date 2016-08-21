package leaderus.study.q3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javolution.util.FastTable;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.google.common.collect.ArrayTable;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年8月11日
 * @since 1.0
 */
class MyPersonCaclByArrayTable {

	private static final FastTable<Person> persons = new FastTable<Person>();
	
	public static void main(String[] args) throws IOException {
		groupBy();
	}
	
	public static void arrayTable() throws IOException {
		List<Integer> ages = new ArrayList<Integer>();
		for(int i = 1; i <= 18; i++) {
			ages.add(i);
		}
		
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		List<Character> chapters = new ArrayList<Character>();
		for(int i = 0; i < str.length(); i++) {
			chapters.add(str.charAt(i));
		}
		
		long start = System.currentTimeMillis();
		ArrayTable<Integer, Character, List<Person>> personTable = ArrayTable.create(ages, chapters);
		File dbFile = new File(Config.DB_FILE_NAME);
		LineIterator it = FileUtils.lineIterator(dbFile, "UTF-8");
		while(it.hasNext()) {
			String line = it.next();
			String[] props = line.split(",");
			Person person = new Person(Integer.valueOf(props[0]), props[1], Integer.valueOf(props[2]));
			char fc = person.name.charAt(0);
			List<Person> persons = personTable.get(person.age, fc);
			if(persons == null) {
				persons = new LinkedList<Person>();
				personTable.put(person.age, person.name.charAt(0), persons);
			}
			persons.add(person);
		}
		System.out.println("load data elapsed time : " + (System.currentTimeMillis() - start));
		
		System.out.println(personTable.get(6, 'a').size());
	}
	
	
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
	
	public static void groupBy() throws IOException {
		MyPersonCaclByArrayTable.loadFromDb();
		
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
	
	
}
