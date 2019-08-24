# Redis的并发竞争问题如何解决?

Redis为单进程单线程模式，采用队列模式将并发访问变为串行访问。Redis本身没有锁的概念，Redis对于多个客户端连接并不存在竞争，但是在Jedis客户端对Redis进行并发访问时会发生连接超时、数据转换错误、阻塞、客户端关闭连接等问题，这些问题均是由于客户端连接混乱造成。对此有2种解决方法：

1.客户端角度，为保证每个客户端间正常有序与Redis进行通信，对连接进行池化，同时对客户端读写Redis操作采用内部锁synchronized。
2.服务器角度，利用setnx实现锁。

>注：对于第一种，需要应用程序自己处理资源的同步，可以使用synchronized也可以使用lock；第二种需要用到Redis的setnx命令.

# Redis常见性能问题和解决方案:

1. Master最好不要做任何持久化工作，如RDB内存快照和AOF日志文件
2. 如果数据比较重要，某个Slave开启AOF备份数据，策略设置为每秒同步一次
3. 为了主从复制的速度和连接的稳定性，Master和Slave最好在同一个局域网内
4. 尽量避免在压力很大的Master上增加Slave

# Redis 大量数据插入

官方文档给的解释：http://www.redis.cn/topics/mass-insert.html

# 混合存储典型场景：如何轻松搭建视频直播间系统

通常除了视频直播窗口外，直播间还包含在线用户，礼物，评论，点赞，排行榜等信息。直播间消息，时效性高，互动性强，对系统时延有着非常高的要求，非常适合使用Redis等缓存服务来处理。

# 直播信息

## 实时排行信息

实时排行信息包含直播间在线用户列表，各种礼物排行榜，弹幕消息（可以理解为按消息维度的消息排行榜）等信息，适合使用Redis中的SortedSet结构进行存储。

>例如，以unix timestamp+毫秒数为分值，记录user55的直播间增加的5条弹幕

```shell
redis> ZADD user55:_danmu 1523959031601166 message111111111111
(integer) 1
11.160.24.14:3003> ZADD user55:_danmu 1523959031601266 message222222222222
(integer) 1
11.160.24.14:3003> ZADD user55:_danmu 1523959088894232 message33333
(integer) 1
11.160.24.14:3003> ZADD user55:_danmu 1523959090390160 message444444
(integer) 1
11.160.24.14:3003> ZADD user55:_danmu 1523959092951218 message5555
(integer) 1
```

>返回最新的3条弹幕信息：

```
redis> ZREVRANGEBYSCORE user55:_danmu +inf -inf LIMIT 0 3
1) "message5555"
2) "message444444"
3) "message33333"
```

>返回指定时间段内的3条弹幕信息：

```shell
redis> ZREVRANGEBYSCORE user55:_danmu 1523959088894232 -inf LIMIT 0 3
1) "message33333"
2) "message222222222222"
3) "message111111111111"
```

## 计数类信息

计数类信息以"用户维度"为例，有未读消息数，关注数，粉丝数，经验值等等。这类消息适合以Redis中的Hash结构进行存储。

```
redis> HSET user:55 follower 5
(integer) 1
redis> HINCRBY user:55 follower 1 //关注数+1
(integer) 6 
redis> HGETALL user:55
1) "follow"
2) "6"
```

## 时间线信息

时间线信息是以"时间为维度"的信息列表，典型的比如主播动态，新帖。这类信息排序方式是固定的时间顺序，可以考虑使用List或者SortedSet来存储。

```shell
redis> LPUSH user:55_recent_activitiy  '{datetime:201804112010,type:publish,title:开播啦,content:加油}'
(integer) 1
redis> LPUSH user:55_recent_activitiy '{datetime:201804131910,type:publish,title:请假,content:抱歉，今天有事鸽一天}'
(integer) 2
redis> LRANGE user:55_recent_activitiy 0 10
1) "{datetime:201804131910,type:publish,title:\xe8\xaf\xb7\xe5\x81\x87\",content:\xe6\x8a\xb1\xe6\xad\x89\xef\xbc\x8c\xe4\xbb\x8a\xe5\xa4\xa9\xe6\x9c\x89\xe4\xba\x8b\xe9\xb8\xbd\xe4\xb8\x80\xe5\xa4\xa9}"
2) "{datetime:201804112010,type:publish,title:\xe5\xbc\x80\xe6\x92\xad\xe5\x95\xa6,content:\xe5\x8a\xa0\xe6\xb2\xb9}"
```

# 其他应用场景

## 页面缓存

如果你使用的是服务器端内容渲染，你又不想为每个请求重新渲染每个页面，就可以使用 Redis 把常被请求的内容缓存起来，能够大大的降低页面请求的延迟。

## 排行榜/计数

Redis 基于内存，可以非常快速高效的处理增加和减少的操作，相比于使用 SQL 请求的处理方式，性能的提升是非常巨大的。

Redis可以实现快速计数、查询缓存的功能，`同时数据可以异步落地到其他数据源。`,[redis和mysql数据一致性解决方案](https://zhuanlan.zhihu.com/p/58536781)

典型应用场景：

1. 播放数计数的基础组件，用户每播放一次视频，相应的视频播放数就会自增1。
2. 排行榜：按照时间、按照数量、按照获得的赞数等排行。

## 共享Session

典型应用场景：用户登陆信息，Redis将用户的Session进行集中管理，每次用户更新或查询登陆信息都直接从Redis中集中获取。

## 消息队列

例如 email 的发送队列、等待被其他应用消费的数据队列，Redis 可以轻松而自然的创建出一个高效的队列。

## 发布/订阅

pub/sub 是 Redis 内置的一个非常强大的特性，例如可以创建一个实时的聊天系统、社交网络中的通知触发器等等。