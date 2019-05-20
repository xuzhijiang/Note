package org.netty.core;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeServer {

    // 时间服务器TimeServer在8080端口监听客户端请求
    private int port = 8080;

    public static void main(String[] args) throws Exception {
        new TimeServer().run();
    }

    public void run() throws Exception {
        // 首先我们创建了两个EventLoopGroup实例：bossGroup和workerGroup，
        // 目前可以将bossGroup和workerGroup理解为两个线程池:
        // 其中bossGroup用于接受客户端连接，bossGroup在接受到客户端连接之后，将连接交给workerGroup来进行处理
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 接着，我们创建了一个ServerBootstrap实例，从名字上就可以看出来这是一个服务端启动类，
            // 我们需要给设置一些参数，包括第1步创建的bossGroup和workerGroup。
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    //我们通过channel方法指定了NioServerSocketChannel，这是netty中表示服务端的类，
                    // 用于接受客户端连接，对应于java.nio包中的ServerSocketChannel。
                    .channel(NioServerSocketChannel.class) // (3)
                    // 我们通过childHandler方法，设置了一个匿名内部类ChannelInitializer实例，
                    // 用于初始化客户端连接SocketChannel实例。在第3步中，我们提到NioServerSocketChannel是用于接受客户端连接，
                    // 在接收到客户端连接之后，netty会回调ChannelInitializer的initChannel方法需要对这个连接进行一些初始化工作，
                    // 主要是告诉netty之后如何处理和响应这个客户端的请求。
                    // 在这里，主要是添加了3个ChannelHandler实例：LineBasedFrameDecoder、StringDecoder、TimeServerHandler。
                    // 其中LineBasedFrameDecoder、StringDecoder是netty本身提供的，用于解决TCP粘包、解包的工具类。

                    //LineBasedFrameDecoder在解析客户端请求时，遇到字符”\n”或”\r\n”时则认为是一个完整的请求报文，
                    // 然后将这个请求报文的二进制字节流交给StringDecoder处理。

                    //StringDecoder将字节流转换成一个字符串，交给TimeServerHandler来进行处理。

                    //TimeServerHandler是我们自己要编写的类，在这个类中，我们要根据用户请求返回当前时间。
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new TimeServerHandler());
                        }
                    });

            // 在所有的配置都设置好之后，我们调用了ServerBootstrap的bind(port)方法，
            // 开启真正的监听在8080端口，接受客户端请求。
            ChannelFuture f = b.bind(port).sync(); // (5)
            System.out.println("TimeServer Started on 8080...");
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}