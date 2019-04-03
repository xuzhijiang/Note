## DirectByteBuffer内存释放

在网络编程中，为了避免频繁的在用户空间与内核空间拷贝数据，
通常会直接从内核空间中申请内存，存放数据，`在Java中，把内核空间的内存称之为直接内存`，
`nio包中的ByteBuffer的allocateDirect方法，就是申请直接内存的`，如下所示：

```java
public static ByteBuffer allocateDirect(int capacity) {
    // 返回的是一个DirectByteBuffer对象，其是ByteBuffer的子类，对于直接内存的分配，就是在这个类中实现的。
    return new DirectByteBuffer(capacity);
}
```

有经验的读者可能知道，在java中，直接内存的申请与释放是通过Unsafe类的allocateMemory方法和freeMemory方法来实现的，
且对于直接内存的释放，必须手工调用freeMemory方法，`因为JVM只能帮我们管理堆内存，直接内存不在其管理范围之内。`

>DirectByteBuffer帮我们简化了直接内存的使用，我们不需要直接操作Unsafe类来进行直接内存的申请与释放，那么其是如何实现的呢？

1. 直接内存的申请:在DirectByteBuffer实例通过构造方法创建的时候，会通过Unsafe类的allocateMemory方法 帮我们申请直接内存资源。
2. 直接内存的释放:DirectByteBuffer本身是一个Java对象，其是位于堆内存中的，JDK的GC机制可以自动帮我们回收，但是其申请的直接内存，不再GC范围之内，无法自动回收。好在JDK提供了一种机制，可以为堆内存对象注册一个钩子函数
(其实就是实现Runnable接口的子类)，当堆内存对象被GC回收的时候，会回调run方法，我们可以在这个方法中执行释放DirectByteBuffer引用的直接内存，即在run方法中调用Unsafe 的freeMemory方法。注册是通过sun.misc.Cleaner类来实现的。

```java
// Cleaner.create方法声明如下所示：
// 其中第一个参数是一个堆内存对象，第二个参数是一个Runnable任务，表
// 示这个堆内存对象被回收的时候，需要执行的回调方法。
public static Cleaner create(Object heapObj, Runnable task)
```

### 关于System.gc对直接内存释放的影响

细心的读者，可能注意到了，在DirectByteBuffer实例创建的时候，分配内存之前调用了Bits.reserveMemory方法，如果分配失败调用了Bits.unreserveMemory，同时在Deallocator释放完直接内存的时候，也调用了Bits.unreserveMemory方法。这两个方法，主要是记录jdk已经使用的直接内存的数量，当分配直接内存时，需要进行增加，当释放时，需要减少，源码如下：Bits.java

>通过代码的分析，我们事实上可以认为Bits类是直接内存的分配担保，当有足够的直接内存可以用时，增加直接内存应用计数，否则，调用System.gc，进行垃圾回收，需要注意的是，System.gc只会回收堆内存中的对象，但是我们前面已经讲过，DirectByteBuffer对象被回收时，那么其引用的直接内存也会被回收，试想现在刚好有其他的DirectByteBuffer可以被回收，那么其被回收的直接内存就可以用于本次DirectByteBuffer直接的内存的分配。

因此我们经常看到，有些文章讲解在使用Nio的时候，不要禁用System.gc，也就是启动JVM的时候，不要传入`-XX:+DisableExplicitGC参数`，因为这样可能会造成直接内存溢出。道理很明显，
因为直接内存的释放与获取比堆内存更加耗时，每次创建DirectByteBuffer实例分配直接内存的时候，都调用System.gc，可以让已经使用完的DirectByteBuffer得到及时的回收。

虽然System.gc只是建议JVM去垃圾回收，可能JVM并不会立即回收，但是频繁的建议，JVM总不会视而不见。不过，这并不是绝对的，因为System.gc导致的是FullGC，可能会暂停用户线程，也就是JVM不能继续响应用户的请求，对于一些要求延时比较短的应用，是不希望JVM频繁的进行FullGC的。

>所以笔者的建议是：禁用System.gc，调大最大可以使用的直接内存。如：`-XX:+DisableExplicitGC -XX:MaxDirectMemorySize=256M`