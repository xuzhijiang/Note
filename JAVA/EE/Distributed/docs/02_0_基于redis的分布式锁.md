# redis分布式锁

Redis为单进程模式，多个client并发操作，按照先到先执行的原则，先到的先执行，其余的阻塞。也就是采用队列模式将并发访问变成串行访问，且多客户端对Redis的连接并不存在竞争关系。其次Redis提供一些命令SETNX，GETSET，可以方便实现分布式锁机制。

本着批判性原则，现在网上可以随手搜索到很多基于Redis的分布式锁实现，但可能是时间原因吧，那些实现方式不是很完美，比如基于Redis实现分布式锁这篇文章，阅读数量挺大的，但是其内容比较老旧，而且也不是很好的解决方案，因为他这种实现机制牵扯到全局时钟一致。在分布式环境中要想实现全局时钟完全同步是非常困难的，个人感觉也没有必要。

# redis分布式锁要达到的特性

1. 安全:互斥,任何时候,只有一个客户端能持有同一个锁.
2. 不会死锁:最终client一定会得到锁,就算一个持有锁的客户端宕掉或者发生网络分区异常
3. 容错性:当部分redis节点宕机时,客户端仍然能够获取锁和释放锁
4. 可以使用阻塞锁与非阻塞锁
5. 高性能(加、解锁时高性能)

# 完美的解决方案（并非真的完美）

>基于`SET resource_name my_random_value NX PX 30000`实现

`语法：jedis/jedisCluster.set(key, value, "nx", "ex/px", 3); `

这也是redis官网提出的推荐的方案,[官网推出了一种叫Redlock的锁](https://redis.io/topics/distlock)

![](安全和可靠性保证.png)

## 在redis单节点上实现分布式锁

![](采用单实例的正确实现01.png)

![](采用单实例的正确实现02.png)

分布式锁需要考虑的: 锁时效(能租用锁多长时间)，锁等待(阻塞/非阻塞)，锁释放通知，锁粒度可控，JDK有的分布式锁都要有，what？redis客户端redisson都已经实现的差不多了。

## Redlock

[http://www.kailing.pub/article/index/arcid/168.html](http://www.kailing.pub/article/index/arcid/168.html)

- [开箱即用的分布式所](https://github.com/kekingcn/spring-boot-klock-starter)