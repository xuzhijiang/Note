package org.netty.core.protocol.JdkSerializable;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.Date;

// 首先我们分别编写两个Java对象Request/Response分别表示请求和响应。


/**
 *  由于client既要发送Java对象Request作为请求，又要接受服务端响应的Response对象，因此在ChannelPipeline中，我们同时添加了ObjectDecoder和ObjectEncoder。
 *
 *         另外我们自定义了一个ChannelInboundHandler，在连接建立时，其channelActive方法会被回调，我们在这个方法中构造一个Request对象发送，通过ObjectEncoder进行编码。服务端会返回一个Response对象，此时channelRead方法会被回调，由于已经经过ObjectDecoder解码，因此可以直接转换为Reponse对象，然后打印。
 */
public class JdkSerializableClient {

    public static void main(String[] args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new ObjectEncoder());

                    socketChannel.pipeline().addLast(new ObjectDecoder(new ClassResolver() {
                        @Override
                        public Class<?> resolve(String s) throws ClassNotFoundException {
                            return Class.forName(s);
                        }
                    }));

                    socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                        // 在与server建立连接后，即发送请求报文
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            Request request = new Request();
                            request.setRequest("i am request!");
                            request.setRequestTime(new Date());
                            ctx.writeAndFlush(request);
                        }

                        // 接收服务端的响应
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            Response response = (Response) msg;
                            System.out.println("receive response: " + response);
                        }
                    });
                }
            });

            // start the client
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8080).sync();
            // wait until the connection is closed.
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
