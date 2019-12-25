# 基于zk的共享锁和排它锁理解

![](../pics/共享锁和排它锁.png)
![](../pics/共享锁和排它锁的理解01.png)
![](../pics/共享锁和排它锁的理解02.png)
![](../pics/共享锁和排它锁的理解03.png)

# 基于Zookeeper分布式锁

Zookeeper 分布式锁恰恰应用了临时顺序节点.

![](../pics/Zookeeper分布式锁的原理01.png)
![](../pics/Zookeeper分布式锁的原理02.png)
![](../pics/Zookeeper分布式锁的原理03.png)
![](../pics/Zookeeper分布式锁的原理04.png)
![](../pics/Zookeeper分布式锁的原理05.png)
![](../pics/Zookeeper分布式锁的原理06.png)
![](../pics/Zookeeper分布式锁的原理07.png)
![](../pics/Zookeeper分布式锁的原理08.png)
![](../pics/Zookeeper分布式锁的原理09.png)
![](../pics/Zookeeper分布式锁的原理10.png)
![](../pics/zk实现分布式锁-临时节点的有序性.png)

>Zookeeper 和 Redis 分布式锁的比较

![](../pics/Zookeeper和Redis分布式锁的比较.png)

# 使用第三方框架基于zk实现分布式锁

工作用的比较多的是Curator这个框架.可以看[视频](https://www.bilibili.com/video/av57591340)
