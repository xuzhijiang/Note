# RabbitMQ

## 概述

RabbitMQ是基于AMQP协议实现的消息代理(Message brokers)。是消息中间件的一种，在易用性、扩展性、高可用性等方面表现不俗。当生产者大量产生数据时，消费者无法快速消费，那么需要一个中间层。保存这个数据。

## 在架构模型方面

1. 一般的MQ结构：producer，broker，consumer
2. RabbitMQ的broker由Exchange,Binding,queue组成，其中exchange和binding组成了消息的路由键
3. 客户端Producer通过连接channel和server进行通信
4. Consumer从queue获取消息进行消费（长连接，queue有消息会推送到consumer端，consumer循环从输入流读取数据）。rabbitMQ以broker为中心；有消息的确认机制。

>消息队列服务有三个概念： 发消息者、队列、收消息者，RabbitMQ在在发消息者和 队列之间, 加入了交换器 (Exchange). 这样发消息者和队列就没有直接联系, 我们向 RabbitMQ 发送消息，实际上是把消息发到交换器了, 交换器根据相关路由规则再把消息再给队列,在队列上监听的消费者就可以进行消费了.见示意图.

队列(Queue):消息队列，用来保存消息直到发送给消费者。它是消息的容器，也是消息的终点。一个消息可投入一个或多个队列。消息一直在队列里面，等待消费者连接到这个队列将其取走。

虚拟主机(Virtual Host)：一个虚拟主机持有一组交换机、队列和绑定。每一个RabbitMQ服务器都有一个默认的虚拟主机“/”。

交换机(Exchange):用于转发消息，但是它不会做存储，如果没有Queue绑定到Exchange的话，它会直接丢弃掉Producer发送过来的消息。 `这里有一个比较重要的概念：路由键。消息到交换机的时候，交互机会转发到对应的队列中，那么究竟转发到哪个队列，就要根据该路由键。`

绑定(Binding)：也就是交换机需要和队列相绑定，是多对多的关系。一个绑定就是基于路由键将交换器和消息队列连接起来的路由规则.

### 交换机(Exchange)

>交换机的功能主要是接收消息并且转发到绑定的队列，交换机不存储消息，在启用ack模式后，交换机找不到队列会返回错误。交换机有四种类型：Direct, topic, Headers and Fanout

**Direct Exchange**

>消息中的路由键（routing key）如果和 Binding中的 binding key 一致， 交换器就将消息发到对应的队列中。路由键与队列名完全匹配，如果一个队列绑定到交换机要求路由键为“dog”，则只转发 routing key 标记为“dog”的消息，不会转发“dog.puppy”，也不会转发“dog.guard”等等。它是完全匹配、单播的模式。Direct Exchange是RabbitMQ默认的模式，也是最简单的模式，根据key全文匹配去寻找队列。

X 到队列 Q1 就有一个 binding key，名字为 orange； X 到队列 Q2 就有 2 个 binding key，名字为 black 和 green。当消息中的 路由键 和 这个 binding key 对应上的时候，那么就知道了该消息去到哪一个队列中。

>为什么 X 到 Q2 要有 black，green，2个 binding key呢，一个不就行了吗？ - 这个主要是因为可能又有 Q3，而Q3只接受 black 的信息，而Q2不仅接受black 的信息，还接受 green 的信息。

**Topic Exchange(按规则转发消息,最灵活)**

转发消息主要是根据通配符。将路由键和某个模式进行匹配，`此时队列需要绑定到一个模式上`,它将路由键和绑定键的字符串切分成单词，这些单词之间用点隔开.

* 路由键必须是一串字符，用.隔开，比如说 agreements.us
* 路由模式必须包含一个 星号\*，主要用于匹配路由键指定位置的一个单词，比如说，一个路由模式是这样子：agreements..b.，那么就只能匹配路由键是这样子的：第一个单词是 agreements，第四个单词是 b。 井号\#就表示相当于一个或者多个单词，例如一个匹配模式是agreements.eu.berlin.#，那么，以agreements.eu.berlin开头的路由键都是可以的。 具体代码发送的时候还是一样，第一个参数表示交换机，第二个参数表示routing key，第三个参数即消息。如下：
`rabbitTemplate.convertAndSend("testTopicExchange","key1.a.c.key2", " this is  RabbitMQ!");`
* topic 和 direct 类似, 只是匹配上支持了”模式”, 在”点分”的 routing_key 形式中, 可以使用两个通配符:其中\*号表示一个词，\#号表示零个或多个词.

