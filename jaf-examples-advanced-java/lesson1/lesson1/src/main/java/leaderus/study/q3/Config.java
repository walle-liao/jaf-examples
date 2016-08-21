package leaderus.study.q3;

/**
 * TODO
 * 
 * @author liaozhicheng
 * @date 2016年8月11日
 * @since 1.0
 */
class Config {

	static final int TOTAL_ROWS = 10000000;
	static final String SPLIT_CHAR = ",";
	static final String DB_FILE_NAME = "d:/temp/persondb.txt";
	
	public static boolean matchRegex(Person p) {
		return p.name.matches("^[a|b|c|d|e]\\w*") 
				&& ((p.age >= 6 && p.age <= 11) || (p.age >= 16 && p.age <= 18));
	}
	
	public static boolean match(Person p) {
		char fc = p.name.charAt(0);
		int age = p.age;
		return (fc == 'a' || fc == 'b' || fc == 'c' || fc == 'd' || fc == 'e')
				&& ((age >= 6 && age <= 11) || (age >= 16 && age <= 18));
	}
	
}
