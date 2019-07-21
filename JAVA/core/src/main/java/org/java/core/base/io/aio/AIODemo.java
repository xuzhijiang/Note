package org.java.core.base.io.aio;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class AIODemo {

    /**
     * 通过Future读取数据（Reading Data Via a Future）
     * @throws IOException
     */
    @Test
    public void readDataByFuture() throws IOException {
       Path path = Paths.get("pom.xml");
       // Java7中新增了AsynchronousFileChannel作为nio的一部分。
        // AsynchronousFileChannel使得数据可以进行异步读写。

        // AsynchronousFileChannel的创建可以通过open()静态方法：
        // 第二个参数是操作类型,我们用的是StandardOpenOption.READ，表示以读的形式操作文件。
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        long position = 0L;

        // read()接受一个ByteBuffer作为第一个参数，数据会被读取到ByteBuffer中
        // 第二个参数是开始读取数据的位置。
        // read()方法会立刻返回，即使读操作没有完成。
        Future<Integer> operation = fileChannel.read(buffer, position);

        // 我们可以通过isDone()方法检查操作是否完成。
        while (!operation.isDone()) {
            System.out.println("还没有读取完成");
        }

        buffer.flip();
        byte[] data = new byte[buffer.limit()];
        buffer.get(data);

        System.out.println("data: " + new String(data));
        buffer.clear();
    }

    /**
     * 通过CompletionHandler读取数据（Reading Data Via a CompletionHandler）
     */
    @Test
    public void readDataByCompletionHandler() throws IOException{
        Path path = Paths.get("pom.xml");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        long position = 0L;
        System.out.println(buffer.hashCode());

        // 一旦读取完成，将会触发CompletionHandler的completed()方法，并传入一个Integer和ByteBuffer。
        // 前面的Integer整形表示的是读取到的字节数大小。第二个ByteBuffer也可以换成其他合适的对象方便数据写入
        // 如果读取操作失败了，那么会触发failed()方法。
        fileChannel.read(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("result: " + result);
                System.out.println("attachment: " + attachment.hashCode());

                attachment.flip();
                byte[] data = new byte[attachment.limit()];
                attachment.get(data);
                System.out.println(new String(data));
                attachment.clear();
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {

            }
        });
    }

    /**
     * 通过Future写数据（Writing Data Via a Future）
     */
    @Test
    public void writeDataByFuture() throws IOException {
        //  需要注意的是，这里的文件必须是已经存在的，否者在尝试write数
        // 据是会抛出一个java.nio.file.NoSuchFileException.
        Path path = Paths.get("aaa.xml");
        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        // 首先把文件已写方式打开
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        long position = 0L;
        buffer.put("hello kitty!!!".getBytes());
        buffer.flip();

        Future<Integer> operation = fileChannel.write(buffer, position);
        buffer.clear();

        while (!operation.isDone()) {
            System.out.println("还没有写完");
        }

        System.out.println("write done!");
    }

    @Test
    public void writeDataByCompletionHandler() throws IOException{
        Path path = Paths.get("aaaa.xml");
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        long postion = 0L;

        buffer.put("hhhhh".getBytes());
        buffer.flip();

        // 同样当数据吸入完成后completed()会被调用，如果失败了那么failed()会被调用。
        fileChannel.write(buffer, postion, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("bytes written : " + result);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("write failed!!");
                exc.printStackTrace();
            }
        });

    }
}
