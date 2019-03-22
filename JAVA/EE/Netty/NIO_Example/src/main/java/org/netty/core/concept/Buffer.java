package org.netty.core.concept;

public abstract class Buffer {

    // ...
    // Invariants: mark <= position <= limit <= capacity
    private int mark = -1;
    private int position = 0;
    private int limit;
    private int capacity;
    // ...

    // Buffer类定义了所有缓冲区实现类需要实现的方法，以下列出的只是这些方法的签名，不包含实现

    //JDK1.4时，引入的api
//    public final int capacity()//返回此缓冲区的容量
//    public final int position( )//返回此缓冲区的位置
//    public final Buffer position (int newPositio)//设置此缓冲区的位置
//    public final int limit( )//返回此缓冲区的限制
//    public final Buffer limit (int newLimit)//设置此缓冲区的限制
//    public final Buffer mark( )//在此缓冲区的位置设置标记
//    public final Buffer reset( )//将此缓冲区的位置重置为以前标记的位置
//    public final Buffer clear( )//清除此缓冲区
//    public final Buffer flip( )//反转此缓冲区
//    public final Buffer rewind( )//重绕此缓冲区
//    public final int remaining( )//返回当前位置与限制之间的元素数
//    public final boolean hasRemaining( )//告知在当前位置和限制之间是否有元素
//    public abstract boolean isReadOnly( );//告知此缓冲区是否为只读缓冲区

    //JDK1.6时引入的api
//    public abstract boolean hasArray();//告知此缓冲区是否具有可访问的底层实现数组
//    public abstract Object array();//返回此缓冲区的底层实现数组
//    public abstract int arrayOffset();//返回此缓冲区的底层实现数组中第一个缓冲区元素的偏移量
//    public abstract boolean isDirect();//告知此缓冲区是否为直接缓冲区

    // Buffer类的七种基本数据类型的缓冲区实现也都是抽象的，这些类没有一种能够直接实例化。
    // 7种:ByteBuffer, DoubleBuffer, FloatBuffer,LongBuffer,ShortBuffer,IntBuffer,CharBuffer

    // 上文所列出的的 Buffer API 并没有包括存取函数。
    // 这些方法(存取函数)都定义在Buffer类的子类中。子类包含了静态工厂方法用来创建相应类的新实例。
    // 以及get和put等操作来实现缓存区的存取。
    // 我们以ByteBuffer类为例进行说明：
}
