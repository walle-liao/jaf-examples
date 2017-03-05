package com.jaf.examples.httpserver.simple;

import com.jaf.examples.httpserver.HttpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static com.jaf.examples.httpserver.common.Constants.SERVER_PORT;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年10月22日
 * @since 1.0
 */
public class SimpleHttpServer extends HttpServer {
	
	@Override
	public void start() throws IOException {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(SERVER_PORT);
			while(true) {
				Socket socket = serverSocket.accept();
				System.out.println("******* open  " + socket.toString() + " connected. *******");

				Request request = new Request(socket.getInputStream());
//				Response response = new Response(socket.getOutputStream());
//				handlerRequest(request, response);
//				response.write();
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

}
