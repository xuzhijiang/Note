package org.java.core.base.concurrent.chapter5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition 实现可以提供不同于 Object 监视器方法的行为和语义，
 * 比如受保证的通知排序，或者在执行通知时不需要保持一个锁。
 * 如果某个实现提供了这样特殊的语义，则该实现必须记录这些语义。
 *
 * 注意，Condition 实例只是一些普通的对象，它们自身可以用作 synchronized 语句中的目标，
 * 并且可以调用自己的 wait 和 notification 监视器方法。
 * 获取 Condition 实例的监视器锁或者使用其监视器方法，
 * 与获取和该 Condition 相关的 Lock 或使用其 waiting 和 signalling 方法没有什么特定的关系。
 * 为了避免混淆，建议除了在其自身的实现中之外，切勿以这种方式使用 Condition 实例。
 */
public class BoundedBuffer {

    final Lock lock = new ReentrantLock();
    // Condition 实例实质上被绑定到一个锁上。
    // 要为特定 Lock 实例获得 Condition 实例，可以使用其 newCondition() 方法。
    final Condition notFull = lock.newCondition();
    // 我们喜欢在单独的等待 set 中保存 put 线程和 take 线程，
    // 这样就可以在缓冲区中的项或空间变得可用时利用最佳规划，一次只通知一个线程。
    // 可以使用两个 Condition 实例来做到这一点。
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[100];
    int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException{
        lock.lock();
        try{
            // 如果试图在满的缓冲区上执行 put 操作，则在有空间变得可用之前，线程将一直阻塞。
            while(count == items.length)
                notFull.await();
            items[putptr] = x;//往缓存区中存放数据
            if (++putptr == items.length) putptr = 0;//修改下一个在存储中存放数据的位置
            ++count;//修改缓存区中数据的数量
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 作为一个示例，假定有一个有界的缓冲区，它支持 put 和 take 方法。
     * 如果试图在空的缓冲区上执行 take 操作，则在某一个项变得可用之前，线程将一直阻塞；
     * @return
     * @throws InterruptedException
     */
    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await();
            Object x = items[takeptr];
            if (++takeptr == items.length) takeptr = 0;
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}
