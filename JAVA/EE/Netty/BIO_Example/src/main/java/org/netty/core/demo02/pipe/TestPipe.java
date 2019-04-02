package org.netty.core.demo02.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * 管道是两个线程之间的单向数据连接，Pipe有一个source通道和一个sink通道，
 * 数据会被写入sink通道，从source通道读取
 */
public class TestPipe {

    public void testPipe() throws IOException {
        //获取管道
        Pipe pipe = Pipe.open();

        //将缓冲区中的数据写入管道
        ByteBuffer buf = ByteBuffer.allocate(1024);
        Pipe.SinkChannel sinkChannel = pipe.sink();
        buf.put("通过管道发送数据".getBytes());
        buf.flip();
        sinkChannel.write(buf);

        //在一个线程中共用一个pipe读取数据，可以开两个线程
        Pipe.SourceChannel sourceChannel = pipe.source();
        buf.flip();
        int len = sourceChannel.read(buf);
        System.out.println(new String(buf.array(),0,len));
        sourceChannel.close();
        sinkChannel.close();
    }

}
