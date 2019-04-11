package com.java.algorithm.atomic;

import sun.misc.Unsafe;

/**
 * 为了解决多线程访问Integer变量导致结果不正确所设计的一个基于多线程
 * 并且支持原子操作的Integer类
 *
 * 缺点：虽然AtomicInteger中的cas操作可以实现非阻塞的原子操作，但是会产生ABA问题
 *
 * 性能对比: 未完
 * @since 1.5
 */
public class MyAtomicInteger extends Number implements java.io.Serializable {

    private static final long serialVersionUID = 6214790243416807050L;

    // setup to use Unsafe.compareAndSwapInt for updates
    // 虽然Unsafe类中所有方法都是public的，但是这个类只能在被JDK信任的类中使用

    // Unsafe类可以执行以下几种操作：
    // 1. 分配内存，释放内存：在方法allocateMemory，reallocateMemory，freeMemory中，有点类似c中的malloc，free方法
    // 2. 可以定位对象的属性在内存中的位置，可以修改对象的属性值。使用objectFieldOffset方法
    // 3. CAS操作(CompareAndSwap方法是原子的，比较并交换,可用来实现高性能的、无锁的数据结构,CAS原语可以用来实现无锁的数据结构)

    // Unsafe中的int类型的CAS操作方法：
    // public final native boolean compareAndSwapInt(Object o, long offset, int expected, int x);
    // 参数o就是要进行cas操作的对象，offset参数是内存位置，expected参数就是期望的值，x参数是需要更新到的值。
    // 也就是说，如果我把1更新到2的话，需要这样调用：
    // compareAndSwapInt(this, valueOffset, 1, 2)
    // valueOffset字段表示内存位置，可以在AtomicInteger对象中使用unsafe得到：

    // AtomicInteger中用的就是Unsafe的CAS操作。
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long valueOffset;

    static {
        try {
            valueOffset = unsafe.objectFieldOffset
                    (MyAtomicInteger.class.getDeclaredField("value"));
        } catch (Exception ex) { throw new Error(ex); }
    }

    // AtomicInteger内部使用变量value表示当前的整型值，
    // 这个整型变量还是volatile的，表示内存可见性，一个线程修改value之后保证对其他线程的可见性：
    private volatile int value;

    public MyAtomicInteger(int initialValue) {
        value = initialValue;
    }

    public MyAtomicInteger() {
    }

    public final int get() {
        return value;
    }

    public final void set(int newValue) {
        value = newValue;
    }

    /**
     * Atomically sets to the given value and returns the old value.
     * 自动的设置给定的值，然后返回旧的值
     */
    public final int getAndSet(int newValue) {
        return unsafe.getAndSetInt(this, valueOffset, newValue);
    }

    // CAS
    public final boolean compareAndSet(int expect, int update) {
        return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
    }

    /**
     * Atomically increments by one the current value.
     * 以原子方式将当前值增加1。
     */
    public final int getAndIncrement() {
        return unsafe.getAndAddInt(this, valueOffset, 1);
    }

    /**
     * 以原子方式将当前值减少1。
     */
    public final int getAndDecrement() {
        return unsafe.getAndAddInt(this, valueOffset, -1);
    }

    /**
     * Atomically adds the given value to the current value.
     * 以原子方式将当前值增加delta
     */
    public final int getAndAdd(int delta) {
        return unsafe.getAndAddInt(this, valueOffset, delta);
    }

    /**
     * Atomically increments by one the current value.
     * 自动的把当前的值+1,然后返回
     * @return the updated value 返回更新的值
     */
    public final int incrementAndGet() {
        // JDK本身只有Unsafe.class，只通过class文件中的参数名，并不能很好的了解Unsafe方法的作用，
        // 可以我们通过OpenJDK 8 来查看Unsafe的源码,下面附上源码
        return unsafe.getAndAddInt(this, valueOffset, 1) + 1;
    }

    // ------------------------- OpenJDK 8 -------------------------
    // OpenJDK8中的Unsafe.java源码
//    public final int getAndAddInt(Object o, long offset, int delta) {
//        int v;
//        do {

    // 1. 设定 AtomicInteger 的 value 原始值为 A，从 Java 内存模型得知，线程 1 和线程 2 各自持有一份 value 的副本，值都是 A。
    // 2. 线程 1 通过getIntVolatile(var1, var2)拿到 value 值 A，这时线程 1 被挂起。
    // 3. 线程 2 也通过getIntVolatile(var1, var2)方法获取到 value 值 A，
    // 并执行compareAndSwapInt方法比较内存值也为 A，成功修改内存值为 B。
    // 4. 这时线程 1 恢复执行compareAndSwapInt方法比较，发现自己手里的值 A 和内存的值 B 不一致，
    // 说明该值已经被其它线程提前修改过了。
    // 5. 线程 1 重新执行getIntVolatile(var1, var2)再次获取 value 值，因为变量 value 被 volatile 修饰，
    // 所以其它线程对它的修改，线程 A 总是能够看到，线程A继续执行compareAndSwapInt进行比较替换，直到成功。

    // 整个“比较+更新”操作封装在compareAndSwapInt()中，
    // 在JNI里是借助于一个CPU指令完成的，属于原子操作，可以保证多个线程都能够看到同一个变量的修改值。
    // 后续JDK通过CPU的cmpxchg指令，去比较寄存器中的值和当前线程手里的value的副本(内存中的值V)。如果相等，
    // 就把要写入的新值B存入内存中。如果不相等，就返回false，当前线程会重新获取value的副本.
    // 然后再次调用cmpxchg指令进行重试，直到设置成功为止。

//            v = getIntVolatile(o, offset);// 某一个线程手里拿到了对象o在offset偏移出值的一个副本.
//        } while (!compareAndSwapInt(o, offset, v, v + delta));// 第三个参数是expect，第4个参数是update
//        return v; // 将旧值返回
//    }

    /**
     * Atomically decrements by one the current value.
     * @return the updated value
     */
    public final int decrementAndGet() {
        return unsafe.getAndAddInt(this, valueOffset, -1) - 1;
    }

    /**
     * Atomically adds the given value to the current value.
     *
     * @param delta the value to add
     * @return the updated value
     */
    public final int addAndGet(int delta) {
        return unsafe.getAndAddInt(this, valueOffset, delta) + delta;
    }

    // 以前jdk的实现:
    // 先得到当前的值value，然后再把当前的值加delta，加完之后使用cas原子操作让当前值加一处理正确
    // 当然cas原子操作不一定是成功的，所以做了一个死循环，当cas操作成功的时候返回数据。这里由于使用了cas原子操作，所以不会出现多线程处理错误的问题。比如线程A得到current为1，线程B也得到current为1；线程A的next值为2，进行cas操作并且成功的时候，将value修改成了2；这个时候线程B也得到next值为2，当进行cas操作的时候由于expected值已经是2，而不是1了；所以cas操作会失败，下一次循环的时候得到的current就变成了2；也就不会出现多线程处理问题了：
//    public final int addAndGet(int delta) {
//        for (;;) {
//            int current = get();
//            int next = current + delta;
//            if (compareAndSet(current, next))
//                return next;
//        }
//    }

    public String toString() {
        return Integer.toString(get());
    }

    public int intValue() {
        return get();
    }

    public long longValue() {
        return (long)get();
    }

    public float floatValue() {
        return (float)get();
    }

    public double doubleValue() {
        return (double)get();
    }

}
