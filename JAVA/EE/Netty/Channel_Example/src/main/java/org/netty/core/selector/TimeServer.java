package org.netty.core.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// Nio综合案例:基于Selector+Channel+线程池的Timeserver

/**
 * 1. 把Channel的就绪选择放在了主线程(Acceptor线程)中来处理(等待数据准备阶段)
 * 2. 而真正的读取请求并返回响应放在了线程池中提交一个任务来执行(处理数据阶段)
 *
 *  真正意义上实现了一个线程服务于多个client
 */
public class TimeServer {

    private static ExecutorService executor;

    static {
        executor = new ThreadPoolExecutor(5, 10,
                10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000));
    }

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));
        // 通道必须配置为非阻塞模式，否则使用选择器就没有任何意义了，因为如果通道在某个事件上被阻塞，
        // 那么服务器就不能响应其它事件，必须等待这个事件处理完毕才能去处理其它事件，
        // 显然这和选择器的作用背道而驰。
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        // 在将通道注册到选择器上时，还需要指定要注册的具体事件
        serverSocketChannel.register(selector, serverSocketChannel.validOps());

        while(true){
            int readyCount = selector.select(1000);
            if(readyCount == 0){
                continue;
            }

            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = keys.iterator();
            while(keyIterator.hasNext()){
                SelectionKey key = keyIterator.next();
                if(key.isValid()){
                    // 表示ServerSocketChannel
                    if(key.isAcceptable()){
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = server.accept();

                        socketChannel.configureBlocking(false);
                        // 这个新连接主要用于从客户端读取数据和写数据
                        socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    } else if(key.isReadable()){
                        executor.submit(new TimeServerTask(key));
                    }
                    keyIterator.remove();
                }
            }
        }

    }

}
