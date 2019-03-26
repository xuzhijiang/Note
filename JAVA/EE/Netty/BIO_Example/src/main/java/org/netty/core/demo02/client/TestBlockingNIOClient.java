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
        SocketChannel sChannel = null;
        //FileChannel inChannel = null;
        try {
            sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
            //inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);

            //分配指定大小的缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);
            buf.put("get current time".getBytes());
            buf.flip();
            sChannel.write(buf);
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
            if (sChannel != null) {
                try {
                    sChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}