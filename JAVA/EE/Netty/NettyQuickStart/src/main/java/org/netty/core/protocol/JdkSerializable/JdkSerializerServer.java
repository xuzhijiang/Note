package org.netty.core.protocol.JdkSerializable;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.Date;

/**
 * Server端与Client端分析类似，这里不再赘述。
 *
 * 先后启动Server端与Client端，在Server端控制台我们将看到：
 *
 * JdkSerializerServer Started on 8080...
 * receive request:Request{request='i am request!', requestTime=2018-9-9 18:47:30}
 * 在Client端控制台我们将看到：
 *
 * receive response:Response{response='response to:i am request!', responseTime=2018-9-9 18:48:36}
 * 到此，我们的案例完成。Request和Response都编码、解码成功了。
 */
public class JdkSerializerServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ObjectEncoder());

                            socketChannel.pipeline().addLast(new ObjectDecoder(new ClassResolver() {
                                @Override
                                public Class<?> resolve(String s) throws ClassNotFoundException {
                                    return Class.forName(s);
                                }
                            }));

                            // 自定义这个ChannelInboundHandler打印拆包后的结果
                            socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    Request request = (Request) msg;
                                    System.out.println("receive request: " + request);
                                    Response response = new Response();
                                    response.setResponse("response to : " + request.getRequest());
                                    response.setResponseTime(new Date());
                                    ctx.writeAndFlush(response);
                                }
                            });
                        }
                    });

            // bind and start to accept incoming connections.
            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
            System.out.println("JdkSerializerServer started on 8080.....");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
