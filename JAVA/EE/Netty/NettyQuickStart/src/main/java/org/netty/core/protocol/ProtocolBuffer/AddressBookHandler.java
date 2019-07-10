package org.netty.core.protocol.ProtocolBuffer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class AddressBookHandler extends ChannelInboundHandlerAdapter {

    // AddressBookHandler是我们自己定义处理类，在其channelRead方法参数中，Object msg就是解码后的Person，在返回数据时，
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AddressBookProtos.Person req = (AddressBookProtos.Person) msg;

        //...处理req
        //返回响应
        ctx.writeAndFlush(AddressBookProtos.AddressBook.newBuilder().
                addPerson(AddressBookProtos.Person.newBuilder()
                .setId(req.getId())
                .setName(req.getName()).setEmail("xzj@gmail.com").build()));
    }
    /**
     * 可以看到，在Netty中使用了protoBuf之后，我们接受数据与响应数据的协议就是.proto文件生成java对象，这极大的简化了自定义协议的开发。
     */
}
