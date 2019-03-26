package org.netty.core.demo02.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 说明：以上程序(TestBlockingNIOServer2)运行后就会导致阻塞，服务端不知道客户端是否发送结束，
 * 解决办法有两种，一种是使用客户端使用shutdownOutPut(见示例TestBlockingNIOServer3)，
 * 另外一种就是服务端换成非阻塞模式,(见示例TestBlockingNIOServer4)
 */
public class TestBlockingNIOServer2 {

    public static void main(String[] args) throws IOException {
        server();
    }

    //服务端(异常处理应该使用try-catch)
    public static void server() throws IOException{
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        //FileChannel outChannel = FileChannel.open(Paths.get("66.jpg"), StandardOpenOption.WRITE,StandardOpenOption.CREATE);

        ssChannel.bind(new InetSocketAddress(9898));

        System.out.println("server waitting for connection......");
        SocketChannel sChannel = ssChannel.accept();

        ByteBuffer buf = ByteBuffer.allocate(1024);

        sChannel.read(buf);

        buf.flip();
        System.out.println("Server receive info from client: " + new String(buf.array()));
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
