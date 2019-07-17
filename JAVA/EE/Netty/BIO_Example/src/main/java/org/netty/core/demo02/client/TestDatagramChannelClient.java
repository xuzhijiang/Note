package org.netty.core.demo02.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Date;
import java.util.Scanner;

/**
 * DatagramChannel是一个能收发UDP包的通道
 */
public class TestDatagramChannelClient {

    /**
     *
     * @throws IOException
     */
    public void send() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        ByteBuffer buf = ByteBuffer.allocate(1024);
        Scanner scan = new Scanner(System.in);
        while(scan.hasNext()){
            String str = scan.next();
            buf.put((new Date().toString()+":\n"+str).getBytes());
            buf.flip();
            // 由于UDP下，服务端和客户端通信并不需要建立连接，只需要知道对方地址即可发出消息，
            // 但是是否发送成功或者成功被接收到是没有保证的;
            // 发送消息通过send方法发出，ci方法返回一个int值，表示成功发送的字节数：
            int len = datagramChannel.send(buf, new InetSocketAddress("127.0.0.1", 9898));
            buf.clear();
        }
        datagramChannel.close();
    }

}
