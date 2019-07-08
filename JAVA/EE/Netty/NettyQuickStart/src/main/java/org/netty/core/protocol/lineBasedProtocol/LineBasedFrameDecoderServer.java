package org.netty.core.protocol.lineBasedProtocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

import java.nio.charset.Charset;
import java.util.Date;

// Server端
public class LineBasedFrameDecoderServer {

    /**
     * LineBasedFrameDecoder要解决粘包问题，根据"\n"或"\r\n"对二进制数据进行解码，
     * 可能会解析出多个完整的请求报文，其会将每个有效报文封装在不同的ByteBuf实例中，
     * 然后针对每个ByteBuf实例都会调用一次其他的ChannelInboundHandler的channelRead方法。
     *
     * 因此LineBasedFrameDecoder接受到一次数据，其之后的ChannelInboundHandler的channelRead方法可能会被调用多次，
     * 且之后的ChannelInboundHandler的channelRead方法接受到的ByteBuf实例参数，包含的都是都是一个完整报文的二进制数据。
     * 因此无需再处理粘包问题，只需要将ByteBuf中包含的请求信息解析出来即可，然后进行相应的处理。本例中，我们仅仅是打印。
     */
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap(); // (2)
            serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 使用LineBasedFrameDecoder解决粘包问题，其会根据"\n"或"\r\n"对二进制数据进行拆分，封装到不同的ByteBuf实例中
                            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024, true, true));

                            // 自定义这个ChannelInboundHandler打印拆包后的结果
                            socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    if (msg instanceof ByteBuf) {
                                        ByteBuf packet = (ByteBuf) msg;
                                        System.out.println(new Date().toLocaleString() + ":" + packet.toString(Charset.defaultCharset()));
                                    }
                                }
                            });
                        }
                    });

            // Bind and start to accept incoming connections.
            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync(); // (7)
            System.out.println("LineBasedFrameDecoderServer Started on 8080...");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
