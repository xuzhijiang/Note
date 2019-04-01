package org.netty.core.demo02.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestBlockingNIOClient {

    public static void main(String[] args) {
        client();
    }

    //客户端
    public static void client() {
        SocketChannel socketChannel = null;
        //FileChannel inChannel = null;
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

            // 创建SocketChannel也可以使用下面的方法,但是这种方法下面要绑定，如果这个端口已经被其他程序
            // 绑定了，就会出现异常.所以我们这里不用
            // socketChannel = SocketChannel.open();
            //inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);

            // 把SocketChannel绑定到某一个SocketAddress
            //socketChannel.bind(new InetSocketAddress("127.0.0.1", 9898));

            //分配指定大小的缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);

            // 向缓冲区写入数据
            buf.put("get current time".getBytes());
            buf.flip();

            // 把缓冲区的数据通过通道传输
            socketChannel.write(buf);
            buf.clear();
            //读取本地文件，并发送到服务端
//            while (inChannel.read(buf) != -1) {
//                buf.flip();
//                // 通过通道传输数据
//                sChannel.write(buf);
//                buf.clear();
//            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            if (inChannel != null) {
//                try {
//                    inChannel.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
            if (socketChannel != null) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}