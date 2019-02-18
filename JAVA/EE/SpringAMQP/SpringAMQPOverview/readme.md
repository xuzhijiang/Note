## 概述

之前已经讨论了一些JMS概念和示例。我们将讨论AMQP协议和Spring AMQP消息传递。

### Spring AMQP

Spring AMQP是基于AMQP的消息传递解决方案的Spring实现。 Spring AMQP为我们提供了一个“模板”，作为发送和接收消息的高级抽象。

## 什么是AMQP？

AMQP代表高级消息队列协议(Advanced Message Queuing Protocol)。 AMQP是用于实现MOM（Message Oriented Middleware）的开放标准协议。

## 为什么我们需要AMQP？

我们有JMS API来开发企业消息系统，但为什么我们需要另一个Messaging标准。

>JMS API的主要缺点或限制是互操作性，这意味着我们仅可以开发基于Java的应用程序中工作的消息传递系统。它不支持其他语言。

AMQP解决了JMS API问题。 AMQP的主要优点是它支持异构平台和消息代理之间的互操作性(supports interoperability between heterogeneous platforms and messaging brokers)。我们可以用任何语言（Java，C++，C#，Ruby等）和任何操作系统开发我们的Messaging系统;他们仍然可以使用基于AMQP的消息代理来相互通信。

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

1. Apache Active MQ服务器
2. Spring Rabbit MQ服务器

### Spring AMQP模块

Spring Framework提供了两组API来处理AMQP协议服务器。 Spring AMQP项目位于以下网站：

>https://spring.io/projects/spring-amqp

1. Spring RabbitMQ AMQP API:用于使用Spring Rabbit MQ服务器实现消息传递应用程序。此API由两个Spring模块开发：

* spring-amqp:它是AMQP协议实现的基础抽象。
* spring-rabbit: 它是RabbitMQ实现。

2. Spring ActiveMQ AMQP API:此API用于使用Apache Active MQ服务器实现消息传递应用程序。此API使用带有ActiveMQ API的Spring AMQP抽象模块：

* spring-amqp:它是AMQP协议实现的基础抽象。
* activemq-spring:它是ActiveMQ实现。

注意：

* Spring AMQP和Spring RabbitMQ模块都来自Pivotal Team（Spring Framework）。 RabbitMQ服务器也来自Pivotal Team。
* ActiveMQ Server来自Apache Software Foundation。 Apache ActiveMQ发布了一个支持Spring Framework的API，它是activemq-spring（它不是来自Pivotal Team）。

## Spring RabbitMQ

Spring RabbitMQ是基于Spring AMQP协议实现的消息代理(Message brokers)。

* How to install RabbitMQ Server
* How to setup Queue & Exchanges in RabbitMQ Server

### Install Spring RabbitMQ Server

1. Download Erlang and RabbitMQ Server:We need to install both Erlang and Rabbit MQ server to start our Application Setup.

>Download Erlang from http://www.erlang.org/download.html, Download Rabbit MQ from https://www.rabbitmq.com/install-windows.html

Set the following SYSTEM variable if missing：`RABBITMQ_HOME=C:\Program Files (x86)\RabbitMQ Server\rabbitmq_server-3.2.3`

配置Rabbit MQ Server

默认情况下，Rabbit MQ Server没有插件，这意味着我们无法将其用作JMS提供程序。 我们需要执行以下步骤：

```shell
CMD> CD到${RABBITMQ_HOME}/sbin
#使用以下命令安装Rabbit MQ插件:
CMD> rabbitmq-plugins.bat enable rabbitmq_management
从cmd->services.msc->重新启动Rabbit MQ Server
```

	Access Rabbit MQ Admin console by using http://localhost:15672/

	username/password: guest/guest

	查看现有队列:单击"queues"以查看现有队列或创建新队列

	添加新队列:单击“Add a new queue”链接以创建新队列并提供详细信息

	单击“Add queue”按钮

### Spring AMQP RabbitMQ服务器安装程序

我们需要进行以下设置，以便使用RabbitMQ Server开发Spring AMQP Messaging应用程序。

在RabbitMQ服务器中配置Exchange and Queue:创建一个queue=“tpQueue”
,单击“Add queue”按钮,创建Exchange并将其映射到以前创建的队列
,点击“Add exchange”按钮,点击“tpExchange”,然后将“tpExchange”与“tpQueue”映射到一些路由键,单击“bind”按钮,现在是时候开始开发Spring AMQP RabbitMQ消息应用了！
