package org.java.core.base.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

// 演示BIO(Blocking I/O)通信（请求一应答）模型。
// 在客户端创建多个线程依次连接服务端并向其发送"当前时间+:hello world"，
// 服务端会为每个客户端线程创建一个线程来处理
public class IOServer {

    public static void main(String[] args) throws IOException {
        // 服务端处理客户端连接请求
        ServerSocket serverSocket = new ServerSocket(3333);

        // 接收到客户端连接请求之后为每个客户端创建一个新的线程进行处理
        new Thread(() -> {
            while (true) {
                try {
                    // 阻塞方法获取新的连接
                    // The method blocks until a connection is made
                    Socket socket = serverSocket.accept();

                    // 每一个新的连接都创建一个线程，负责读取数据
                    new Thread(() -> {
                        try {
                            int len;
                            // 创建大小为1024的字节数组
                            byte[] data = new byte[1024];
                            InputStream inputStream = socket.getInputStream();
                            // 按`字节流`方式读取数据
                            while ((len = inputStream.read(data)) != -1) {
                                // InputStream.read(): This method blocks until input data is
                                // available, end of file is detected, or an exception is thrown.
                                // 大意是: 这个方法阻塞，直到输入数据可用，或者到达了文件末尾，或者抛出异常.
                                System.out.println(new String(data, 0, len));
                            }
                        } catch (IOException e) {
                        }
                    }).start();
                } catch (IOException e) {
                }
            }
        }).start();

    }

}
