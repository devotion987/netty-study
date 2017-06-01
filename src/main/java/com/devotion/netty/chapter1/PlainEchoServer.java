package com.devotion.netty.chapter1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wugy on 2017/6/1.
 */
public class PlainEchoServer {

    public void serve(int port) throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted connection from " + clientSocket);
            new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                    while (true) {
                        writer.println(reader.readLine());
                        writer.flush();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        clientSocket.close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
