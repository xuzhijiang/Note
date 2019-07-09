package org.netty.core.protocol.LengthFieldBasedProtocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 *  在Server端，我们通过LengthFieldBasedFrameDecoder进行解码，并删除Length字段的2个字节，
 *  交给之后StringDecoder转换为字符串，最后在我们的自定义的ChannelInboundHandler进行打印。
 *
 *  分别启动server端与client端，在server端，我们将看到输出：
 *
 * LengthFieldBasedFrameDecoderServer Started on 8080...
 * receive req:i am request!
 * receive req:i am a anther request!
 *
 *  注意这里打印了2次，表示LengthFieldBasedFrameDecoder的确解码成功。
 *
 *   部分读者可能不信任这个结果，那么可以尝试将Server端的LengthFieldBasedFrameDecoder和Client端的LengthFieldPrepender注释掉，
 *   再次运行的话，大概率你在server端控制台将看到：
 *
 * LengthFieldBasedFrameDecoderServer Started on 8080...
 * receive req:i am request!i am a anther request!
 *
 * 也就是说，在没有使用LengthFieldBasedFrameDecoder和LengthFieldPrepender的情况下，发生了粘包，而服务端无法区分。
 */
public class LengthFieldBasedFrameDecoderServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(16384, 0, 2, 0, 2));

                            socketChannel.pipeline().addLast(new StringDecoder());

                            socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    System.out.println("receive req: " + msg);
                                }
                            });
                        }
                    });

            // Bind and start to accept incoming connections.
            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
            System.out.println("LengthFieldBasedFrameDecoderServer started on 8080....");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
