# Zookeeper集群搭建

ZooKeeper集群中的节点一起工作，形成仲裁。

仲裁是指`在事务提交之前需要就事务达成一致的最小节点数`。仲裁需要奇数个节点，以便它可以建立a majority。偶数个节点可能导致并列，这意味着节点不会达到多数或达成共识。

在生产环境中，您应该在单独的主机上运行每个ZooKeeper节点。这可以防止因主机硬件故障或重新启动而导致的服务中断。`这是构建弹性且高度可用的分布式系统的重要且必要的架构考虑因素。`

# Zookeeper集群配置文件说明

```shell
# initLimit指定初始同步阶段可以采用的时间。 在这个时间之内，仲裁中每个节点需要连接到leader.
initLimit=10

# syncLimit请求和应答时间长度,最长不能超过多少个tickTime的时间长度(5*tickTime)。 这是follower与leader不同步的最长时间。
syncLimit=5

# server.A=B：C：D
# A: 是一个数字,表示这个是第几号服务器；
# B: 这zk服务器的IP地址（或者是与IP地址做了映射的主机名）；
# C: leader与follower之间通信端口
# D: 是在leader挂掉时专门用来进行选举leader所用的端口
# 可以通过zkServer.sh status查看节点是什么角色,是follower还是leader
# 注意：如果是伪集群的配置方式，不同的 Zookeeper 实例之间,通信端口号不能一样，所以要给它们分配不同的端口号。
server.1=edu-zk-01:2881:3881
server.2=edu-zk-02:2882:3882
server.3=edu-zk-03:2883:3883

dataDir=/opt/zookeeper/node-0X/data
# 在dataDir下创建myid文件,编辑myid文件，并在对应的IP的机器上输入对应的编号。如在node-01上，myid文件内容就是1,node-02上就是2，node-03上就是3：
例如,vi /opt/zookeeper/node-01/data/myid  ## 值为1
```

## Zookeeper的伪分布式搭建

所谓Zookeeper的伪分布式搭建，就是在同一台机器上的不同端口，启动多个zookeeper Server实例，并通过多个配置文件来实现Server之间的通信

```shell
# 通过脚本zookeeper_pseudo_cluster.sh,新建目录和文件:
# 形成以下目录结构
├── zk1
│   ├── data
│   │   └── myid
│   └── zk1.cfg
├── zk2
│   ├── data
│   │   └── myid
│   └── zk2.cfg
└── zk3
    ├── data
    │   └── myid
    └── zk3.cfg

# zk1.cfg
tickTime=2000
initLimit=10
syncLimit=5
# 相对于当前所在的目录下寻找dataDir,然后找myid,注意不是相对于配置文件所在目录,很重要
dataDir=./data
clientPort=2181
server.1=127.0.0.1:2222:2223
server.2=127.0.0.1:3333:3334
server.3=127.0.0.1:4444:4445

# zk2.cfg
tickTime=2000
initLimit=10
syncLimit=5
dataDir=./data
clientPort=2182
server.1=127.0.0.1:2222:2223
server.2=127.0.0.1:3333:3334
server.3=127.0.0.1:4444:4445

# 启动zk1, param1: start/stop  param2: 配置文件
# 注意,自己在一台集群上启动zk集群的时候踩坑了
# 必须要cd到zk1.cfg所在目录才能执行,因为要在这个目录下寻找dataDir配置的data目录,然后寻找myid文件
zkServer.sh start ./zk1.cfg

# 因为我们设置了集群中有3台机器,现在只启动了其中一台,因为Zookeeper目前还是不可以用的，查看当前目录下的zookeeper.out文件，有类似以下内容:
# Java.net.ConnectException: Connection refused, 可以看到zk1尝试连接其他的zookeeper server，但是由于其他的都没启动，所以连接失败。

# 启动zk2: zkServer.sh start ./zk2.cfg
# 查看zk2的启动日志: ... [myid:1] - INFO [QuorumPeer[myid=1]/...:2181:Follower@63] - FOLLOWING
# 说明zk1是follower。

# 虽然我们只启动了2个zk实例，但是由于已经超过集群总数的一半(3个当中启动了2个)，所以此时服务已经可以用使用了。
# 此时我们要使用zkCli.sh去连接ZK服务。虽然zk3还没有启动，但是服务现在已经可以使用的，所以我们可以去连接，在zk3没有启动的情况下，去连接ZK服务，可以帮助我们更好的了解zookeeper的一些特点
bin/zkCli.sh -server 127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183

# 启动zkCli时，控制台打印出类似以下内容：Socket connection established, initiating session, client: /127.0.0.1:42939, server: localhost/127.0.0.1:2182
# 说明当前连接的是zk2，如果停止zkCli，再启动zkCli，重复几次这样的操作。我们会看到客户端连接连接的端口号在zk1(2181)和zk2(2182)之间切换，如果连接了zk3(2183)，就会提示连接失败信息，并自动尝试去连接其他的server。
```

# 参考

- [很好的zookeeper集群搭建参考文章,用zk作为dubble的注册中心](https://www.open-open.com/lib/view/open1454043410245.html)
