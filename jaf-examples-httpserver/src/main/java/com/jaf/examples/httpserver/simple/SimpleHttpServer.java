package com.jaf.examples.httpserver.simple;

import static com.jaf.examples.httpserver.common.Constants.REQUEST_HEAD_FIRST_LINE_PATTERN;
import static com.jaf.examples.httpserver.common.Constants.SERVER_PORT;
import static com.jaf.examples.httpserver.common.Constants.SPLIT;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.jaf.examples.httpserver.HttpServer;
import com.jaf.examples.httpserver.Request;
import com.jaf.examples.httpserver.Response;

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
				
				try (LineNumberReader reader = new LineNumberReader(new InputStreamReader(socket.getInputStream()))) {
					String lineInput;
					StringBuilder requestStr = null;
					while((lineInput = reader.readLine()) != null) {
						System.out.println(lineInput);
						if(lineInput.matches(REQUEST_HEAD_FIRST_LINE_PATTERN)) {
							requestStr = new StringBuilder();
						}
						requestStr.append(lineInput).append(SPLIT);
						
						if(lineInput.isEmpty()) {
							Response response = this.doService(new SimpleRequest(requestStr.toString()));
							this.doWrite(socket.getOutputStream(), response.getResponseBytes());
						}
					}
				}
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

	@Override
	protected Response doService(Request request) {
		return request.decode().getResponse();
	}
	
	protected void doWrite(OutputStream writer, byte[] responseBytes) throws IOException {
		writer.write(responseBytes);
		writer.flush();
	}
	
}