**Headers Exchange(设置header attribute参数类型的交换机)**

相较于direct和topic固定地使用routing_key,headers匹配AMQP消息的header 而不是路由键，除此外headers交换器和 direct 交换器完全一致，但性能差很多，目前几乎用不到了

**Fanout Exchange(转发消息到所有绑定队列)**

每个发送到 fanout 交换器中的消息，他不会去匹配路由键(routing key)，直接把消息投递到所有绑定到 fanout 交换器中的队列上，它就像一个广播站一样，它会向所有收听广播的用户发送消息。
对应到系统上，它允许你针对一个消息作不同操作，比如用户上传了一张新的图片，系统要同时对这个事件进行不同的操作，比如删除旧的图片缓存、增加积分奖励等等。这样就大大降低了系统之间的耦合度了。

## 在吞吐量

rabbitMQ在吞吐量方面稍逊于kafka，他们的出发点不一样，rabbitMQ支持对消息的可靠的传递，支持事务，不支持批量的操作；基于存储的可靠性的要求存储可以采用内存或者硬盘。

## 在可用性方面

rabbitMQ支持miror的queue，主queue失效，miror queue接管。

## 在集群负载均衡方面

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

## Rabbit管理

### Windows下

我们可以直接通过配置文件的访问进行管理，也可以通过Web的访问进行管理。默认情况下，Rabbit MQ Server没有插件，这意味着我们无法将其用作AMQP提供程序。我们需要执行以下步骤：

1. cd 到${RABBITMQ_HOME}/sbin
2. 安装插件:`.\rabbitmq-plugins.bat enable rabbitmq_management`以开启Web管理插件，就可以通过浏览器来进行管理了.
3. 从cmd->services.msc->重新启动Rabbit MQ Server
4. 打开浏览器并访问http://localhost:15672/,并使用默认用户guest登录，密码也为guest
5. 通过RabbitMQ面板，可以看到Connection和Channels中包含当前连接的条目。
6. add a new queue
7. Add a new exchange
8. click Bindings,将exchange Bindings to queue

## Linux下

```shell
yum install rabbitmq-server

# 启动 RabbitMQ：
/sbin/rabbitmq-server
# 守护线程开启
$ /sbin/rabbitmq-server -detached
# 重启节点
$ /sbin/rabbitmqctl reset

# 停止 RabbitMQ：
/sbin/rabbitmqctl stop
/sbin//rabbitmqctl stop_app // 停止应用
/sbin/rabbitmqctl -n rabbit@server.example.com stop  // 停止特定节点

#由于RabbitmMQ是用Erlang写的，Erlang有节点的概念，也就是在一个Erlang 节点上，可以运行很多个Erlang应用。stop 命令是使得整个Erlang节点停止工作，而stop_app 则是使得当前应用停止工作，不会影响其它应用的正常运行。

# 查看 RabbitMQ 状态：
$ /sbin/rabbitmqctl status
# 查看绑定：
$ /sbin/rabbitmqctl list_bindings
# 查看交换器：
$ /sbin/rabbitmqctl list_exchanges
# 查看已声明的队列：
$ /sbin/rabbitmqctl list_queues
# 配置文件
$ sudo vim /etc/rabbitmq/rabbitmq.config // 配置文件位置
```

## RabbitMQ集群原理与部署

>在项目中想要 RabbitMQ 变得更加健壮，就要使得其变成高可用，所以我们要搭建一个 RabbitMQ 集群，这样你可以从任何一台 RabbitMQ 故障中得以幸免，并且应用程序能够持续运行而不会发生阻塞。而 RabbitMQ 本身是基于 Erlang 编写的，Erlang 天生支持分布式（通过同步 Erlang 集群各节点的 cookie 来实现），因此不需要像 ActiveMQ、Kafka 那样通过 ZooKeeper 分别来实现 HA 方案和保存集群的元数据。
