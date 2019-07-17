package org.netty.core.channel;

import java.nio.ByteBuffer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 *
 * 到这里，我们好像已经可以达到我们的目标"以尽可能少的线程，处理尽可能多的client请求" ，
 * 但是现实总是残酷的，这个案例中代码的效率太低了。
 *
 * 因为我们并不知道一个SocketChannel是否发送了请求，所以必须迭代所有的SocketChannel，
 * 然后尝试读取请求数据，如果有请求，就处理，否则就跳过。假设一个有10000个连接，
 * 前9999个连接都没有请求，刚好最后一个连接才有请求。那么前9999次任务处理都是没有必要的。
 *
 * 如果有一种方式，可以让我们直接获取到真正发送了请求的SocketChannel，那么效率将会高的多。
 *
 * 这就是我们下一节将要讲解的Selector(选择器)，其可以帮助我们管理所有与server端已经建立了
 * 连接的client(SocketChannel)，并将准备好数据的client过滤出来。我们可以有一个专门的线程来运行Selector，
 * 将准备好数据的client交给工作线程来处理。
 */
public class TimeClient {

    //连接超时时间
    static int connectTimeOut=3000;
    static ByteBuffer buffer=ByteBuffer.allocateDirect(1024);

    public static void main(String[] args) throws IOException, InterruptedException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(8080));
        socketChannel.configureBlocking(false);

        long start=System.currentTimeMillis();
        while (!socketChannel.finishConnect()){
            if (System.currentTimeMillis()-start>=connectTimeOut){
                throw new RuntimeException("尝试建立连接超过3秒");
            }
        }

        // 如果走到这一步，说明连接建立成功
        while (true){
            buffer.put("GET CURRENT TIME".getBytes());
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
            if(socketChannel.read(buffer)>0){
                buffer.flip();
                byte[] response=new byte[buffer.remaining()];
                buffer.get(response);
                System.out.println("reveive response:"+new String(response));
                buffer.clear();
            }
            Thread.sleep(5000);
        }

    }

}
