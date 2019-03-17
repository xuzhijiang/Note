### JMS和AMQP之间的区别

1. 互通性(Interoperability):JMS Application适用于任何OS环境，但它仅支持Java平台。如果我们想使用JMS API开发以下系统架构，那么所有这些系统都应该只使用Java语言开发。
但是如果我们使用AMQP标准来开发相同的系统，那么我们可以使用任何语言开发这些系统，这意味着System-1（JAVA），System-2（C＃），System-3（Ruby）和System-4（C++）
，JMS API仅适用于Java平台，但AMQP支持许多技术。
2. 消息模型Messaging Models:正如我们已经讨论过的，JMS API支持两种消息传递模型：P2P（点对点）模型和PUB / SUB（发布者/订阅者）模型。

>AMQP支持五种不同的Messaging模型（或Exchange类型）

Direct Exchange
Fanout Exchange
Topic Exchange
Headers Exchange
System Exchange

3. 消息结构:JMS消息分为3个部分：Header, Properties, and Body.
AMQP消息分为4个部分：Header, Properties, Body, and Footer.
4. 消息类型:JMS API支持5种类型的消息作为Body部分的一部分，但AMQP仅支持一种类型的消息 - 二进制（字节）消息(Binary (bytes) message.)

### Spring AMQP如何运作？

1. Spring Framework提供Spring AMQP API，将AMQP消息代理(Message brokers)与Spring应用程序集成，以开发企业消息系统。
2. 在AMQP Messaging系统中，Message Publisher将消息发送到Exchange。 Publisher不知道哪个队列配置到此Exchange,以及哪个Consumer与此队列关联。
3. 在配置Exchange时，我们将通过指定一些路由密钥将其映射到一个或多个队列。
4. AMQP Consumer连接到Queue并使用某些路由密钥监听消息。
5. 当Publisher将Message发送到Exchange时，Exchange使用此路由密钥并将这些Message发送到关联的队列。当队列接收消息时，关联的消费者会自动接收这些消息。

>AMQP-Exchange-Queue-Config.png

1. 如果Publisher发送Route key = 1的消息，则此消息将路由到Queue1，然后路由到Consumer1。
2. 如果Publisher发送路由密钥= 2的消息，则此消息将路由到Queue2，然后路由到Consumer2。

>注意：最流行的AMQP协议实现的消息代理或JMS服务器是：

1. Active MQ服务器(JMS实现)
2. Rabbit MQ服务器(AMQP协议实现)

### Spring AMQP模块

>https://spring.io/projects/spring-amqp

Spring RabbitMQ AMQP API:用于使用Spring Rabbit MQ服务器实现消息传递应用程序。此API由两个Spring模块开发：

* spring-amqp:它是AMQP协议实现的基础抽象。
* spring-rabbit: 它是RabbitMQ实现。

## Spring Boot使用RabbitMQ

RabbitMQ是基于Spring AMQP协议实现的消息代理(Message brokers)。

### Install Spring RabbitMQ Server

1. 安装Erland:http://www.erlang.org/download.html
2. 安装RabbitMQ:https://www.rabbitmq.com/install-windows.html
3. RabbitMQ Server安装完成之后，会自动的注册为服务(services.msc中可以看到)，并以默认配置启动起来

Set the following SYSTEM variable if missing：`RABBITMQ_HOME=C:\Program Files (x86)\RabbitMQ Server\rabbitmq_server-3.2.3`

### Rabbit管理

我们可以直接通过配置文件的访问进行管理，也可以通过Web的访问进行管理。下面我们将介绍如何通过Web进行管理。默认情况下，Rabbit MQ Server没有插件，这意味着我们无法将其用作AMQP提供程序。我们需要执行以下步骤：

1. cd 到${RABBITMQ_HOME}/sbin
2. 使用以下命令安装Rabbit MQ插件:
`.\rabbitmq-plugins.bat enable rabbitmq_management`,执行rabbitmq-plugins enable rabbitmq_management命令，开启Web管理插件，这样我们就可以通过浏览器来进行管理了.
3. 从cmd->services.msc->重新启动Rabbit MQ Server
4. 打开浏览器并访问http://localhost:15672/,并使用默认用户guest登录，密码也为guest
5. 查看现有队列:单击"queues"以查看现有队列或创建新队列
6. 添加新队列:单击“Add a new queue”以创建新队列
7. 通过RabbitMQ面板，可以看到Connection和Channels中包含当前连接的条目。

### Spring AMQP RabbitMQ服务器安装程序

在RabbitMQ服务器中配置Exchange and Queue:创建一个queue=“tpQueue”
,单击“Add queue”按钮,创建Exchange并将其映射到以前创建的队列
,点击“Add exchange”按钮,点击“tpExchange”,然后将“tpExchange”与“tpQueue”映射到一些路由键,单击“bind”按钮,现在是时候开始开发Spring AMQP RabbitMQ消息应用了！
