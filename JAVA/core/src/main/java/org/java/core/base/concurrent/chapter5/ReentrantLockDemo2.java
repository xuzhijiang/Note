package org.java.core.base.concurrent.chapter5;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo2 {

    public static void main(String[] args){
        // 注意这里直接定义了ReentrantLock，通过直接使用这个类而不是Lock接口，
        // 我们可以使用其独有的方法getHoldCount()，这个方法表示的是当前线程持有锁的次数
        ReentrantLock lock = new ReentrantLock();

        System.out.println(lock.getHoldCount());// 没有调用lock之前，hold count为0
        lock.lock();//holdCount+1
        System.out.println(lock.getHoldCount());
        lock.lock();//holdCount+1
        System.out.println(lock.getHoldCount());
        lock.unlock();//holdCount-1
        System.out.println(lock.getHoldCount());
        lock.unlock();//holdCount-1
        System.out.println(lock.getHoldCount());
        // 通过输出，我们可以看到，一个线程(本案例就是主线程)的确可以多次持有同一个锁。
        // 每当调用lock方法时，次数+1，每当调用unlock方法时，次数-1。我们看到出现了2，
        // 这事实上就体现了所谓的可重入。需要注意的是：当getHoldCount()不为0的时候，
        // 表示锁当前正在被某个线程使用，只有当其为0的时候，其他线程才有机会获取这个锁。
    }
}
