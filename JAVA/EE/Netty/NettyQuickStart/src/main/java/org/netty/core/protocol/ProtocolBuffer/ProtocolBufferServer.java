package org.netty.core.protocol.ProtocolBuffer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ProtocolBufferServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 我们首先在ChannelPipeline中添加了ProtobufVarint32FrameDecoder，其主要用于半包处理
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            // 随后添加ProtobufDecoder，它的参数类型是com.google.protobuf.MessageLite，
                            // 实际上就是告诉ProtobufDecoder需要解码的目标类是什么，
                            // 否则仅仅从字节数组中是无法判断出要解码的目标类型的，
                            // 这里我们设置的是AddressBookProtos.Person类型实例，在.proto文件中所有的定义的message在生成的java类，例如这里的Person，都实现了MessageLite接口。
                            ch.pipeline().addLast(new ProtobufDecoder(AddressBookProtos.Person.getDefaultInstance()));
                            ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            // ProtobufEncoder类用于对输出的消息进行编码。
                            ch.pipeline().addLast(new ProtobufEncoder());
                            ch.pipeline().addLast(new AddressBookHandler());
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
