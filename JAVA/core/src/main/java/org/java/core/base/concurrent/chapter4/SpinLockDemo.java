package org.java.core.base.concurrent.chapter4;

/**
 * 为了解决StackOverFlowError,
 */
public class SpinLockDemo {
    int count = 0;
    public void incr(){
        count++;
        System.out.println("执行了: " + count + "次");
    }

    public static void main(String[] args){
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        while(spinLockDemo.count != 1000000){//这段代码其实就是一个自旋锁
            // 需要注意的是：自旋锁不是真正的锁，其只是解决思路的一种方式，
            // 只要不能继续往下执行，就不断的循环。
            spinLockDemo.incr();
        }
        System.out.println("执行其他代码..");
    }
}
