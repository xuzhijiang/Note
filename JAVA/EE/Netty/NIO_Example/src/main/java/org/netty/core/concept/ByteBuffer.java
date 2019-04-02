package org.netty.core.concept;


public abstract class ByteBuffer {

    //缓冲区创建相关api
//    public static ByteBuffer allocateDirect(int capacity)
//    public static ByteBuffer allocate(int capacity)
//    public static ByteBuffer wrap(byte[] array)
//    public static ByteBuffer wrap(byte[] array,int offset, int length)

    // 存储操作是通过get和put操作进行的，get 和 put 可以是相对的或者是绝对的。
    // 在前面的程序列表中，相对方案是不带有索引参数的函数。当相对函数被调用时，位置在返回时前进一。
    // 如果位置前进过多，相对运算就会抛 出 异 常 。
    // 对 于 put() ， 如 果 运 算 会 导 致 位 置 超 出 上 界 ，
    // 就 会 抛 出BufferOverflowException 异常。对于 get()，如果位置不小于上界，
    // 就会抛出BufferUnderflowException 异常。
    // 绝对存取不会影响缓冲区的位置属性，但是如果您所提供的索引超出范围（负数或不小于上界），
    // 也将抛出 IndexOutOfBoundsException 异常。

    //缓存区存取相关API
//    public abstract byte get( );//从当前位置position上get，get之后，position会自动+1
//    public abstract byte get (int index);//从绝对位置get
//    public abstract ByteBuffer put (byte b);//从当前位置上普通，put之后，position会自动+1
//    public abstract ByteBuffer put (int index, byte b);//从绝对位置上put

    // 下面我们通过详细的案例说明，如何创建缓冲区，以及对缓存区进行操作：BufferCreateDemo

}