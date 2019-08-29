package org.java.core.base.concurrent.chapter2;

import java.util.concurrent.locks.LockSupport;

/**
 * 分析线程的状态有什么用处?
 * 1. 线程B处于BLOCKED状态，这个时候可以分析一下是不是线程A加锁的时候忘记释放了，或者释放的时机不对。导致线程B一直处于BLOCKED状态
 * 2. 再比如线程A处于WAITING状态，这个时候可以分析一下是不是线程B的notifyAll或者signalAll方法的调用时机是否不对。
 */

// Java线程的生命周期中，存在几种状态
// 在Thread类中定义了一个枚举类型叫State，State这个枚举定义了线程的几种状态
enum State {

    // 线程还没有启动的时候的状态
    /**
     * Thread state for a thread which has not yet started.
     * 当我们用new 操作符new一个Thread对象的时候，线程状态是New,线程还不是活跃的,就只是一个java对象而已.
     */
    NEW,

    /**
     * 当我们调用了Thread.start()方法的时候,状态从NEW变成了RUNNABLE,
     * 控制权转移到了操作系统线的程调度器上,线程调度器去决定什么开始执行,
     * 是否去立刻运行这个线程,还是运行它之前把它保留在可以运行的线程池中,
     * 依赖于具体操作系统的线程调度器的实现.
     * When we call start() function on Thread object,
     * it’s state is changed to Runnable. The control is given to
     * Thread scheduler to finish it’s execution. Whether to run this
     * thread instantly or keep it in runnable thread pool before running,
     * depends on the OS implementation of thread scheduler.
     *
     * 在jvm上正在运行任务的线程的状态。在RUNNABLE状态下的线程可能会正在等待操作系统的资源，比如处理器
     * Thread state for a runnable thread.  A thread in the runnable
     * state is executing in the Java virtual machine but it may
     * be waiting for other resources from the operating system
     * such as processor.
     */
    RUNNABLE,

    /**
     * 阻塞状态，等待锁的释放，比如线程A进入了一个synchronized方法，线程B也想进入这个方法，
     * 但是这个方法的锁已经被线程A获取了，这个时候线程B就处于BLOCKED状态
     * Thread state for a thread blocked waiting for a monitor lock.
     * A thread in the blocked state is waiting for a monitor lock
     * to enter a synchronized block/method or
     * reenter a synchronized block/method after calling
     */
    BLOCKED,

    /**
     * 等待状态，处于等待状态的线程是由于执行了3个方法中的任意方法。
     * 1. Object的wait方法，并且没有使用timeout参数;
     * 2. Thread的join方法，没有使用timeout参数
     * 3. LockSupport的park方法。
     * 处于waiting状态的线程会等待另外一个线程处理特殊的行为。
     * 再举个例子，如果一个线程调用了一个对象的wait方法，那么这个线程就会处于waiting状态直到
     * 另外一个线程调用这个对象的notify或者notifyAll方法后才会解除这个状态
     * Thread state for a waiting thread.
     * A thread is in the waiting state due to calling one of the
     * following methods:
     * <ul>
     *   <li>{@link Object#wait() Object.wait} with no timeout</li>
     *   <li>{@link Thread#join() Thread.join} with no timeout</li>
     *   <li>{@link LockSupport#park() LockSupport.park}</li>
     * </ul>
     *
     * <p>A thread in the waiting state is waiting for another thread to
     * perform a particular action.
     *
     * For example, a thread that has called <tt>Object.wait()</tt>
     * on an object is waiting for another thread to call
     * <tt>Object.notify()</tt> or <tt>Object.notifyAll()</tt> on
     * that object. A thread that has called <tt>Thread.join()</tt>
     * is waiting for a specified thread to terminate.
     */
    WAITING,

    /**
     * 有等待时间的等待状态，比如调用了以下几个方法中的任意方法，并且指定了等待时间，
     * 线程就会处于这个状态。
     * 1. Thread.sleep方法
     * 2. Object的wait方法，带有时间
     * 3. Thread.join方法，带有时间
     * 4. LockSupport的parkNanos方法，带有时间
     * 5. LockSupport的parkUntil方法，带有时间
     *
     * Thread state for a waiting thread with a specified waiting time.
     * A thread is in the timed waiting state due to calling one of
     * the following methods with a specified positive waiting time:
     * <ul>
     *   <li>{@link Thread#sleep Thread.sleep}</li>
     *   <li>{@link Object#wait(long) Object.wait} with timeout</li>
     *   <li>{@link Thread#join(long) Thread.join} with timeout</li>
     *   <li>{@link LockSupport#parkNanos LockSupport.parkNanos}</li>
     *   <li>{@link LockSupport#parkUntil LockSupport.parkUntil}</li>
     * </ul>
     */
    TIMED_WAITING,

    /**
     * 线程中止的状态，这个线程已经执行完了任务
     * Thread state for a terminated thread.
     * The thread has completed execution.
     * it’s considered to be not alive.
     */
    TERMINATED;
}
// 对BLOCKED,WAITING,TIMED_WAITING说明:
// Once the thread LOCKED/WAITING/TIMED_WAITING state is over,
// it’s  state is changed to Runnable and it’s moved back to runnable thread pool.