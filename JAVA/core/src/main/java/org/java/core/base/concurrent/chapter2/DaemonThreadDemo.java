package org.java.core.base.concurrent.chapter2;

import org.junit.Test;

public class DaemonThreadDemo {

    @Test
    public void createDeamonThread() throws InterruptedException {
        Thread t = new Thread(() -> {
            System.out.println("我是Daemon Thread");
            // 在守护线程中产生的新线程也是守护线程
        }, "Daemon Thread");
        //设置守护线程,setDaemon(true)必须在start()之前设置
        t.setDaemon(true);
        t.start();

        Thread.sleep(5000L);
    }
}
