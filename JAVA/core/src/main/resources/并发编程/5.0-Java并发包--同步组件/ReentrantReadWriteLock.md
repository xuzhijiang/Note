# ReentrantLock 和 ReentrantReadWriteLock

    读写锁 ReentrantReadWriteLock 可以保证多个线程可以同时读，所以当读操作远大于写操作的时候，读写锁就非常有用了

    ReentrantLock 和 ReentrantReadWriteLock都是基于AQS来实现.

## 读写锁

使用 `ReentrantReadWriteLock` ,同时维护一对锁：读锁和写锁。当写线程访问时则其他所有锁都将阻塞，读线程访问时则不会。通过读写锁的分离可以很大程度的提高并发量和吞吐量。
    