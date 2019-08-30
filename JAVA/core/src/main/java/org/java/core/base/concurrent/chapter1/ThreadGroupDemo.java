package org.java.core.base.concurrent.chapter1;

public class ThreadGroupDemo {

    public static void main(String[] args) {
        Thread.currentThread().setName("xzj-main-thread");
        Thread t1 = new Thread("xzj-thread") {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();

        ThreadGroup threadGroup = t1.getThreadGroup();
        System.out.println("t1 thread group is " + threadGroup);
        threadGroup.list();
        System.out.println("---------------------");

        ThreadGroup systemThreadGroup = threadGroup.getParent();
        System.out.println("threadGroup parent is: " + systemThreadGroup);
        //列出线程组树形结构，只会打印出已经start的线程信息
        systemThreadGroup.list();
    }

}
