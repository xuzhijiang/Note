package org.netty.core.demo02.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestBlockingNIOClient2 {

    public static void main(String[] args) throws IOException {
        client();
    }

    //客户端
    public static void client() throws IOException{
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",9898));
        //FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        ByteBuffer buf = ByteBuffer.allocate(1024);

        // 把请求内容存放到缓冲区
        buf.put("get current time".getBytes());

        // 将缓冲区中的数据通过通道发送 给服务器
        buf.flip();
        sChannel.write(buf);
        buf.clear();
//        while (inChannel.read(buf) != -1) {
//            buf.flip();
//            sChannel.write(buf);
//            buf.clear();
//        }

        //接收服务端返回的信息
        while(sChannel.read(buf)!= -1){
            buf.flip();
            System.out.println(new String(buf.array()));
            buf.clear();
        }
        //inChannel.close();
        sChannel.close();
    }

}