Java Thread dump is list of all the threads active in the JVM.
Java Thread dump是所有活跃在JVM中的线程的列表.

Java thread dump is very helpful in analyzing bottlenecks 
in the application and deadlock situations.
Java thread dump是一个非常有用的在分析应用的瓶颈以及死锁情形的时候.

Here we will learn multiple ways through 
which we can generate thread dump for a java program. 
These instructions are valid for *nix operating 
systems but in windows the steps might be little different.
这里我们将会学到很多种方式，通过这些方式我们可以生成thread dump为一个java程序，
这些命令对于*nix操作系统是有效的，但是对于windows，这些步骤有可能不同.

VisualVM Profiler: If you are analyzing application for slowness, 
you must use a profiler. We can generate thread dump for 
any process using VisualVM profiler very easily. 
You just need to right click on the running process 
and click on “Thread Dump” option to generate it.
如果您正在分析应用程序缓慢的原因，你必须使用一个分析器。 我们可以为任何的进程用VisualVM分析器非常容易的生成线程dump
您只需右键单击正在运行的进程即可，并单击“Thread Dump”选项以生成它。

jstack: Java comes with jstack tool through which 
we can generate thread dump for a java process. 
This is a two step process.

	Find out the PID of the java process using ps -eaf | grep java command

	Run jstack tool as jstack PID to generate the thread 
	dump output to console, you can append thread dump output 
	to file using command “jstack PID >> mydumps.tdump“

We can use kill -3 PID command to generate the thread dump. 
This is slightly different from other ways to generate thread dump.
When kill command is issued, thread dump is generated to the System 
out of the program. So if it’s a java program with console as system
out, the thread dump will get printed on the console. If the java 
program is a Tomcat server with system out as catalina.out, then 
thread dump will be generated in the file.

Java 8 has introduced jcmd utility. You should use this instead of 
jstack if you are on Java 8 or higher. Command to generate 
thread dump using jcmd is jcmd PID Thread.print.
Above are four different ways to generate thread dump in 
java. Usually I prefer jstack or jcmd command to generate 
thread dump and analyze. Note that whatever way you choose, 
thread dump will always be the same.

Thread dump is the list of all the threads, every 
entry shows information about thread which includes following in the order of appearance.

Thread Name: Name of the Thread
Thread Priority: Priority of the thread
Thread ID: Represents the unique ID of the Thread
Thread Status: Provides the current thread state, for 
example RUNNABLE, WAITING, BLOCKED. While analyzing 
deadlock look for the blocked threads and resources 
on which they are trying to acquire lock.

Thread callstack: Provides the vital stack information 
for the thread. This is the place where we can see the 
locks obtained by Thread and if it’s waiting for any lock.

# Java线程状态分析

```java
// Java线程的生命周期中，存在几种状态。在Thread类里有一个枚举类型State，定义了线程的几种状态
public enum State {
    /**
     * Thread state for a thread which has not yet started.
     * 线程创建之后，但是还没有启动(not yet started)。这时候它的状态就是NEW
     */
    NEW,

    /**
     * 正在Java虚拟机下跑任务的线程的状态。在RUNNABLE状态下的线程可能会等待系统资源的释放，比如IO
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
     *   <li>{@link #join() Thread.join} with no timeout</li>
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
     * 有等待时间的等待状态，比如调用了以下几个方法中的任意方法，并且指定了等待时间，线程就会处于这个状态。 
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
     *   <li>{@link #sleep Thread.sleep}</li>
     *   <li>{@link Object#wait(long) Object.wait} with timeout</li>
     *   <li>{@link #join(long) Thread.join} with timeout</li>
     *   <li>{@link LockSupport#parkNanos LockSupport.parkNanos}</li>
     *   <li>{@link LockSupport#parkUntil LockSupport.parkUntil}</li>
     * </ul>
     */
    TIMED_WAITING,

    /**
     * 线程中止的状态，这个线程已经完整地执行了它的任务
     * Thread state for a terminated thread.
     * The thread has completed execution.
     */
    TERMINATED;
}
```

>了解线程的状态可以分析一些问题:

* 比如线程处于BLOCKED状态，这个时候可以分析一下是不是lock加锁的时候忘记释放了，或者释放的时机不对。导致另外的线程一直处于BLOCKED状态。
* 比如线程处于WAITING状态，这个时候可以分析一下notifyAll或者signalAll方法的调用时机是否不对。

>java自带的jstack工具可以分析查看线程的状态、优先级、描述等具体信息。