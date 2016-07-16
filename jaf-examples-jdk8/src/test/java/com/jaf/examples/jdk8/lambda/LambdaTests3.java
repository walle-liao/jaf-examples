package com.jaf.examples.jdk8.lambda;

import org.junit.Test;

/**
 * 1、lambda 表达式中变量的作用域
 * 2、Java 中的闭包
 * 3、lambda this 的指向（lambda 表单式的方法体与嵌套代码块有着相同的作用域）
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月16日
 * @since 1.0
 */
public class LambdaTests3 {
	
	
	/**
	 * 
	 *	public static void execute(String text, int count) {
	 *		Runnable r = () -> {
	 *			for(int i = 0; i < count; i++) {
	 *				System.out.println(text);
	 *			}
	 *		};
	 *		new Thread(r).start();
	 *	}
	 * 
	 * lambda表达式中的变量 count, text，并没有在 lambda 中定义，而是方法  execute 的参数变量
	 * 如果思考下就会发现 lambda 表达式可能会在 execute 方法返回之后才运行，此时参数变量已消失，那么 lambda 如何保存变量 count 和 text 的值呢
	 * 你可以将 lambda 表达式想象成一个只包含一个方法的对象，而 lambda 引用的外部变量已经被 lambda 表达式捕获，这些被捕获的变量会被复制到对象的实例变量中
	 * 此种特性就是 Java 中的闭包，Java 中 lambda 表达式就是闭包。事实上，Java 中的内部类一直都是闭包
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void repeatMessageTest() throws InterruptedException {
		RepeatMessage.execute("hello world", 10);
		Thread.sleep(50);
	}
	
	
	@Test
	public void thisReferenceTest() {
		Application application = new Application();
		application.doWork();
	}
	
	
	class Application {
		
		public void doWork() {
			
			/**
			 * lambda 表单式的方法体与嵌套代码块有着相同的作用域
			 * 因此此处 lambda 表达式中的 this 指向的是 doWork 方法中的 this，即 Application 对象
			 */
			Runnable r = () -> {
				System.out.println(this.toString());  // this is application
			};
			new Thread(r).start();
			
			
			/**
			 * 正常的匿名类的写法，此时的 this 很显然是指向 Runnable 对象
			 */
			new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println(this.toString());  // this is runnable
				}
				@Override
				public String toString() {
					return "this is runnable";
				}
			}).start();
		}

		@Override
		public String toString() {
			return "this is application";
		}
		
	}
	
}
