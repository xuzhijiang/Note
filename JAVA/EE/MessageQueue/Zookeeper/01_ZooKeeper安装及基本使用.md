## ZooKeeper要求的预配置

由于ZooKeeper将数据保存在内存中以实现高吞吐量和低延迟(high throughput and low latency)，因此生产系统最适合使用8GB RAM。 较少量的RAM可能导致JVM交换，这可能导致ZooKeeper服务器延迟。 高ZooKeeper服务器延迟可能导致客户端会话超时等问题，从而对系统功能产生负面影响。

>JDK >= 1.6

## 安装ZooKeeper软件包

把ZooKeeper Binaries下载到/opt下:

>cd /opt

从http://mirror.bit.edu.cn/apache/zookeeper/上下载,建议下载stable:

>wget http://mirror.bit.edu.cn/apache/zookeeper/stable/zookeeper-3.4.14.tar.gz

解压压缩文件:

>tar -zxvf zookeeper-3.4.14.tar.gz

Create a symbolic link using the ln command.使用`ln`命令创建一个符号链接:

>ln -s zookeeper-3.4.14 zookeeper

为zookeeper创建一个目录存放data:

>mkdir -p /data/zookeeper

## 配置ZooKeeper

配置文件存在于zookeeper/conf目录中，此目录包含ZooKeeper发行版附带的示例配置文件。 此示例文件名为zoo_sample.cfg，包含这些参数的最常见配置参数定义和值。 一些常见参数如下：

* tickTime：设置tick的长度（以毫秒为单位）。 tick是ZooKeeper用来测量心跳之间长度的时间单位。 最小会话超时是tickTime的两倍。
* dataDir：指定用于存储`内存数据库快照`和`更新事务日志`的目录。 您可以为`事务日志`指定单独的目录
* clientPort：用于侦听客户端连接的端口。
* maxClientCnxns：限制最大客户端连接数

在zookeeper/conf下创建zoo.cfg(注意名字不能错了)，添加以下内容:

```shell
# tickTime为2000毫秒是被建议的心跳之间的间隔。 较短的间隔可能导致系统开销，但收益有限(system overhead with limited benefits)。 
tickTime=2000

# dataDir参数指向之前创建的符号链接定义的路径
# 这里指向的是/data/zookeeper,可以指向一个相对路径
# ./data,就是相对于配置文件路径下的data目录下.
dataDir=/data/zookeeper
# 通常，ZooKeeper使用端口2181来侦听客户端连接
clientPort=2181
# 在大多数情况下，60个允许的客户端连接足以进行开发和测试。
maxClientCnxns=60
```

## 启动ZooKeeper并测试

使用zkServer.sh命令启动ZooKeeper：

>bin/zkServer.sh start

使用以下命令连接到本地ZooKeeper服务器：

>bin/zkCli.sh -server 127.0.0.1:2181

你将看到CONNECTED的提示，这确认您已成功安装本地独立的ZooKeeper。 如果遇到错误，则需要验证配置是否正确。在此提示符上键入`help`以获取可以从客户端执行的命令列表:

### 可以从客户端执行的命令

```shell
# 以下命令都是在上一步输入了help之后执行

# 查看节点:
ls /zookeeper

# create a new znode by running "create /zk_test my_data". This creates a new znode and associates the string "my_data" with the node.
# 通过运行"create /zk_test my_data"创建一个新znode节点
# 并且把字符串"my_data"和这个节点关联在了一起
create /zk_test my_data

# Issue another "ls /"" command to see what the directory looks like:
# 发出另一个命令"ls /",查看目录的样子(查看节点)
ls /

# 查看节点数据
get /zk_test

# Zxid： Every change to the ZooKeeper state receives a stamp in the form of a zxid (ZooKeeper Transaction Id). This exposes the total ordering of all changes to ZooKeeper. Each change will have a unique zxid and if zxid1 is smaller than zxid2 then zxid1 happened before zxid2.

# 更新数据
# We can change the data associated with zk_test by issuing the set command, as in:
set /zk_test junk

get /zk_test   

# 删除节点
delete /zk_test

ls /

# 每个节点参数说明：

# czxid:
# The zxid of the change that caused this znode to be created.

# mzxid
# The zxid of the change that last modified this znode.

# ctime
# The time in milliseconds from epoch when this znode was created.

# mtime
# The time in milliseconds from epoch when this znode was last modified.

# version
# The number of changes to the data of this znode.

# cversion
# The number of changes to the children of this znode.

# aversion
# The number of changes to the ACL of this znode.

# ephemeralOwner
# The session id of the owner of this znode if the znode is an ephemeral node. If it is not an ephemeral node, it will be zero.

# dataLength
# The length of the data field of this znode.

# numChildren
# The number of children of this znode.
```

