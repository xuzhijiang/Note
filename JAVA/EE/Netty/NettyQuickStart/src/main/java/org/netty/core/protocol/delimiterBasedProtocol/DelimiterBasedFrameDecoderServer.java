package org.netty.core.protocol.delimiterBasedProtocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.base64.Base64Decoder;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * Server接受到的数据，首先我们要去除分隔符，然后才能进行base64解码。
 * 因此首选我们添加了DelimiterBasedFrameDecoder根据&处理粘包半包问题，之后使用Base64Decoder进行解码，最后通过一个自定义ChannelInboundHandler打印请求的数据。
 */
public class DelimiterBasedFrameDecoderServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();     // (1)
        EventLoopGroup workderGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workderGroup)
                    .channel(NioServerSocketChannel.class)  // (3)
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ByteBuf byteBuf = Unpooled.buffer();
                    byteBuf.writeBytes("&".getBytes());
                    //  先使用DelimiterBasedFrameDecoder解码，以&作为分割符
                    socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, true, true, byteBuf));

                    // 之后使用Base64Decoder对数据进行解码，得到报文的原始二进制流
                    socketChannel.pipeline().addLast(new Base64Decoder());

                    // 对请求报文进行处理
                    socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            if (msg instanceof ByteBuf) {
                                ByteBuf packet = (ByteBuf)msg;
                                System.out.println(new Date().toLocaleString() + ":" + packet
                                .toString(Charset.defaultCharset()));
                            }
                        }
                    });
                }
            });

            // Bind and start to accept incoming connections.
            ChannelFuture f = serverBootstrap.bind(8080).sync();
            System.out.println("DelimiterBasedFrameDecoderServer Started on 8080...");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workderGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
