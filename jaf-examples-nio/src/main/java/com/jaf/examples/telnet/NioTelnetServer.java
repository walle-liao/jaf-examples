package com.jaf.examples.telnet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by walle on 2017/3/12.
 */
public class NioTelnetServer {

    public static void main(String[] args) throws IOException {
        NioTelnetServer.start();
    }


    public static void start() throws IOException {
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
                    ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    socketChannel.write(ByteBuffer.wrap("welcome telnet server ... \r\n".getBytes(Charset.forName("UTF-8"))));
                    keyIterator.remove();
                } else if(key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    CharBuffer charBuffer = CharBuffer.allocate(20);
                    charBuffer.put('\r').put('\n');

                    ByteBuffer buffer = ByteBuffer.allocate(100);
                    buffer.put("\r\nFollow you: ".getBytes(Charset.forName("UTF-8")));
                    socketChannel.read(buffer);
                    buffer.put("\r\n".getBytes(Charset.forName("UTF-8")));
                    buffer.flip();
                    socketChannel.write(buffer);
                }
            }
        }
    }


}
