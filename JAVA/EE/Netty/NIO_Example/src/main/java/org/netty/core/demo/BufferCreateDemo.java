package org.netty.core.demo;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * 以 ByteBuffer 类为例,也就是字节缓冲.
 * 其它六种主要的缓冲区类也是适用的： IntBuffer， DoubleBuffer， ShortBuffer， LongBuffer， FloatBuffer，和 CharBuffer
 */
public class BufferCreateDemo {

    // 新的缓冲区是由allocate或wrap操作创建的:

    // 1. allocate操作创建一个缓冲区对象并分配一个私有的空间来储存容量大小的数据元素。
    // 2. wrap操作创建一个缓冲区对象但是不分配任何空间来储存数据元素。
    // 它使用您所提供的数组作为存储空间来储存缓冲区中的数据元素。
    public static void main(String[] args) {
        //方式1：allocate方式直接分配，内部将隐含的创建一个数组
        // 这段代码隐含地从堆空间中分配了一个 byte 型数组作为备份存储器来储存 10 个 byte变量
        ByteBuffer allocate = ByteBuffer.allocate(10);

        //方式2：通过wrap根据一个已有的数组创建
        // 这意味着通过调用put()函数造成的对缓冲区的改动会直接影响这个数组，
        // 而且对这个数组的任何改动也会对这个缓冲区对象可见
        byte[] bytes=new byte[20];
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
                    +",hasRemaining:"+buffer.hasArray()
                    +",remaining="+buffer.remaining()
                    +",hasArray="+buffer.hasArray()
                    +",isReadOnly="+buffer.isReadOnly()
                    +",arrayOffset="+buffer.arrayOffset());
            // arrayOffset()，返回缓冲区数据在数组中存储的开始位置的偏移量（从数组头 0 开始计算）。
            // 如果您使用了带有三个参数的版本的 wrap()函数来创建一个缓冲区，对于这个缓冲区，
            // arrayOffset()会一直返回 0
        }
    }

}