在提示符下输入quit来关闭client session。关闭客户端会话后，ZooKeeper服务将继续运行。 关闭ZooKeeper服务，因为您将在下一步中将其配置为systemd服务：

>bin/zkServer.sh stop

## Zookeeper集群环境搭建

独立的ZooKeeper服务器对开发和测试很有用，但每个生产环境都应该有一个复制的多节点集群。

ZooKeeper集群中的节点作为应用程序一起工作，形成仲裁。仲裁是指`在事务提交之前需要就事务达成一致的最小节点数`。仲裁需要奇数个节点，以便它可以建立a majority。偶数个节点可能导致并列，这意味着节点不会达到多数或达成共识。

在生产环境中，您应该在单独的主机上运行每个ZooKeeper节点。这可以防止因主机硬件故障或重新启动而导致的服务中断。`这是构建弹性且高度可用的分布式系统的重要且必要的架构考虑因素。`

在本教程中，将在仲裁中安装和配置三个节点以演示多节点设置(本教材要演示的是在3台不同的服务器上配置,而不是一台服务器上部署3个不同的zk实例)。

>在配置三个节点群集之前，您将使用与`独立ZooKeeper安装`相同的配置启动另外两台服务器。确保两个附加节点满足先决条件.

你按照上面的步骤依次运用到2个新节点之后，仲裁中的所有节点都需要相同的配置文件。 在三个节点中每个节点上的zoo.cfg文件中，在文件末尾添加其他配置参数和值:`initLimit`，`syncLimit`和`server`

```shell
dataDir=/data/zookeeper
clientPort=2181
maxClientCnxns=60
# initLimit指定初始同步阶段可以采用的时间。 在这个时间之内，仲裁中每个节点需要连接到leader.
initLimit=10
# syncLimit指定发送请求和接收确认之间可以通过的时间。 这是节点与领导者不同步的最长时间。 
syncLimit=5
#ZooKeeper节点使用一对端口：2888和：3888，分别用于跟随节点连接到领导节点和领导者选举。
server.1=your_zookeeper_node_1:2888:3888
server.2=your_zookeeper_node_2:2888:3888
server.3=your_zookeeper_node_3:2888:3888
```

>要完成多节点配置，您将在每个服务器上指定节点ID。 为此，您将在每个节点上创建一个myid文件。 每个文件都包含一个与配置文件中分配的服务器编号相关的编号

在your_zookeeper_node_1上创建myid文件，然后执行node的id,由于your_zookeeper_node_1被标识为server.1,所以在/data/zookeeper/myid中要保存1.

在剩余的节点做如下步骤,在每个节点上的myid应该如下:

1. your_zookeeper_node_1 /data/zookeeper/myid (保存1)
2. your_zookeeper_node_2 /data/zookeeper/myid (保存2)
3. your_zookeeper_node_3 /data/zookeeper/myid (保存3)

你现在已经配置了3个节点的ZooKeeper集群，接下来你将运行集群并且测试.

