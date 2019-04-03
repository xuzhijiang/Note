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
 * 这段代码的实现中：
 *
 * 1. 把Channel的就绪选择放在了主线程(Acceptor线程)中来处理(等待数据准备阶段)
 *
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
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(8080));
        ssc.configureBlocking(false);
        Selector selector = Selector.open();
        ssc.register(selector, ssc.validOps());

        while(true){
            int readyCount = selector.select(1000);
            if(readyCount == 0){
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while(keyIterator.hasNext()){
                SelectionKey selectionKey = keyIterator.next();
                if(selectionKey.isValid()){
                    // 表示ServerSocketChannel
                    if(selectionKey.isAcceptable()){
                        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel socketChannel = server.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    }

                    // 表示SocketChannel
                    if(selectionKey.isReadable()){
                        executor.submit(new TimeServerTask(selectionKey));
                    }
                    keyIterator.remove();
                }
            }
        }

    }

}
