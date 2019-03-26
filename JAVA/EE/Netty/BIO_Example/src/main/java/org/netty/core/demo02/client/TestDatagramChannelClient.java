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
        DatagramChannel dc = DatagramChannel.open();
        dc.configureBlocking(false);
        ByteBuffer buf = ByteBuffer.allocate(1024);
        Scanner scan = new Scanner(System.in);
        while(scan.hasNext()){
            String str = scan.next();
            buf.put((new Date().toString()+":\n"+str).getBytes());
            buf.flip();
            dc.send(buf, new InetSocketAddress("127.0.0.1",9898));
            buf.clear();
        }
        dc.close();
    }

}
