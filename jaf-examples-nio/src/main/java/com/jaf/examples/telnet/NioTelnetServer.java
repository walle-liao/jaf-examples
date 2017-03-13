package com.jaf.examples.telnet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by walle on 2017/3/12.
 */
public class NioTelnetServer {

    public static void main(String[] args) throws IOException {
        NioTelnetServer.start();
    }


    public static void start() throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        InetSocketAddress address = new InetSocketAddress(9090);
        serverSocketChannel.socket().bind(address);
        System.out.println("telnet server start at " + address);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int selectNum = selector.select();
            System.out.println("selectNum : " + selectNum);

            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while(keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if(key.isAcceptable()) {
                    executorService.execute(new AcceptEventHandler(selector, key));
                } else if(key.isReadable()) {
                    executorService.execute(new ReadEventHandler(key));
                }
                keyIterator.remove();
            }
        }
    }

    private static class AcceptEventHandler implements Runnable {

        private final Selector selector;
        private final SelectionKey selectionKey;

        private AcceptEventHandler(Selector selector, SelectionKey selectionKey) {
            this.selector = selector;
            this.selectionKey = selectionKey;
        }

        @Override
        public void run() {
            try {
                ServerSocketChannel serverChannel = (ServerSocketChannel) selectionKey.channel();
                SocketChannel socketChannel = serverChannel.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);
                socketChannel.write(ByteBuffer.wrap("welcome telnet server ... \r\n".getBytes(Charset.forName("UTF-8"))));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static class ReadEventHandler implements Runnable {

        private final SelectionKey selectionKey;

        private ReadEventHandler(SelectionKey selectionKey) {
            this.selectionKey = selectionKey;
        }

        @Override
        public void run() {
            try {
                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                ByteBuffer buffer = ByteBuffer.allocate(100);
                buffer.put("\r\nFollow you: ".getBytes(Charset.forName("UTF-8")));
                socketChannel.read(buffer);
                buffer.put("\r\n".getBytes(Charset.forName("UTF-8")));
                buffer.flip();
                socketChannel.write(buffer);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
