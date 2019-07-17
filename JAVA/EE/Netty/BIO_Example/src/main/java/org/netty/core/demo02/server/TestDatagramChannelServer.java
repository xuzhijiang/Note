package org.netty.core.demo02.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class TestDatagramChannelServer {

    /**
     *
     * @throws IOException
     */
    public void receive() throws IOException {
        // DatagramChannel类似于java 网络编程的DatagramSocket类；使用UDP进行网络传输，
        // UDP是无连接，面向数据报文段的协议，对传输的数据不保证安全与完整 ；
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.bind(new InetSocketAddress(9898));
        Selector selector = Selector.open();
        datagramChannel.register(selector, SelectionKey.OP_READ);
        while(selector.select() > 0){
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while(it.hasNext()){
                SelectionKey sk = it.next();
                if(sk.isReadable()){
                    // 先创建一个缓存区对象
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    // 通过receive方法接收消息，这个方法返回一个SocketAddress对象，表示发送消息方的地址：
                    SocketAddress socketAddress = datagramChannel.receive(buf);
                    buf.flip();
                    System.out.println(new String(buf.array(),0,buf.limit()));
                }
            }
            it.remove();
        }
    }

}
