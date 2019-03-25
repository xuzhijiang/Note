package org.netty.core.channel;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Calendar;

public class TimeServerHandleTask implements Runnable {

    SocketChannel socketChannel;

    public TimeServerHandleTask(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    // 在TimeServerHandleTask中，会判断是否发送了请求，如果没有请求则不需要处理。
    // 如果SocketChannel发送了多次请求，TimeServerHandleTask一次也只会处理一个请求。
    // 其他的请求等到下一次循环的时候再处理。因为使用线程池的情况，线程的数量有限，所以要合理的分配，
    // 不能让一个线程一直处理一个client的请求。

    @Override
    public void run() {
        try {
            ByteBuffer requestBuffer = ByteBuffer.allocate("GET CURRENT TIME".length());

            //尝试读取数据，因为是非阻塞，所以如果没有数据会立即返回。
            int bytesRead = socketChannel.read(requestBuffer);
            //如果没有读取到数据，说明当前SocketChannel并没有发送请求，不需要处理
            if (bytesRead <= 0) {
                return;
            }
            //如果读取到了数据，则需要考虑粘包、解包问题，这个while代码是为了读取一个完整的请求信息"GET CURRENT TIME"，
            while (requestBuffer.hasRemaining()) {
                socketChannel.read(requestBuffer);
            }
            // requestBuffer.array()返回字节数组.并使用 字节数组构造字符串
            String requestStr = new String(requestBuffer.array());
            if (!"GET CURRENT TIME".equals(requestStr)) {
                String bad_request = "BAD_REQUEST";
                ByteBuffer responseBuffer = ByteBuffer.allocate(bad_request.length());
                responseBuffer.put(bad_request.getBytes());
                responseBuffer.flip();
                socketChannel.write(responseBuffer);
            } else {
                String timeStr = Calendar.getInstance().getTime().toLocaleString();
                ByteBuffer responseBuffer = ByteBuffer.allocate(timeStr.length());
                responseBuffer.put(timeStr.getBytes());
                responseBuffer.flip();
                socketChannel.write(responseBuffer);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}