>参考:[https://www.digitalocean.com/community/tutorials/how-to-install-and-configure-an-apache-zookeeper-cluster-on-ubuntu-18-04](https://www.digitalocean.com/community/tutorials/how-to-install-and-configure-an-apache-zookeeper-cluster-on-ubuntu-18-04)

### Zookeeper的伪分布式搭建

所谓Zookeeper的伪分布式搭建，就是在同一台机器上的不同端口，启动多个zookeeper Server实例，并通过多个配置文件来实现Server之间的通信。

```shell
# 新建以下目录和文件:
[root@www zookeeper-3.4.5-cdh5.4.7]# tree $ZOOKEEPER_HOME/pseudo_conf/
/usr/local/zookeeper-3.4.5-cdh5.4.7/pseudo_conf/
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

# 创建脚本
mkdir $ZOOKEEPER_HOME/pseudo_conf
mkdir $ZOOKEEPER_HOME/pseudo_conf/zk1
mkdir $ZOOKEEPER_HOME/pseudo_conf/zk2
mkdir $ZOOKEEPER_HOME/pseudo_conf/zk3
mkdir $ZOOKEEPER_HOME/pseudo_conf/zk1/data
mkdir $ZOOKEEPER_HOME/pseudo_conf/zk2/data
mkdir $ZOOKEEPER_HOME/pseudo_conf/zk3/data
touch $ZOOKEEPER_HOME/pseudo_conf/zk1/data/myid
touch $ZOOKEEPER_HOME/pseudo_conf/zk2/data/myid
touch $ZOOKEEPER_HOME/pseudo_conf/zk3/data/myid
echo 1 > $ZOOKEEPER_HOME/pseudo_conf/zk1/data/myid
echo 2 > $ZOOKEEPER_HOME/pseudo_conf/zk2/data/myid
echo 3 > $ZOOKEEPER_HOME/pseudo_conf/zk3/data/myid
touch $ZOOKEEPER_HOME/pseudo_conf/zk1/zk1.cfg
touch $ZOOKEEPER_HOME/pseudo_conf/zk2/zk2.cfg
touch $ZOOKEEPER_HOME/pseudo_conf/zk3/zk3.cfg

# zk1.cfg
tickTime=2000
initLimit=10
syncLimit=5
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

# 启动zk1
# 第一个参数值可以使用start/stop  第二个参数指的是配置文件的配置
cd $ZOOKEEPER_HOME/pseudo_conf/zk1
/usr/local/zookeeper-3.4.5-cdh5.4.7/bin/zkServer.sh start ./zk1.cfg

# 因为我们设置了集群中有3台机器,现在只启动了其中一台,因为Zookeeper目前还是不可以用的，
# 查看当前目录下的zookeeper.out文件，有类似以下内容:
# Java.net.ConnectException: Connection refused
# 可以看到zk1尝试连接其他的zookeeper server，但是由于其他的都没启动，所以连接失败。

# 启动zk2
#第一个参数值可以使用start/stop  第二个参数指的是配置文件的配置
cd $ZOOKEEPER_HOME/pseudo_conf/zk2
/usr/local/zookeeper-3.4.5-cdh5.4.7/bin/zkServer.sh start ./zk2.cfg
# 查看zk2的启动日志
# ... [myid:1] - INFO [QuorumPeer[myid=1]/...:2181:Follower@63] - FOLLOWING
# - LEADER ELECTION TOOK - 212
# 说明zk1是follower。

# 虽然我们只启动了2个zk实例，但是由于已经超过集群总数的一半(3个当中启动了2个)，所以此时服务已经可以用使用了。

# 此时我们要使用zkCli.sh去连接ZK服务。虽然zk3还没有启动，但是服务现在已经可以使用的，所以我们可以去连接，在zk3没有启动的情况下，去连接ZK服务，可以帮助我们更好的了解zookeeper的一些特点。
{ZOOKEEPER_HOME}/bin/zkCli.sh -server 127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183

# 启动zkCli时，控制台打印出类似以下内容：
Socket connection established, initiating session, client: /127.0.0.1:42939, server: localhost/127.0.0.1:2182

# 说明当前连接的是zk2，如果停止zkCli，再启动zkCli，重复几次这样的操作。我们会看到客户端连接连接的端口号在zk1(2181)和zk2(2182)之间切换，如果连接了zk3(2183)，就会提示连接失败信息，并自动尝试去连接其他的server。
```
