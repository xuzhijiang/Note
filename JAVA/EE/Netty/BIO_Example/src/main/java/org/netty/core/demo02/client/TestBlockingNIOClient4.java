package org.netty.core.demo02.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.Scanner;

public class TestBlockingNIOClient4 {

    public static void main(String[] args) throws IOException {
        client();
    }

    public static void client() throws IOException{
        //获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",9898));
        //切换到非阻塞模式
        sChannel.configureBlocking(false);
        //分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        System.out.println("准备发送数据: ");
        //发送数据给服务端
        Scanner scan = new Scanner(System.in);
        System.out.println("scan.hasNext(): " + scan.hasNext());
        while(scan.hasNext()){
            String string = scan.next();
            System.out.println("发送: " + string);
            buf.put(string.getBytes());
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }

        //关闭通道
        sChannel.close();
    }

}