package org.netty.core;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

// TimeClientHandler主要用于给Server端发送"QUERY TIME ORDER”请求，并接受服务端响应。
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    // 1、TimeClientHandler继承了ChannelInboundHandlerAdapter，并同时覆盖了channelActive和channelRead方法。

    private byte[] req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();

    //   当客户端与服务端连接建立成功后，channelActive方法会被回调，
    //   我们在这个方法中给服务端发送"QUERY TIME ORDER”请求。
    @Override
    public void channelActive(ChannelHandlerContext ctx) {//1
        ByteBuf message = Unpooled.buffer(req.length);
        message.writeBytes(req);
        ctx.writeAndFlush(message);
    }

    // 当接受到服务端响应后，channelRead方法会被会回调，我们在这个方法中打印出响应的时间信息。
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("Now is:" + body);
    }

}