package org.netty.core.demo02.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestBlockingNIOServer {

    public static void main(String[] args) throws IOException {
        server();
    }

    //服务端(异常处理应该使用try-catch)
    public static void server() throws IOException {

        //获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();

//        FileChannel outChannel = FileChannel.open(Paths.get("44.jpg"),
//                StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        //绑定连接
        ssChannel.bind(new InetSocketAddress(9898));

        System.out.println("server accept:");
        //获取客户端连接的通道
        SocketChannel sChannel = ssChannel.accept();
        System.out.println("server 已经接受到客户端连接了");

        //分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        sChannel.read(buf);

        String requestStr = new String(buf.array());
        System.out.println("Server receive: " + requestStr);
        //接收客户端的数据，并保存在本地
//        while (sChannel.read(buf) != -1) {
//            buf.flip();
//            outChannel.write(buf);
//            buf.clear();
//        }

        //关闭通道
        sChannel.close();
        //outChannel.close();
        ssChannel.close();
    }
}
