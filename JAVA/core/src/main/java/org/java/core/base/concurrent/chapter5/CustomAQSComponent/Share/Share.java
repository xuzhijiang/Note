package org.java.core.base.concurrent.chapter5.CustomAQSComponent.Share;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 共享式组件:
 *
 * 第一步: 如果你打算在自己的独占式组件Share中调用
 * 内部类Sync的 acquireShared(int arg) 或者 acquireSharedInterruptibly(int arg) 或者 tryAcquireSharedNanos(int arg, long nanosTimeout)这三个AQS的模板方法,
 * 那么你就需要在Sync中重写AbstractQueuedSynchronizer的tryAcquireShared(int arg),在
 * tryAcquireShared(int arg)中利用CAS修改state来获取共享锁,如果Sync不实现tryAcquireShared这个方法,则会抛出UnsupportedOperationException
 *
 * 第二步: 如果你打算在自己的独占式组件Share中调用内部类Sync的releaseShared,
 * 就需要Sync内部类重写AQS的tryReleaseShared方法,来释放锁,如果不实现，则抛出UnsupportedOperationException
 *
 * 第三步: 还需要一个isHeldExclusively()方法需要覆写(查询当前同步器是否在独占模式下被占用)，不过其没有对应的模板方法。通常情况下，
 * 我们实现的同步组件要不就是独占式，要不就是共享式，但是也有例外，例如ReadWriteLock同时实现了独占式与共享式。
 *
 * 因此当我们需要实现一个独占式同步组件时，只需要覆写AQS的tryAcquire和tryRelease即可
 * 实现一个共享式同步组件的,只需要实现tryAcquireShared和tryReleaseShared即可
 * isHeldExclusively()是可选的。默认情况下，需要覆写的方法的实现都是抛出一个UnsupportedOperationException。
 */
public class Share {
    
    private Sync sync;
    
    public Share(int permits) {
        sync = new Sync(permits);    
    }
    
    public void acquire() {
        sync.acquireShared(1);
    }
    
    public void release() {
        sync.releaseShared(1);
    }
    
    private static class Sync extends AbstractQueuedSynchronizer {
        Sync (int permits) {
            setState(permits);
        }

        /**
         * 共享方式。尝试获取资源。负数表示失败；0表示成功，但没有剩余可用资源；正数表示成功，且有剩余资源。
         * @param acquires 要获取资源的数量
         * @return
         */
        @Override
        protected int tryAcquireShared(int acquires) {
            for (;;) {
                if (hasQueuedPredecessors())
                    return -1;
                int available = getState();
                int remaining = available - acquires;
                if (remaining < 0 ||
                        compareAndSetState(available, remaining))
                    return remaining;
            }
        }

        /**
         * 共享方式。尝试释放资源，成功则返回true，失败则返回false。
         * @param releases 归还资源的数量
         * @return
         */
        @Override
        protected boolean tryReleaseShared(int releases) {
            for (;;) {
                int current = getState();
                int next = current + releases;
                if (next < current) // overflow
                    throw new Error("Maximum permit count exceeded");
                if (compareAndSetState(current, next))
                    return true;
            }
        }

    }
    
}
