package org.netty.core.demo;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * 以 ByteBuffer 类为例,也就是字节缓冲.
 */
public class ByteBufferDemo {

    public static void main(String[] args) {

//        createByteBuffer();

//         copyDataFromByteBufferToByteArray();
//        testMark();
    }

    /**
     * 缓冲区是由allocate或wrap操作创建的:
     *
     *      1. allocate创建一个缓冲区对象并分配一个私有的空间来储存数据元素。
     *      2. wrap操作创建一个缓冲区对象但是不分配任何空间来储存数据元素。
     *      它使用您所提供的数组作为存储空间来储存缓冲区中的数据元素。
     */
    private static void createByteBuffer() {
        // 方式1：allocate方式直接分配，内部将隐含的创建一个数组
        // 这段代码隐含地从堆空间中分配了一个 byte 型数组作为备份存储器来储存 10 个 byte变量
        ByteBuffer allocate = ByteBuffer.allocate(10);

        //方式2：通过wrap根据一个已有的数组创建
        // 这意味着通过调用put()函数造成的对缓冲区的改动会直接影响这个数组，
        // 而且对这个数组的任何改动也会对这个缓冲区对象可见
        byte[] bytes = new byte[20];
        ByteBuffer wrap = ByteBuffer.wrap(bytes);

        // 方式3：通过wrap根据一个已有的数组指定区间创建
        // 带有 offset 和 length 作为参数的 wrap()函数会构造一个
        // 按照您提供的 offset 和 length参数值初始化位置(position)和上界(limit)的缓冲区。
        // 这个函数并不像您可能认为的那样，创建了一个只占用了一个数组子集的缓冲区。
        // 这个缓冲区可以存取这个数组的全部范围； offset 和 length 参数只是设置了初始的状态。
        ByteBuffer wrapoffset = ByteBuffer.wrap(bytes,2,5);

        //打印出刚刚创建的缓冲区的相关信息
        print(allocate, wrap, wrapoffset);
    }

    private static void print(Buffer... buffers) {
        for (Buffer buffer : buffers) {
            System.out.println("capacity="+buffer.capacity()
                    +",limit="+buffer.limit()
                    +",position="+buffer.position()
                    +",hasRemaining:"+buffer.hasRemaining()
                    +",remaining="+buffer.remaining()
                    +",hasArray="+buffer.hasArray()
                    +",isReadOnly="+buffer.isReadOnly()
                    +",arrayOffset="+buffer.arrayOffset());
        }
    }

    /**
     * 把数据从ByteBuffer拷贝到ByteArray中
     */
    private static void copyDataFromByteBufferToByteArray(){
        String str = "Hello";
        ByteBuffer buffer = ByteBuffer.allocate(1024); // 分配缓冲区
        buffer.put(str.getBytes()); // 把str字符串存储到buffer缓冲区中
        buffer.flip();// 从写模式切换到读模式
        byte [] dst = new byte[buffer.limit()];// (buffer.limit()就是刚刚写模式下的position，即写入的元素的个数)
        buffer.get(dst); // 将buffer中的数据存放到dst中
        System.out.println("dst: " + new String(dst));
        buffer.rewind();// rewind()把position设为0，mark设为-1，不改变limit的值,这样我们可以重复读取buffer中的数据。
        //clear():清空缓冲区,但是缓冲区中的数据依然被存在，但是数据处于“被遗忘”状态
        buffer.clear();
    }

    private static void testMark() {
        String str = "abcde";
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(str.getBytes());
        buffer.flip();// 从写模式切换到读模式.
        System.out.println("position:"+buffer.position()+", limit: " + buffer.limit());

        // 第一次读数据
        byte[] dst = new byte[buffer.limit()];
        buffer.get(dst,0,2);// 把buffer中0-2的数据读到dst中.
        System.out.println("第一次读数据导dst的0-2: " + new String(dst,0,2));

        System.out.println("mark标记前的position====="+buffer.position());// position的值2
        buffer.mark();

        //第二次读数据
        buffer.get(dst,2,2);
        System.out.println("第一次读数据导dst的2-4:"+ new String(dst,2,2));
        System.out.println("第二次读取数据后的position====="+buffer.position());

        // reset()方法后
        buffer.reset();// reset到了之前调用mark的时候的postion.
        System.out.println("reset方法后的position====="+buffer.position());
        //判断缓冲区是否还有数据
        if(buffer.hasRemaining()){
            //输出还有数据的数量
            System.out.println("remaining: " + buffer.remaining());
        }
    }
}
