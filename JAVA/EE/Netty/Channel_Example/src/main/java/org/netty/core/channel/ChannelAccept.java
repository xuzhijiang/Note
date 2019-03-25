package org.netty.core.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 这段程序的作用是，在1234端口上接受client的请求，一旦接收到client的请求，
 * 会给其回复固定的字符串响应"Hello I must be going."
 *
 * 控制台打印出多次:Waiting for connections,说明程序的确运行在非阻塞模式下，
 * 因为否则就会想ServerSocket.accpet方法那样，一直阻塞下去。
 *
 * 通过命令行执行: telnet localhost 1234
 * 可以看到得到一个响应之后，连接立马关闭
 */
public class ChannelAccept {
    public static final String GREETING = "Hello I must be going.\r\n";

    public static void main(String[] argv) throws Exception {
        int port = 1234; // default
        if (argv.length > 0) {
            port = Integer.parseInt(argv[0]);
        }

        // 创建一个ServerSocketChannel
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 获取这个ServerSocketChannel对等的Socket，然后把这个获取到的Socket绑定到端口1234
        ssc.socket().bind(new InetSocketAddress(port));
        // 默认情况下，一个通道创建，总是阻塞的，我们可以通过调用configureBlocking(boolean)方法即可
        // 传递参数值为true 则设为阻塞模式，参数值为 false 值设为非阻塞模式.
        ssc.configureBlocking(false);

        while (true) {
            System.out.println("Waiting for connections");
            SocketChannel sc = ssc.accept();
            if (sc == null) {
                // no connections, snooze a while
                System.out.println("no connections, snooze a while!!");
                Thread.sleep(2000);
            } else {
                sc.configureBlocking(false);
                ByteBuffer allocate = ByteBuffer.allocateDirect (16 * 1024);

                ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes());
                System.out.println("sc.read(allocate): " + sc.read(allocate));
                while(sc.read(allocate)>0){
                    allocate.flip();
                    System.out.println("buffer.hasRemaining( ): " + buffer.hasRemaining( ));
                    while (buffer.hasRemaining( )) {
                        byte b = buffer.get();
                        System.out.println("b: " + b);
                    }
                    allocate.clear();
                }

                System.out.println("Incoming connection from: " + sc.socket().getRemoteSocketAddress());
                // rewind()函数与 flip()相似，但不影响上界limit属性。
                // 它只是将位置position值设回 0。可以使用 rewind()后退，重读已经被翻转的缓冲区中的数据。
                buffer.rewind();
                sc.write(buffer);
                sc.close();
            }
        }
    }
}