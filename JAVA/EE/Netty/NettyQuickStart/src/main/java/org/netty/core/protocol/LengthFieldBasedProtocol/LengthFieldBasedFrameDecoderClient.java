package org.netty.core.protocol.LengthFieldBasedProtocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 了解了LengthFieldPrepender和LengthFieldBasedFrameDecoder的作用后，我们编写一个实际的案例。
 * client发送一个请求，使用LengthFieldPrepender进行编码，server接受请求，使用LengthFieldBasedFrameDecoder进行解码。
 *
 * 我们采用最简单的的通信协议格式，不指定其他报文头：
 *
 * +--------+-----------+
 * | Length |   Content |
 * +--------+-----------+
 *
 * 在Client端我们自定义了一个ChannelInboundHandler，在连接被激活时，立即发送两个请求："i am request!”、"i am a anther request!" 。
 * 另外注意，我们是分两次调用ctx.writeAndFlush，每次调用都会导致当前请求数据经过StringEncoder进行编码，得到包含这个请求内容ByteBuf实例，  然后再到LengthFieldPrepender进行编码添加Length字段。
 *
 *       因此我们发送的实际上是以下2个报文
 *
 * Length(13)    Content
 * +--------+-------------------+
 * + 0x000D | "i am request!"   |
 * +--------+-------------------+
 * Length(23)    Content
 * +--------+-----------------------------+
 * + 0x0017 | "i am a anther request!"    |
 * +--------+-----------------------------+
 */
public class LengthFieldBasedFrameDecoderClient {

    public static void main(String[] args) {
        EventLoopGroup workderGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workderGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);

            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new LengthFieldPrepender(2, 0, false));

                    socketChannel.pipeline().addLast(new StringEncoder());

                    socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                        // 在与server建立连接后，即发送请求报文
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            ctx.writeAndFlush("i am request!");
                            ctx.writeAndFlush("i am another request!");
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
            workderGroup.shutdownGracefully();
        }
    }

}
