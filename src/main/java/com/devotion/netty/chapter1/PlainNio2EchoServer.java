package com.devotion.netty.chapter1;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * Created by wugy on 2017/6/1.
 */
public class PlainNio2EchoServer {

    public void serve(int port) throws Exception {
        System.out.println("Listening for connections on port " + port);
        AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(port);
        serverChannel.bind(address);
        CountDownLatch latch = new CountDownLatch(1);
        serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel channel, Object attachment) {
                serverChannel.accept(null, this);
                ByteBuffer buf = ByteBuffer.allocate(100);
                // Trigger a read operation on the Channel, the given CompletionHandler will be notified once something was read
                channel.read(buf, buf, new EchoCompletionHandler(channel));
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                try {
                    serverChannel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static class EchoCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

        private final AsynchronousSocketChannel channel;

        public EchoCompletionHandler(AsynchronousSocketChannel channel) {
            this.channel = channel;
        }

        @Override
        public void completed(Integer result, ByteBuffer buf) {
            buf.flip();
            // Trigger a write operation on the Channel, the given CompletionHandler will be notified once something was write
            channel.write(buf, buf, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    if (attachment.hasRemaining()) {
                        // Trigger again a write operation if something is left in the ByteBuffer
                        channel.write(buf, buf, this);
                    } else {
                        attachment.compact();
                        // Trigger a read operation on the Channel, the given CompletionHandler will be notified once something was read
                        channel.read(buf, buf, EchoCompletionHandler.this);
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    try {
                        channel.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            try {
                channel.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
