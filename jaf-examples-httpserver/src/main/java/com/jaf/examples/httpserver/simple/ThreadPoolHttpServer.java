package com.jaf.examples.httpserver.simple;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.jaf.examples.httpserver.common.Constants.SERVER_PORT;

/**
 * 基于线程池的实现
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年10月23日
 * @since 1.0
 */
public class ThreadPoolHttpServer extends SimpleHttpServer {

	@Override
	public void start() throws IOException {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(SERVER_PORT);
			while(true) {
				Socket socket = serverSocket.accept();
				System.out.println("******* open  " + socket.toString() + " connected. *******");

				ServiceTask task = new ServiceTask(socket.getInputStream(), socket.getOutputStream());
				executorService.execute(task);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

	private class ServiceTask implements Runnable {

		private final InputStream in;
		private final OutputStream out;

		ServiceTask(InputStream in, OutputStream out) {
			this.in = in;
			this.out = out;
		}

		@Override
		public void run() {
			Request request = new Request(in);
//			Response response = new Response(out);
//			handlerRequest(request, response);
//			response.write();
		}
	}
	
}
