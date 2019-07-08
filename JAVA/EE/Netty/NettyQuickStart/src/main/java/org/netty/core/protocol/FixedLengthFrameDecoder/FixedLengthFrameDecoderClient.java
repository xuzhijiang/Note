package org.netty.core.protocol.FixedLengthFrameDecoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 通常情况下，很少有client与server交互时，直接使用定长协议，可能会造成浪费。例如你实际要发送的实际只有3个字节，但是定长协议设置的1024，那么可能你就要为这3个字节基础上，在加1021个空格，以便server端可以解析这个请求。在下一节我们将要介绍的LengthFieldBasedFrameDecoder，支持动态指定报文的长度。
 */
public class FixedLengthFrameDecoderClient {

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
                    socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                        // 在与server建立连接后，即发送请求报文
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            ByteBuf A = Unpooled.buffer().writeBytes("A".getBytes());
                            ByteBuf BC = Unpooled.buffer().writeBytes("BC".getBytes());
                            ByteBuf DEFG = Unpooled.buffer().writeBytes("DEFG".getBytes());
                            ByteBuf HI = Unpooled.buffer().writeBytes("HI".getBytes());

                            ctx.writeAndFlush(A);
                            ctx.writeAndFlush(BC);
                            ctx.writeAndFlush(DEFG);
                            ctx.writeAndFlush(HI);
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
