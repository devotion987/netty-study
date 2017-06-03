package com.devotion.netty.chapter4;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by wugy on 2017/6/3 8:52
 */
public class PlainNioServer {

    public void serve(int port) throws Exception {
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.configureBlocking(false);
        ServerSocket serverSocket = socketChannel.socket();

        InetSocketAddress address = new InetSocketAddress(port);
        serverSocket.bind(address);

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        ByteBuffer msg = ByteBuffer.wrap("HI!\r\n".getBytes());

        for (; ; ) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();

                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());
                    System.out.println("Accepted connection form " + client);
                }
                if (selectionKey.isWritable()) {
                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    ByteBuffer buf = (ByteBuffer) selectionKey.attachment();
                    while (buf.hasRemaining()) {
                        if (client.write(buf) == 0)
                            break;
                    }
                    client.close();
                }

                selectionKey.cancel();
                selectionKey.channel().close();
            }
        }
    }
}
