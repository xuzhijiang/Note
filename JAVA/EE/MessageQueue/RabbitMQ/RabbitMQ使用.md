## RabbitMQ

### 概述

RabbitMQ是基于AMQP协议实现的消息代理(Message brokers)。是消息中间件的一种，在易用性、扩展性、高可用性等方面表现不俗。当生产者大量产生数据时，消费者无法快速消费，那么需要一个中间层。保存这个数据。

### 在架构模型方面

1. 一般的MQ结构：producer，broker，consumer
2. RabbitMQ的broker由Exchange,Binding,queue组成，其中exchange和binding组成了消息的路由键
3. 客户端Producer通过连接channel和server进行通信
4. Consumer从queue获取消息进行消费（长连接，queue有消息会推送到consumer端，consumer循环从输入流读取数据）。rabbitMQ以broker为中心；有消息的确认机制。

谈到消息队列服务, 会有三个概念： 发消息者、队列、收消息者，RabbitMQ 在这个基本概念之上, 多做了一层抽象, 在发消息者和 队列之间, 加入了交换器 (Exchange). 这样发消息者和队列就没有直接联系, 转而变成发消息者把消息给交换器, 交换器根据调度策略再把消息再给队列。

虚拟主机：一个虚拟主机持有一组交换机、队列和绑定。每一个RabbitMQ服务器都有一个默认的虚拟主机“/”。

交换机：Exchange用于转发消息，但是它不会做存储，如果没有Queue绑定到Exchange的话，它会直接丢弃掉Producer发送过来的消息。 `这里有一个比较重要的概念：路由键。消息到交换机的时候，交互机会转发到对应的队列中，那么究竟转发到哪个队列，就要根据该路由键。`

绑定：也就是交换机需要和队列相绑定，是多对多的关系。

#### 交换机(Exchange)

>交换机的功能主要是接收消息并且转发到绑定的队列，交换机不存储消息，在启用ack模式后，交换机找不到队列会返回错误。交换机有四种类型：Direct, topic, Headers and Fanout

1. Direct：direct 类型的行为是”先匹配, 再投送”. 即在绑定时设定一个 routing_key, 消息的routing_key 匹配时, 才会被交换器投送到绑定的队列中去.
2. Topic：按规则转发消息（最灵活）
3. Headers：设置header attribute参数类型的交换机
4. Fanout：转发消息到所有绑定队列

### 在吞吐量

rabbitMQ在吞吐量方面稍逊于kafka，他们的出发点不一样，rabbitMQ支持对消息的可靠的传递，支持事务，不支持批量的操作；基于存储的可靠性的要求存储可以采用内存或者硬盘。

### 在可用性方面

rabbitMQ支持miror的queue，主queue失效，miror queue接管。

### 在集群负载均衡方面

rabbitMQ的负载均衡需要单独的loadbalancer进行支持。

### Spring AMQP模块

Spring RabbitMQ AMQP API,此API由两个Spring模块开发：

* spring-amqp:它是AMQP协议实现的基础抽象。
* spring-rabbit: 它是RabbitMQ实现。

## 使用RabbitMQ(Windows)

### Install Spring RabbitMQ Server

1. 安装Erland:http://www.erlang.org/download.html
2. 安装RabbitMQ:https://www.rabbitmq.com/install-windows.html
3. RabbitMQ Server安装完成之后，会自动的注册为服务(services.msc中可以看到)，并以默认配置启动起来
4. Set the following SYSTEM variable if missing：`RABBITMQ_HOME=C:\Program Files (x86)\RabbitMQ Server\rabbitmq_server-3.2.3`

### Rabbit管理

我们可以直接通过配置文件的访问进行管理，也可以通过Web的访问进行管理。默认情况下，Rabbit MQ Server没有插件，这意味着我们无法将其用作AMQP提供程序。我们需要执行以下步骤：

1. cd 到${RABBITMQ_HOME}/sbin
2. 安装插件:`.\rabbitmq-plugins.bat enable rabbitmq_management`以开启Web管理插件，就可以通过浏览器来进行管理了.
3. 从cmd->services.msc->重新启动Rabbit MQ Server
4. 打开浏览器并访问http://localhost:15672/,并使用默认用户guest登录，密码也为guest
5. 通过RabbitMQ面板，可以看到Connection和Channels中包含当前连接的条目。

### 配置Exchange and Queue:

1. add a new queue
2. Add a new exchange
3. click Bindings,将exchange Bindings to queue