Redis是一个开源的高性能键值对数据库。它通过提供多种键值数据类型来适应不同场景下的存储需求.

## 特性

### 存储结构

Redis是REmote DIctionary Server(远程字典服务器）的缩写，它以字典(或称映射)结构存储数据，并允许其他应用通过`TCP协议读写字典中的内容`。Redis字典中的键值除了可以是字符串，还可以是其他数据类型。到目前为止Redis支持的键值数据类型如下：

1.字符串类型
2. 散列类型
3. 列表类型
3. 集合类型
4. 有序集合类型

这种字典形式的存储结构与常见的MySQL等关系数据库的二维表形式的存储结构有很大的差异。举个例子，如下所示，我们在程序中使用post变量存储了一篇文章的数据(包括标题、正文、阅读量和标签):

post["title"]="Hello World!"
post["content"]="Blablabla..."
post["views"]=0
post["tags"]=["PHP","Ruby","Node.js"]

### 内存存储与持久化

Redis数据库中的所有数据都存储在内存中。由于内存的读写速度远快于硬盘，因此Redis在性能上对比其他基于硬盘存储的数据库有非常明显的优势，在一台普通的笔记本电脑上，Redis可以在一秒内读写超过十万个键值。

>将数据存储在内存中也有问题，例如，程序退出后内存中的数据会丢失。不过 Redis提供了对持久化的支持，即可以将内存中的数据异步写入到硬盘中，同时不影响继续提供服务。

### 功能丰富

Redis虽然是作为数据库开发的，但由于其提供了丰富的功能，越来越多的人将其用作`缓存、队列系统`等。Redis可谓是名副其实的多面手。

Redis可以为每个键设置`生存时间(Time To Live，TTL）`，生存时间到期后键会自动被删除。这一功能配合出色的性能让Redis可以作为缓存系统来使用，而且由于Redis支持`持久化`和丰富的数据类型，使其成为了`另一个非常流行的缓存系统Memcached的有力竞争者`。

#### Redis与Memcached

讨论关于Redis和Memcached优劣的讨论一直是一个热门的话题。`在性能上Redis是单线程模型，而Memcached支持多线程，所以在多核服务器上后者的性能更高一些`。然而，前面已经介绍过，Redis的性能已经足够优异，在绝大部分场合下其性能都不会成为瓶颈。所以在使用时更应该关心的是二者在功能上的区别，`如果需要用到高级的数据类型或是持久化等功能，Redis将会是Memcached很好的替代品`。

作为缓存系统，Redis还可以限定数据占用的最大内存空间，在数据达到空间限制后可以按照一定的规则自动淘汰不需要的键。

>除此之外，Redis的列表类型键可以用来实现队列，并且支持阻塞式读取，可以很容易地实现一个高性能的优先级队列。同时在更高层面上，Redis还支持“发布/订阅”的消息模式，可以基于此构建聊天室等系统。

### 简单稳定

Redis直观的存储结构使得通过程序与Redis交互十分简单。在Redis中`使用命令来读写数据`，`命令语句之于Redis`就相当于`SQL语言之于关系数据库`。例如在关系数据库中要获取posts表内id为1的记录的title字段的值可以使用如下SQL语句实现：

>LSELECT title FROM posts WHERE id=1 LIMIT 1

相对应的，在Redis中要读取键名为post:1的散列类型键的title字段的值，可以使用如下命令语句实现：

>HGET post:1 title

其中HGET就是一个命令。Redis提供了一百多个命令，听起来很多，但是常用的却只有十几个，并且每个命令都很容易记忆。读完第3章你就会发现Redis的命令比SQL语言要简单很多。

Redis提供了几十种不同编程语言的客户端库，这些库都很好地封装了Redis的命令，使得在程序中与Redis进行交互变得更容易。

>读者可以在http://redis.io/commands上查看到所有的redis命令和使用方法

Redis使用C语言开发，代码量只有3万多行。这降低了用户通过修改Redis源代码来使之更适合自己项目需要的门槛。对于希望“榨干”数据库性能的开发者而言，这无疑是一个很大的吸引力。

## 安装Redis

Redis的版本规则:Redis约定次版本号(即第一个小数点后的数字）为偶数的版本是稳定版(如2.4版、2.6版），奇数版本是非稳定版(如2.5版、2.7版），推荐使用稳定版本进行开发和在生产环境使用。

>Redis兼容大部分POSIX系统，在这些系统中推荐直接下载Redis源代码编译安装以获得最新的稳定版本。Redis最新稳定版本的源代码可以从地址http://download.redis.io/redis-stable.tar.gz下载。

下载安装包后解压即可使用make 命令完成编译，完整的命令如下：

```shell
wget http://download.redis.io/redis-stable.tar.gz
tar xzf redis-stable.tar.gz
cd redis-stable
make
make install
```

>Redis没有其他外部依赖，最好在编译后直接执行make install命令来将这些可执行程序复制到/usr/local/bin目录中以便以后执行程序时可以不用输入完整的路径。

可以用ps命令查看到相关信息：`ps -ef | grep redis`

## 启动Redis和停止Redis

Redis可执行文件说明:

1. redis-server: Redis服务器
2. redis-cli(Redis Command Line Interface): Redis命令行客户端
3. redis-benchmark: Redis性能测试工具
4. redis-check-aof: AOF文件修复工具
5. redis-check-dump: RDB文件检查工具

我们最常使用的两个程序是redis-server和redis-cli.

### 停止Redis

考虑到Redis有可能正在将内存中的数据同步到硬盘中，强行终止Redis进程可能会导致数据丢失。正确停止Redis的方式应该是向Redis发送SHUTDOWN命令，方法为：

`redis-cli SHUTDOWN`

>当Redis收到SHUTDOWN命令后，会先断开所有客户端连接，然后根据配置`执行持久化`，最后完成退出。

>Redis可以妥善处理SIGTERM信号，所以使用“kill Redis进程的PID”也可以正常结束Redis，效果与发送SHUTDOWN命令一样。

启动Redis有`直接启动`和`通过初始化脚本`启动两种方式，分别适用于`开发环境`和`生产环境`。

### 直接启动

直接运行redis-server即可启动Redis，十分简单： `redis-server`

Redis服务器默认会使用6379端口 ，通过--port参数可以自定义端口号：
`redis-server --port 6380`

### 作为服务启动

/usr/local/bin/redis-server /etc/redis/redis.conf   #指定配置文件 启动

redis-cli向Redis发送命令有两种方式:

1. 将命令作为redis-cli的参数执行,redis-cli执行时会自动按照默认配置(服务器地址为127.0.0.1，端口号为6379）连接Redis如: `redis-cli SHUTDOWN`,通过-h和-p参数可以自定义地址和端口号：`redis-cli -h 127.0.0.1 -p 6379`
2. 不附带参数运行redis-cli，这样会进入交互模式，可以自由输入命令:

```shell
redis-cli
redis 127.0.0.1:6379> PING
PONG
redis 127.0.0.1:6379> ECHO hi
"hi"
```

>Redis提供了PING命令来测试客户端与Redis的连接是否正常，如果连接正常会收到回复PONG。如：

```shell
redis-cli PING
PONG
```

#### 命令返回值

1. 状态回复:PING命令的回复PONG就是状态回复，再比如向Redis发送SET命令设置某个键的值时，Redis会回复状态OK表示设置成功。
2. 错误回复：当出现命令不存在或命令格式有错误等情况时Redis会返回错误回复(error reply）。错误回复以(error)开头。
3. 整数回复：Redis没有整数类型，但是却提供了一些用于整数操作的命令，如递增键值的INCR命令会以整数形式返回递增后的键值。可以获取当前数据库中键的数量的DBSIZE命令也会返回整数。整数回复(integer reply）以(integer)开头，并在后面跟上整数数据：
```shell
redis>INCR foo
(integer) 1
```
4. 字符串回复:字符串回复(bulk reply）是最常见的一种回复类型，当请求一个字符串类型键的键值或一个其他类型键中的某个元素时就会得到一个字符串回复。字符串回复以双引号包裹：
```shell
redis>GET foo
"1"
```

>特殊情况是当请求的键值不存在时会得到一个空结果，显示为(nil)。如：
```shell
redis>GET noexists
(nil)
```

5．多行字符串回复:(multi-bulk reply）同样很常见，如当请求一个非字符串类型键的元素列表时就会收到多行字符串回复。多行字符串回复中的每行字符串都以一个序号开头，如：
```shell
redis> KEYS *
1) "bar"
2) "foo"
```

提示: KEYS命令的作用是获取数据库中符合指定规则的键名，由于读者的Redis中还没有存储数据，所以得到的返回值应该是(empty list or set）.

## 多数据库

Redis是一个字典结构的存储服务器，而实际上`一个Redis实例`提供了`多个用来存储数据的字典`，客户端可以指定将数据存储在哪个字典中。这与我们熟知的在一个关系数据库实例中可以创建多个数据库类似，所以可以将其中的`每个字典`都`理解成一个独立的数据库`。

每个数据库对外都是以一个从0开始的递增数字命名，Redis默认支持16个数据库，可以通过配置参数databases来修改这一数字。客户端与Redis建立连接后会自动选择0号数据库，不过可以随时使用SELECT命令更换数据库，如要选择1号数据库：

```shell
redis>SELECT 1
OK
redis [1]>GET foo
(nil)
```

然而这些以数字命名的数据库又与我们理解的数据库有所区别。首先Redis不支持自定义数据库的名字，每个数据库都以编号命名，开发者必须自己记录哪些数据库存储了哪些数据。另外Redis也不支持为每个数据库设置不同的访问密码，所以一个客户端要么可以访问全部数据库，要么连一个数据库也没有权限访问。最重要的一点是多个数据库之间并不是完全隔离，比如FLUSHALL命令可以清空一个Redis实例中所有数据库中的数据。综上所述，这些数据库更像是一种命名空间，而不适宜存储不同应用程序的数据。

比如可以使用0号数据库存储某个应用生产环境中的数据，使用1号数据库存储测试环境中的数据，但不适宜使用0号数据库存储A应用的数据而使用1号数据库存储B应用的数据，不同的应用应该使用不同的Redis实例存储数据。由于Redis非常轻量级，一个空Redis实例占用的内存只有1MB左右，所以不用担心多个Redis实例会额外占用很多内存。

