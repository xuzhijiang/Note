package org.netty.core;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

// TimeServerHandler用户处理客户端的请求，每当接收到"QUERY TIME ORDER”请求时，
// 就返回当前时间，否则返回"BAD REQUEST”。

public class TimeServerHandler extends ChannelInboundHandlerAdapter {


    // 1.
    // TimeServerHandler继承了ChannelInboundHandlerAdapter，并覆盖了channelRead方法，
    // 当接受到客户端发送了请求之后，channelRead方法会被回调。
    // 参数ChannelHandlerContext包含了"当前发送请求的客户端"的一些上下文信息，msg表示客户端发送的请求信息。
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 2.
        // 我们直接将msg强制转换成了String类型。这是因为我们在前面已经添加过了StringDecoder，
        // 其已经将二进制流转换成了一个字符串
        String request = (String) msg; //2
        String response;
        // 3.
        // 构建响应。会判断请求是否合法，如果请求信息是"QUERY TIME ORDER”，则返回当前时间，否则返回"BAD REQUEST”
        if ("QUERY TIME ORDER".equals(request)) { // 3
            response = new Date(System.currentTimeMillis()).toString();
        } else {
            response = "BAD REQUEST";
        }
        // 4.
        // 在响应内容中加上了System.getProperty("line.separator”)，也就是所谓的换行符。
        // 在linux操作系统中，就是”\n”，在windows操作系统是”\r\n”。
        // 加上换行符，主要是因为客户端也要对服务端的响应进行解码，当遇到一个换行符时，就认为是一个完整的响应。
        response = response + System.getProperty("line.separator"); // 4
        // 5.
        // 调用了Unpooled.copiedBuffer方法创建了一个缓冲区对象ByteBuf。
        // 在java nio包中，使用ByteBuffer类来表示一个缓冲区对象。在netty中，使用ByteBuf表示一个缓冲区对象。
        // 在后面的章节中，我们会对ByteBuf进行详细讲解。
        ByteBuf resp = Unpooled.copiedBuffer(response.getBytes()); // 5
        // 6.
        // 调用ChannelHandlerContext的writeAndFlush方法，将响应刷新到客户端
        ctx.writeAndFlush(resp); // 6
    }

}
