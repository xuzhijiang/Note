package org.netty.core.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;

/**
 * 获取通道的三种方式
 * 1. java针对支持通道的类提供了getChannel()方法。
 * 本地IO:FileInputStream/FileOutputStream、RandomAcsessFile。
 * 网络IO:Socket、ServerSocket、DatagramSocket。
 *
 * 2. 在jdk1.7中的NIO.2针对各个通道提供了静态方法open();
 *
 * 3. Files工具类的newByteChannel()
 *
 * 注意:以下示例都是使用通道解决本地的数据传输，NIO的核心是网络数据传输。
 */
public class FileChannelSimpleDemo {

    public static void main(String[] args) {

    }

    /**
     * 利用通道完成文件的复制(非直接缓冲区)
     */
    public static void test1(){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        // 获取通道
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fis = new FileInputStream("1.jpg");
            fos = new FileOutputStream("2.jpg");
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();
            //分配指定大小的缓冲区,注意allocate分配的是非直接缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //将通道中的数据存入缓存区中
            while(inChannel.read(buffer) !=-1){
                buffer.flip();//切换到数据模式
                //将缓冲区中的数据写入通道中
                outChannel.write(buffer);
                buffer.clear();//清空缓存区
            }
        }catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(outChannel != null){outChannel.close(); }
                if(inChannel != null){ inChannel.close(); }
                if(fos != null){ fos.close(); }
                if(fis != null){ fis.close(); }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用直接缓冲区完成文件的复制
     * @throws IOException
     */
    public void test2() throws IOException{
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE_NEW);
        //内存映射文件
        MappedByteBuffer inMapperBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMapperBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());
        //直接对缓冲区进行数据的读写操作
        byte[] dst = new byte[inMapperBuf.limit()];
        inMapperBuf.get(dst);
        outMapperBuf.put(dst);
        inChannel.close();
        outChannel.close();
    }

    /**
     * 通道之间的数据传输
     * 使用上面的两种方式实现文件的复制，有不少的麻烦，使用通道之间的数据传输就方便很多。
     * @throws IOException
     */
    public void test3() throws IOException{
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("4.jpg"), StandardOpenOption.WRITE,
                StandardOpenOption.READ,StandardOpenOption.CREATE_NEW);
        inChannel.transferTo(0, inChannel.size(), outChannel);
        // 还有transferFrom也可以实现:
        // outChannel.transferFrom(inChannel, 0, inChannel.size());
        inChannel.close();
        outChannel.close();
    }

    /**
     * 分散读取实例:将通道中的数据分散到多个缓冲区中
     * @throws IOException
     */
    public void test4() throws IOException {
        RandomAccessFile rFile = new RandomAccessFile("1.txt", "rw");
        //获取通道
        FileChannel channel = rFile.getChannel();
        //分配指定大小的缓冲区
        ByteBuffer buffer1 = ByteBuffer.allocate(100);
        ByteBuffer buffer2 = ByteBuffer.allocate(1024);
        //分散读取
        ByteBuffer[] bufs = {buffer1, buffer2};
        channel.read(bufs);
        for (ByteBuffer byteBuffer : bufs) {
            byteBuffer.flip();
        }
        System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
        System.out.println("==================================");
        System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));
    }

    /**
     * 聚集写入实例:将多个缓冲区中的数据聚集到通道中。
     * @throws IOException
     */
    public void test5() throws IOException{
        RandomAccessFile rFile = new RandomAccessFile("1.txt", "rw");
        //获取通道
        FileChannel channel = rFile.getChannel();
        //分配指定大小的缓冲区
        ByteBuffer buffer1 = ByteBuffer.allocate(100);
        ByteBuffer buffer2 = ByteBuffer.allocate(1024);
        //分散读取
        ByteBuffer[] bufs = {buffer1,buffer2};
        channel.read(bufs);
        for (ByteBuffer byteBuffer : bufs) {
            byteBuffer.flip();
        }
        System.out.println(new String(bufs[0].array(),0,bufs[0].limit()));
        System.out.println("==================================");
        System.out.println(new String(bufs[1].array(),0,bufs[1].limit()));
        //聚集写入
        RandomAccessFile rfile2 = new RandomAccessFile("2.txt", "rw");
        FileChannel channel2 = rfile2.getChannel();
        channel2.write(bufs);
    }

    /**
     * 查看支持的字符集
     */
    public void test6(){
        //支持的字符集
        Map<String, Charset> map = Charset.availableCharsets();
        //遍历map
        Set<Map.Entry<String, Charset>> set = map.entrySet();
        for (Map.Entry<String, Charset> entry : set) {
            System.out.println(entry.getKey()+"==="+entry.getValue());
        }
    }

    /**
     * 通过一个字符集得到的编码器和解码器就不会出现乱码
     * @throws IOException
     */
    public void test7() throws IOException{
        Charset cs1 = Charset.forName("GBK");
        //获取编码器
        CharsetEncoder ce = cs1.newEncoder();
        //获取解码器
        CharsetDecoder cd = cs1.newDecoder();
        //创建buffer，并存入文字
        CharBuffer cBuff = CharBuffer.allocate(1024);
        cBuff.put("疾风知劲草");
        cBuff.flip();
        //编码，字符-字节
        ByteBuffer bBuf =  ce.encode(cBuff);
        //查看是否编码成功
        for(int i = 0; i < 10; i++){
            System.out.println(bBuf.get());
        }
        //解码，字节-字符
        bBuf.flip();
        CharBuffer cBuf2 = cd.decode(bBuf);
        System.out.println(cBuf2.toString());// 疾风知劲草
    }

    /**
     * 如果使用GBK进行编码，使用UTF-8进行解码，就会出现乱码，如下所示。
     * @throws IOException
     */
    public void test8() throws IOException{
        Charset cs1 = Charset.forName("GBK");
        //获取编码器
        CharsetEncoder ce = cs1.newEncoder();
        //获取解码器
        CharsetDecoder cd = cs1.newDecoder();
        //创建buffer，并存入文字
        CharBuffer cBuff = CharBuffer.allocate(1024);
        cBuff.put("疾风知劲草");
        cBuff.flip();
        //编码，字符-字节
        ByteBuffer bBuf =  ce.encode(cBuff);
        //查看是否编码成功
        for(int i = 0; i < 10; i++){
            System.out.println(bBuf.get());
        }
        //解码，字节-字符
        bBuf.flip();
        CharBuffer cBuf2 = cd.decode(bBuf);
        System.out.println(cBuf2.toString());// 疾风知劲草
        System.out.println("=========================");
        //如果按照UTF-8解码，就会出现乱码
        Charset cs2 = Charset.forName("UTF-8");
        bBuf.flip();
        CharBuffer cBuf3 = cs2.decode(bBuf);
        System.out.println(cBuf3.toString());
    }
}
