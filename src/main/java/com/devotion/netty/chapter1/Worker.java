package com.devotion.netty.chapter1;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.Future;

/**
 * Created by lenovo on 2017/6/1.
 */
public class Worker {

    public void doWork() {
        Fetcher fetcher = () -> {
            return null;
        };
        Future<String> future = fetcher.fetchData();
        while (future.isDone()) {
            // do something else
        }
        try {
            System.out.println("Data received:" + future.get());
        } catch (Exception e) {
            System.err.println("An error occur:" + e.getMessage());
        }

    }

    void testNio() throws Exception {
        RandomAccessFile file = new RandomAccessFile("data.txt", "rw");
        FileChannel fileChannel = file.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = -1;
        do {
            bytesRead = fileChannel.read(buf);
            if (bytesRead != -1) {
                buf.flip();
                while (buf.hasRemaining()) {
                    System.out.println((char) buf.get());
                }
                buf.clear();
            }
        } while (bytesRead != -1);
        fileChannel.close();
    }
}
