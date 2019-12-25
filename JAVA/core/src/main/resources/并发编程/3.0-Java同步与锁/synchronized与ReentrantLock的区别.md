# synchronized与ReentrantLock的区别，用Lock有什么好处？

- 原始构成
- 使用方法
- 中断
- 加锁是否公平
- 锁可以绑定多个条件Condition,可以实现唤醒指定线程

![](../pics/synchronized与ReentrantLock的区别，用Lock有什么好处.png)

    两者都是可重入锁
    
    synchronized需要依赖wait()/notify/notifyAll()方法相结合可以实现等待-通知机制
    
    ReentrantLock可以可以实现唤醒指定线程

# 使用选择

    除非需要使用 ReentrantLock 的高级功能，否则优先使用 synchronized。
    这是因为 synchronized 是 JVM 实现的一种锁机制，JVM 原生地支持它,1.6之后,性能有了很大的提升.
    并且使用 synchronized 不用担心没有释放锁而导致死锁问题，因为 JVM 会确保锁的释放
    