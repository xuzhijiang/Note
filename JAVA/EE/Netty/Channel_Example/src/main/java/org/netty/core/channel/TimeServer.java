package org.netty.core.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.*;

/**
 * Channel案例,在这个案例中，我们使用nio中的channel+线程池，来实现TimeServer、TimeClient
 *
 * 本文的案例，只是要说明一个思路。在实际开发中，我们并不会写这样的代码，
 * 肯定是通过Selector来进行。这个案例只是想以一种循序渐进的方式来讲解，最终引出Selector
 *
 * Server端
 */
public class TimeServer {

    // TimeServer中维护了两个队列，idleQueue 和workingQueue

    private BlockingQueue<SocketChannel> idleQueue =new LinkedBlockingQueue<SocketChannel>();

    private  BlockingQueue<Future<SocketChannel>> workingQueue=new LinkedBlockingQueue<Future<SocketChannel>>();

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    {
        //  工作步骤如下所示：
        //  1、在main线程中，当接受到一个新的连接时，我们将相应的SocketChannel放入idleQueue。
        //  2、在static静态代码块中，我们创建了一个Thread。其作用是不断的循环idleQueue和workingQueue。

        new Thread(){
            @Override
            public void run() {
                try {
                    while (true) {
    // 首先循环idleQueue，迭代出其中的SocketChannel，然后封装成一个TimeServerHandleTask对象，
    // 提交到线程池中处理这个SocketChannel的请求，同时我们会将SocketChannel从idleQueue中移除，放到workingQueue中。
    // 需要注意的是，这个SocketChannel可能只是与服务端建立了连接，但是没有发送请求，又或者是发送了一次或者多次请求。
    // 发送一次"GET CURRENT TIME”，就相当于一次请求。

                        //task1：迭代当前idleQueue中的SocketChannel，
                        // 提交到线程池中执行任务，并将其移到workingQueue中
                        for (int i = 0; i < idleQueue.size(); i++) {
                            // poll会从idelQueue中Retrieves and removes。
                            SocketChannel socketChannel = idleQueue.poll();
                            if (socketChannel != null) {
                                // 将SocketChannel封装成一个TimeServerHandleTask对象，
                                // 提交到线程池中处理这个SocketChannel的请求
                                Future<SocketChannel> result = executor.submit(new TimeServerHandleTask(socketChannel), socketChannel);
                                workingQueue.put(result);
                            }
                        }

                        //接着是迭代workingQueue，通过future.isDone()判断当前请求是否处理完成，
                        // 如果处理完成，将其从workingQueue中移除，重新加入idleQueue中。
                        //task2：迭代当前workingQueue中的SocketChannel，如果任务执行完成，将其移到idleQueue中
                        for (int i = 0; i < workingQueue.size(); i++) {
                            Future<SocketChannel> future = workingQueue.poll();
                            // 如果任务没有完成，再放到workingQueue中.
                            if (!future.isDone()){
                                workingQueue.put(future);
                                continue;
                            }
                            // 走到这里，说明当前迭代到任务已经完成了。
                            SocketChannel channel  = null;
                            try {
                                channel = future.get();
                                idleQueue.put(channel);
                            } catch (ExecutionException e) {
                                //如果future.get()抛出异常，关闭SocketChannel，不再放回idleQueue
                                channel.close();
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        TimeServer timeServer = new TimeServer();
        ServerSocketChannel ssc= ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(8080));

        while (true){
            SocketChannel socketChannel = ssc.accept();
            if(socketChannel==null){
                continue;
            }else{
                socketChannel.configureBlocking(false);
                timeServer.idleQueue.add(socketChannel);
            }
        }
    }
}
