package org.netty.core.demo02.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

/**
 * 使用非阻塞式实现(重点)(非阻塞网络通信)
 *
 * 选择器（Selector）：将每一个通道注册到选择器上，选择器就是监控每一个通道的IO状况（读，写，连接等情况）。
 * 只有当通道中的请求准备就绪时，才会将任务分配到服务端的一个线程或者多个线程上运行。
 * 选择器：是SelectableChannel的多路复用器，用于监控selectablechannel的IO状况。
 */
public class TestBlockingNIOServer4 {

    //服务端
    public void server() throws IOException{
        //获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();

        //切换为非阻塞模式
        ssChannel.configureBlocking(false);

        //绑定连接
        ssChannel.bind(new InetSocketAddress(9898));

        //创建Selector选择器
        Selector selector = Selector.open();

        //将通道注册到选择器上,并且指定监听接收事件
        // 通过第二个参数指定，可以监听的事件如下:
        // - 读事件：SelectionKey.OP_READ
        // - 写事件：SelectionKey.OP_WRITE
        // - 连接事件:SelectionKey.OP_CONNECT
        // - 接收事件：SelectionKey.OP_ACCEPT
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 如果注册时不止监听一个事件，则可以使用“位或”操作符连接。如下
        // int interestSet = SelectionKey.OP_RED|SelectionKey.OP_WRITE;

        //通过轮询式的方式获取选择器中准备就绪的事件
        while(selector.select() > 0){
            //获取当前选择器中所有注册的选择键（已就绪的监听事件）
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                //获取准备就绪的事件
                SelectionKey sk = it.next();
                //判断是什么事件就绪（接收事件|连接事件|读事件|写事件就绪）
                if(sk.isAcceptable()){
                    //若接收事件就绪，获取客户端连接
                    SocketChannel sChannel = ssChannel.accept();
                    //切换到非阻塞模式
                    sChannel.configureBlocking(false);
                    //将通道注册到选择器上
                    sChannel.register(selector, SelectionKey.OP_READ);
                }else if(sk.isReadable()){
                    //获取当前选择器上读就绪状态的通道
                    SocketChannel sChannel = (SocketChannel) sk.channel();
                    //读取数据
                    ByteBuffer buf = ByteBuffer.allocate(1024);

                    int len = 0;
                    while((len = sChannel.read(buf)) > 0){
                        buf.flip();
                        System.out.println(new String(buf.array(),0,len));
                        buf.clear();
                    }
                }
                //取消选择键（SelectionKey）
                it.remove();
            }
        }
    }

}
