package com.devotion.netty.chapter4;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by wugy on 2017/6/3.
 */
public class PlainOioServer {

    public void serve(int port) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        for (; ; ) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted connection form " + clientSocket);

            new Thread(() -> {
                try {
                    OutputStream out = clientSocket.getOutputStream();
                    out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8")));
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
