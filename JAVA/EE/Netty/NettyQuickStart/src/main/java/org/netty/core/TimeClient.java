package org.netty.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

// 将NIO编程时的时间服务案例，用Netty来实现: TimeClient发送“QUERY TIME ORDER”请求，
// TimeServer接受到这个请求后，返回当前时间

// TimeClient负责与服务端的8080端口建立连接
public class TimeClient {

    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 8080;
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 第一步:
            // 首先我们创建了一个Bootstrap实例，与ServerBootstrap相对应，这表示一个客户端的启动类
            Bootstrap bootstrap = new Bootstrap(); // (1)

            // 第二步:
            // 我们调用Bootstrap的group方法给Bootstrap实例设置了一个EventLoopGroup实例。
            // 前面提到，EventLoopGroup的作用是线程池。前面在创建ServerBootstrap时，
            // 设置了一个bossGroup，一个wrokerGroup，这样做主要是为将接受连接和处理连接请求任务划分开，
            // 以提升效率。对于客户端而言，则没有这种需求，只需要设置一个EventLoopGroup实例即可。
            bootstrap.group(workerGroup); // (2)

            // 第三步:
            // 通过channel方法指定了NioSocketChannel，这是netty在nio编程中用于表示客户端的对象实例。
            bootstrap.channel(NioSocketChannel.class); // (3)

            // 第四步:
            // 类似server端，在连接创建完成，初始化的时候，
            // 我们也给SocketChannel添加了几个处理器类。
            // 其中TimeClientHandler是我们自己编写的给服务端发送请求，并接受服务端响应的处理器类。
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {// (4)
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new TimeClientHandler());
                }
            });

            // 第五步:
            // 所有参数设置完成之后，调用Bootstrap的connect(host, port)方法，与服务端建立连接。
            // Start the client.
            ChannelFuture f = bootstrap.connect(host, port).sync(); // (5)
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}