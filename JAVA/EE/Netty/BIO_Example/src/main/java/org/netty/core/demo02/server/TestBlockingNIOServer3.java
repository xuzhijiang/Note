package org.netty.core.demo02.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestBlockingNIOServer3 {

    public static void main(String[] args) throws IOException {
        server();
    }

    public static void server() throws IOException{
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        //FileChannel outChannel = FileChannel.open(Paths.get("66.jpg"), StandardOpenOption.WRITE,StandardOpenOption.CREATE);

        ssChannel.bind(new InetSocketAddress(9898));
        SocketChannel sChannel = ssChannel.accept();
        ByteBuffer buf = ByteBuffer.allocate(1024);

        sChannel.read(buf);
        buf.flip();
        System.out.println("server receive message from client: " + new String(buf.array()));
        buf.clear();
//        while (sChannel.read(buf)!= -1) {
//            buf.flip();
//            outChannel.write(buf);
//            buf.clear();
//        }
        //发送反馈请求给客户端
        buf.put("服务端接收数据成功".getBytes());
        buf.flip();
        sChannel.write(buf);
        sChannel.close();
        //outChannel.close();
        ssChannel.close();
    }

}
