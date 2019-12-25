# zookeeper实现过分布式锁么?你们的业务为什么会用到分布式锁呢?用来解决什么问题的?

    用来解决分布式环境下,订单号生成不重复的问题.

# zk的分布式锁你是怎么实现的?(实现思路)

    使用zk的临时节点+zkClient端的事件通知
    
![](zk实现分布式锁的简单设计思路.png)

# zk的节点类型有几种?创建节点的命令是什么?默认创建的是什么节点类型?

    zk的节点类型有几种:
        大的分为2种: 持久节点 和 临时节点
        再细分: 持久节点 持久顺序节点 临时节点 临时顺序节点
    
    创建节点的命令是什么?
        -s 顺序节点
        -e 临时节点
        持久顺序节点: create -s /PersiSeqNode vPersiSeqNode (可以创建多次)
        临时节点: create -e /EpheNode vEpheNode (quit后,或zk重启,会被自当删除 )
        临时顺序节点: create -e -s /EpheSeqNode vEpheSeqNode (可以创建多次)
    持久节点和临时节点都只能create一次,但是顺序节点可以创建多次

![](节点类型的英文表示.png)

    默认创建的是什么节点类型? 持久节点
    create /firstNode vFirstNode                

# 如何退出zkClient

    输入quit    