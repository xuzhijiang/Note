## 什么是JMS

JMS代表Java消息服务(Java Message Service)。JMS API是一种Java API，它包含用于实现"基于企业的消息传递系统"的通用接口集。 JMS API仅用于在"基于Java的应用程序中实现Messaging系统"，不支持其他语言。

JMS API用于在不同系统之间创建，发送，接收和读取消息或交换消息。 一旦我们使用JMS API开发Java Messaging System，我们就可以在任何JMS Provider软件中部署相同的应用程序。

Java Application ----> JMS API ------> JMS Provider(JBoss, WebLogic, RabbitMQ etc.) (Typical JMS Architecture)

### JMS的优势

1. 松耦合：我们可以非常容易地开发松耦合应用程序。
2. 异步：我们可以非常轻松地开发异步消息传递 这意味着JMS Sender可以发送消息并继续自己的工作。 它不会等到JMS Receiver完成消息消耗。
3. 健壮,可靠：JMS确保有且仅将消息传递到目标系统一次。 因此我们可以非常轻松地开发可靠的应用
4. 互操作性：JMS API允许其他Java平台语言(如Scala和Groovy）之间的互操作性。

### JMS组件

> 典型的JMS系统包含以下组件：

1. JMS Client
	*JMS Sender
	* JMS Receiver
2. JMS提供程序(JMS Provider)
3. JMS管理对象(JMS Administered Objects)
	*连接工厂(ConnectionFactory)
	*目的地(Destination)
4. JMS消息(Message)

* `JMS客户端`是用于 send (or produce or publish)or receive (or consume or subscribe) 消息的Java程序。

* JMS Sender是一个JMS客户端，用于向目标系统发送消息.JMS发送者也称为JMS生产者或JMS发布者。(JMS sender is also known as JMS Producer or JMS Publisher.)

* JMS Receiver是一个JMS客户端，用于从源系统接收消息。 JMS Receiver也称为JMS消费者或JMS订阅者。(JMS Receiver is also known as JMS Consumer or JMS Subscriber.)

* JMS API是一组通用接口，不包含任何实现。

* JMS Provider是第三方系统，负责实现JMS API以向客户端提供消息传递功能。

* JMS Provider也称为MOM(面向消息的中间件-Message Oriented Middleware）软件或Message Broker或JMS Server或Messaging Server。 JMS Provider还提供了一些用于管理和控制此MOM软件的UI组件。

* JMS Message是一个对象，包含在JMS客户端之间传输的数据。

* JMS管理对象是“由管理员预先配置的用于JMS客户端使用”的JMS对象。它们是ConnectionFactory和Destination Objects。

* ConnectionFactory对象用于在Java Application和JMS Provider之间创建连接。 Application使用它与JMS Provider进行通信。

* Destinations也是由JMS客户端用于指定其"发送的消息的目标"及其"接收的消息源"的JMS对象。( There are two types of Destinations:)目的地有两种类型：队列和主题(Queue and Topic)。

### Most popular JMS Providers

S.NO.	JMS PROVIDER SOFTWARE	ORGANIZATION
1.		WebSphere MQ			IBM
2.		Weblogic Messaging		Oracle Corporation
3.		Active MQ				Apache Foundation
4.		Rabbit MQ	Rabbit Technologies(acquired by Spring Source)
5.		HornetQ					JBoss
6.		Sonic MQ				Progress Software
7.		TIBCO EMS				TIBCO
8.		Open MQ					Oracle Corporation
9.		SonicMQ					Aurea Software

### JMS消息传递模型(Messaging Models)

>JMS API支持两种消息传递模型(编程模型),以支持异构系统之间的异步消息传递。

1. Point-To-Point Model(P2P Model)
2. Publish-Subscribe Model(Pub/Sub Model)(发布/订阅模型）

#### Point-to-Point Messaging Model

			Messages		  Messages
JMS Sender ----------> Queue ------------> JMS Receiver

>P2P模型如何运作？点对点模型遵循以下概念：

1. P2P模型使用“队列”作为JMS目的地
2. 在P2P模型中，JMS发送器或JMS生产者创建消息并将消息发送到队列。
3. JMS Queue是一个受管理的对象，由管理员在JMS提供程序中创建。
4. 在P2P模型中，JMS接收器或JMS消费者从队列接收和读取消息。
5. 在P2P模型中，JMS消息被传递给一个且仅有一个JMS消费者。
6. 我们可以将任意数量的JMS发送者和JMS接收者配置到特定队列。 但是，任何消息都应该发送给一个且只有一个Receiver。
7. JMS Sender和JMS接收器之间没有时序依赖性。 这意味着当JMS发送者发送该消息时，JMS接收者可以使用消息，无论它是否存活。
8. 在此模型中，Destination将消息存储到Receiver使用之前。

##### 实现P2P消息传递模型的可能方式

1.  one sender and one receiver
2. one sender and multiple receivers
3. multiple Senders and only one Receiver
4. multiple senders and multiple

> 对于4种方式: 通信只会从一个发送者到一个接收者。 如果发送者-2向接收者-2发送消息，则仅接收者-2接收该消息并将ACK(Acknowledgement-确认）发送回发送者-2。
即P2P模型可以具有多个发送方和多个接收方，但是每个消息由一个且仅一个接收方消费。

#### Publish/Subscribe Messaging Model:

发布/订阅消息模型再次分为两类：

1. 耐用的消息传递模型(Durable Messaging Model):
耐用模型也称为持久(Persistent)消息传递模型。在此模型中，消息存储在JMS服务器中的某种存储中，直到它们正确地传递到目标。
2. 非持久性消息传递模型(Non-Durable Messaging Model):
非耐用模型也称为非持久消息传递模型。 在此模型中，消息不存储在JMS服务器中。

>Pub/Sub模型的工作原理：


			  Message		Subscribes
JMS Publisher ----> Topic  <------ JMS Subscriber
							------>
							Message

1. 发布/订阅模型使用Topic作为JMS目标。
2. JMS管理员使用JMS提供程序管理控制台并在JMS提供程序中配置所有必需的ConnectionFactory和主题对象。
3. JMS Publisher创建消息并将消息发布到主题。
4. JMS订阅者订阅感兴趣的主题并使用所有消息。
5. 发布/订阅消息模型具有时序依赖性。 这意味着JMS订阅者只有在订阅该主题后才能使用"发布到主题的消息"。 在订阅之前发布的任何消息或在其处于非活动状态时发布的任何消息都无法发送给该消费者。
6. 与P2P模型不同，在此模型中，Destination不存储消息。

>就像P2P模型一样，Pub/Sub模型也包含很多可能的架构:

* 一个发布者和许多订阅者
* 许多发布者和一个订阅者
* 许多发布者和许多订阅者

### Differences between P2P and Pub/Sub Messaging Model:


S.NO.	POINT-TO-POINT MESSAGING MODEL	PUBLISH/SUBSCRIBE MESSAGING MODEL
1.	每条消息都传递给一个且只有一个JMS接收器   每条消息都会传递给多个消费者
2.	P2P模型没有时序依赖性.			Pub/Sub model 有时序依赖性.
3.	JMS Receiver收到消息后会向JMS Sender发送确认。 	不需要确认。

### JMS Administered Objects and JMS Message

JMS API为JMS客户端提供两种管理对象:

1. 连接工厂
2 目的地

JMS系统管理员使用Application Server(AS）管理控制台在JMS提供程序(Message Broker）中创建这两个Administered对象。这两个对象存储在AS JNDI目录(The Java Naming and Directory Interface)或JNDI注册表中。

>目的地

目标对象用于在JMS客户端之间交换消息。 JMS API提供两种目标：

1. Queue
2. Topic

> 连接工厂

Connection Factory对象用于在JMS Provider和JMS Client之间创建连接。当JMS客户端(JMS Sender或JMS Receiver）从JNDI Registry查找此对象时，JMS Client会收到一个Connection对象，该对象只是JMS Provider和JMS Client之间的物理连接。通过使用此连接，JMS客户端可以与Destination对象通信，以将消息发送或接收到Queue或Topic中。

						 -------------------------------
			Connection	 |	JMS Provider
JMS Client ===Messages==>|
						 |	ConnectionFactory
						 |
						 |  Destination(Queue or Topic)
						 |_______________________________

在创建这两个对象时(目标对象,Connection Factory对象)，我们需要提供三件事:

1. 名称： - 对象的名称。我们可以提供任何有效的名称
2. JNDI名称： - 这是JNDI名称，用于在JNDI目录中注册此对象。 JMS客户端使用此名称从JNDI注册表中查找这些对象，并在其应用程序中使用它们来发送或接收来自JMS提供程序的消息。
3. 资源类型： - 我们需要在此处指定资源类型。

S.NO.	ADMINISTERED OBJECTS	RESOURCE TYPE
1.	   ConnectionFactory	javax.jms.ConnectionFactory
2.	   Queue	javax.jms.Queue
3.	   Topic	javax.jms.Topic

```java
// To create a Queue Object in Oracle GlassFish Application Server

//Here I’ve provided Destination Name = TPQueue , JNDI Name = jms/TPQueue and Resource Type as javax.jms.Queue. JMS Clients can lookup this object by using the following code snippet:

Queue queue = (Queue) context.lookup("jms/TPQueue");
```

#### JMS Message

JMS客户端使用JMS消息在系统之间交换信息。 此JMS消息的类型为javax.jms.Message。

> 此JMS消息分为3部分：

1. Message Header
2. Message Properties(Optional)
3. Message Body(Optional)

##### Message Header

它是强制性的(mandatory)。 它包含JMS客户端和JMS (Providers)提供程序用于“标识”和“路由消息”的预定义(name-value pairs对).

> Predefined Headers:

1. JMSDestination
2. JMSDeliveryMode
3. JMSMessageID
4. JMSTimestamp
5. JMSCorrelationID
6. JMSReplyTo
7. JMSRedelivered
8. JMSType
9. JMSExpiration
10. JMSPriority

##### Message Properties

此部分是可选的。 这些属性是由应用程序设置或读取的自定义键值对。 这些对于支持过滤消息很有用。 我们将在Messaging Filtering Advanced JMS Concepts部分详细讨论它。

##### Message Body

此部分是可选的。 它包含从JMS Sender发送到JMS Receiver的实际消息。

> 它支持以下消息格式：

1. TextMessage
2. ObjectMessage
3. BytesMessage
4. StreamMessage
5. MapMessage

下表描述了这些JMS消息类型：https://cdn.journaldev.com/wp-content/uploads/2015/11/jms_message_types.png

### JMS Architecture and JMS API Architecture

#### JMS Architecture

JMSArchitecture.png显示了典型的消息系统/JMS系统体系结构：

>注意：Both JMS Messaging models( P2P Model and Pub/Sub model)都遵循相同的体系结构。它们仅在Destination类型中有所不同，Destination类型用于交换消息。

##### 消息系统中的常见步骤：

1. 系统管理员使用JMS提供程序提供的管理控制台，并在JMS提供程序的JNDI Repository存储库中配置所有必需的受管对象:如ConnectionFactory和Destinations。
2. Application Developer编写Producer和Consumer应用程序并将其部署到Server中。
3. 当客户端访问JMS Sender组件(程序）时，JMS Sender从JMS提供程序JNDI寄存器查找管理对象并将消息发送到目标(例如Queue）
3. JMS Destination(either Queue or Topic)保存所有JMS消息(由JMS发送者发送的消息），直到消费者使用它们。
4. 当客户端访问JMS Receiver组件(程序）时，JMS Receiver从JMS提供程序“JNDI Register中寻找受管理的对象，并从目标(例如Queue)接收消息。
5. 如果消息系统(即服务器）由于某些原因而崩溃，则系统管理员使用JMS提供程序管理控制台进行监视，查找问题的根本原因，并将消息还原到JMS Destinations。
6. 如果Destinations中存在任何未传递的JMS消息(由于消息传递系统之间存在某些消息协定），则系统管理员使用JMS提供程序管理控制台修复这些消息协定问题并将其重新发送到目标，以便目标消费者可以接收这些消息和处理它们。
7. 如果我们使用Durable or Persistent Destinations，则JMS服务器使用JMS存储来存储未传递的消息。
8. Durable or Persistent Destination存储消息，直到它们传递给所有订阅服务器，并且从它们接收到ACK。
9. JMS Stores可能是某种Flat Data文件，或者某些JMS Servers使用某种数据库来存储消息。Flat Data文件可以是XML文件或文本文件。

>注意：-在基于Java EE 7的应用程序中，JMS客户端(JMS发件人或接收者）不会从JMS提供程序查找受管理对象。
相反，JMS Provider将使用依赖注入将它们注入到所需位置的JMS客户端。

##### 高级JMS架构

典型的JMS应用程序具有以下高级体系结构:

Java Application ---> JMS API ---> JMS Provider(JBoss, Weblogic,RabbitMQ etc.) (Typical JMS Architecture)

1. JAVA Sender Application使用JMS API创建与JMS服务器的连接，创建JMS消息并将这些消息发送到目标。
2. 同样，JAVA Receiver Application使用JMS API创建与JMS服务器的连接，接收JMS消息并向发送方发送ACK（确认）。

##### 低级JMS架构

典型的JMS应用程序具有以下低级架构:

			Java Application
				|
			   JMS API
		|		|				|
	WebLogic	WebSphere	  JBoss AS
		|			|			|
	JMS Server	JMS Server	JMS Server

>我们可以在任何JMS提供程序中使用相同的JMS应用程序，并在服务器端使用一些最小配置

#### JMS API架构

JMS API遵循几乎相似的架构。 首先使用JMS服务器创建连接，然后准备消息/接收消息，处理消息并关闭连接。

>The JMS Sender API Architecture

Create ConnectionFactory -> Create JMS Session -> Create JMS Message ->
Send Message -> Close Message  (High-Level JMS Sender API Architecture)

>JMS Receiver API体系结构

Create ConnectionFactory -> Create JMS Session -> Receive JMS Message ->
Process Message -> Close Message  (High-Level JMS Receiver API Architecture)

