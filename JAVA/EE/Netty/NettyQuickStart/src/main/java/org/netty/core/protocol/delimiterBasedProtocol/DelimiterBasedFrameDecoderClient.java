package org.netty.core.protocol.delimiterBasedProtocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.base64.Base64;

/**
 * 在编写client端代码时我们先对原始内容进行base64编码，然后添加分割符之后进行输出。
 *
 * 需要注意，虽然Netty提供了Base64Encoder进行编码，这里并没有直接使用，如果直接使用Base64Encoder，
 * 那么会对我们输出的所有内容进行编码，意味着分隔符也会被编码，这显然不符合我们的预期，所以这里直接使用了Netty提供了Base64工具类来处理。
 *
 *
 * 如果一定要使用Base64Encoder，那么代码需要进行相应的修改，自定义的ChannelInboundHandler只输出原始内容，
 * 之后通过Base64Encoder进行编码，然后需要额外再定义一个ChannelOutboundHandler添加分隔符，如：
 *
 * ch.pipeline().addLast(new ChannelOutboundHandlerAdapter() {
 *    @Override
 *    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
 *       if(msg instanceof ByteBuf){
 *       // 添加分隔符
 *          ((ByteBuf) msg).writeBytes("&".getBytes());
 *       }
 *    }
 * });
 * ch.pipeline().addLast(new Base64Encoder());
 * ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
 *     // 在与server建立连接后，即发送请求报文
 *    public void channelActive(ChannelHandlerContext ctx) {
 *    // 只添加真正的要编码的内容
 *       ByteBuf req = Unpooled.buffer().writeBytes("hello&xzj&".getBytes());
 *       ctx.writeAndFlush(req);
 *    }
 * });
 *
 * 上述两种方案效果是等价的。
 *
 * 当我们先后启动server和client后，会看到server端控制台输出：
 *
 * DelimiterBasedFrameDecoderServer Started on 8080...
 * 2018-9-8 13:56:32:hello&xzj&
 *
 * 说明经过base64编码后，我们的请求中可以包含分隔符作为内容。
 *
 * 如果我们将base64编解码相关逻辑去掉，你将会看到的输出是:
 *
 * DelimiterBasedFrameDecoderServer Started on 8080...
 * 2018-9-8 14:13:31:hello
 * 2018-9-8 14:13:31:xzj
 *
 * 也就是说，原始内容也被误分割了，解码失败，读者可以自行验证。
 */
public class DelimiterBasedFrameDecoderClient {

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
                    socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        // 在与server建立连接后，即发送请求报文
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            // 先对要发送的原始内容进行base64编码
                            ByteBuf content = Base64.encode(Unpooled.buffer().writeBytes("Hello&xzj&".getBytes()));
                            // 之后添加分隔符
                            ByteBuf byteBuf = Unpooled.copiedBuffer(content);
                            byteBuf.writeBytes("&".getBytes());
                            ctx.writeAndFlush(byteBuf);
                        }
                    });
                }
            });

            // start the client.
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
