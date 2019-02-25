package org.java.core.base.concurrent.chapter1;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class ThreadNumDemo {

    public static void main(String[] args) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        for(ThreadInfo threadInfo : threadInfos) {
            System.out.println("thread id: " + threadInfo.getThreadId() + " - thread name: " + threadInfo.getThreadName());
        }
        // 这段代码的作用是打印出当前JVM中运行的所有线程信息，这使用到了JMX的API
        // 可以看到，当一个java进程启动的时候至少会开启以上的这些线程。
        // 不过需要注意的是，这是在JDK1.8中运行的结果，不同版本的JDK运行的结果可能会不同。

        // thread id: 6 - thread name: Monitor Ctrl-Break
        //thread id: 5 - thread name: Attach Listener
        //thread id: 4 - thread name: Signal Dispatcher//信号分发线程,是发送给JVM的
        //thread id: 3 - thread name: Finalizer//调用对象的finalize方法的线程，就是垃圾回收的线程
        //thread id: 2 - thread name: Reference Handler//清除reference的线程（清除引用的线程）
        //thread id: 1 - thread name: main

        // 希望读者可以简单的记忆一下这些线程，毕竟面试的时候会被经常问到这个问题，
        // 如果你都能说出来，肯定会加分的。
    }
}
