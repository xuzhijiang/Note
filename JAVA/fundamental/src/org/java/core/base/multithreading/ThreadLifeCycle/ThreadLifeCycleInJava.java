package org.java.core.base.multithreading.ThreadLifeCycle;

/**
 * 理解Java中线程的生命周期以及线程的状态是对于多线程程序的环境是
 * 非常重要的，在Java中创建一个thread，并且start它，但是thread
 * 从Runnable到Running再到Blocked依赖于OS线程调度器(thread scheduler)
 * 的实现，Java不能完全控制它.
 * <p>
 * <strong>New</strong>
 * 当我们用new 操作符new一个Thread对象的时候，线程状态是New Thread.at this point,
 * 线程还不是活的，它只是java程序内部的状态
 * <p>
 * <strong>Runnable</strong>
 * When we call start() function on Thread object, 
 * it’s state is changed to Runnable. The control is given to 
 * Thread scheduler to finish it’s execution. Whether to run this 
 * thread instantly or keep it in runnable thread pool before running, 
 * depends on the OS implementation of thread scheduler.
 * <p>
 * <p>Blocked/Waiting</p>
 * A thread can be waiting for other thread to finish using 
 * thread join or it can be waiting for some resources to 
 * available. For example producer consumer problem or waiter 
 * notifier implementation or IO resources, then it’s state is 
 * changed to Waiting. Once the thread wait state is over, it’s 
 * state is changed to Runnable and it’s moved back to runnable thread pool.
 * <p>
 * <strong>Dead</strong>
 * Once the thread finished executing, it’s state is changed to 
 * Dead and it’s considered to be not alive. 
 * <p>
 * Above are the different states of thread. It’s good to know 
 * them and how thread changes it’s 
 * state. That’s all for thread life cycle in java.
 */
public class ThreadLifeCycleInJava {
	
}
