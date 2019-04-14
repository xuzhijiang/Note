package org.java.core.base.concurrent.chapter5.ReadWriteLock;

// 1. 在没有写操作的时候，两个线程同时读一个资源没有任何问题，所以应该允许多个线程能在同时读取共享资源。
// 2. 如果有一个线程想去写这些共享资源，就不应该再有其它线程对该资源进行读或写

// 归纳: 一个线程读，其他线程也可以读同一资源.
// 一个线程读资源，其他写线程就不能去写.
// 一个线程去写资源，其他的线程不能去读，也不能去写.

// 本例是不可重入的：即
// 当一个已经持有写锁的线程再次请求写锁时，就会被阻塞。
// 原因是已经有一个写线程了——就是它自己。此外，考虑下面的例子：
//
// Thread 1 获得了读锁
// Thread 2 请求写锁，但因为Thread 1 持有了读锁，所以写锁请求被阻塞。
// Thread 1 再想请求一次读锁，但因为Thread 2处于请求写锁的状态，所以想再次获取读锁也会被阻塞。

// 上面这种情形使用前面的ReadWriteLock就会被锁定——一种类似于死锁的情形。
// 不会再有线程能够成功获取读锁或写锁了。

public class ReadWriteLock1 { //本例实现的读/写锁(ReadWriteLock) 是不可重入的

    // 拥有读锁的线程的个数
    private int readers = 0;
    // 拥有写锁的线程的个数
    private int writers = 0;
    // 请求写锁的线程
    private int writeRequests = 0;

    /**
     * 读锁
     * @throws InterruptedException
     */
    public synchronized void lockRead()
            throws InterruptedException{
        // 只要没有线程拥有写锁（writers==0）
        // ，且没有线程在请求写锁（writeRequests ==0）
        // 所有想获得读锁的线程都能成功获取
        while(writers > 0 || writeRequests > 0){
            wait();
        }
        readers++;
    }

    public synchronized void unlockRead(){
        readers--;
        notifyAll();
    }

    public synchronized void lockWrite()
            throws InterruptedException{
        // 把写锁请求数加1(请求写锁的线程数)
        writeRequests++;

        // 判断是否能够真能获得写锁，当没有线程持有读锁（readers==0 ）,
        // 且没有线程持有写锁（writers==0）时就能获得写锁。有多少线程在请求写锁并无关系。
        while(readers > 0 || writers > 0){
            wait();
        }
        writeRequests--;
        writers++;
    }

    public synchronized void unlockWrite()
            throws InterruptedException{
        writers--;
        // 防止信号丢失
        notifyAll();
    }

}
