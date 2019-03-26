package org.netty.core.demo01.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class TimeServer {

    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            server = new ServerSocket(9090);
            System.out.println("TimeServer started on 9090...");
            while(true) {
                // 我们把主线程作为Acceptor Thread,它只负责与client建立连接)
                Socket client = server.accept();
                // 每次接收到一个新的客户端连接，启动一个新的线程来处理,
                // 这样当前线程就能够继续处理其他客户端连接了。
                new Thread(new TimeServerHandler(client)).start();// worker thread用于处理每个thread真正要执行的操作
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
