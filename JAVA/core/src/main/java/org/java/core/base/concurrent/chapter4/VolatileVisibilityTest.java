package org.java.core.base.concurrent.chapter4;

public class VolatileVisibilityTest {

    private static int number = 10;
    // private volatile static int number = 10;

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "-----------> coming in");
            // 暂停线程一会儿
            try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
            number = 200;
            // 把number从当前线程的工作内存中写回到主内存中
            System.out.println(Thread.currentThread().getName() + "-----------> change number: " + number);
        }, "AAA").start();

        while (number == 10) {
            // 注意这里如果读取number的话,就会从主内存中从新读取,如果不读取的话,main线程是不知道number更改的,会一直死循环下去
            // 需要一种通知机制告诉main线程,number已经被修改为200了
            // 可以把这行取消注释之后看看效果
           // System.out.println(Thread.currentThread().getName() + "  ---->  number: " + number);
        }
        System.out.println(Thread.currentThread().getName() + " is over!!");
    }
}
