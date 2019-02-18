ActiveMQ Server提供了JMS API以与Spring Framework集成，以使用Spring AMQP开发Message应用程序。

1. Download: http://activemq.apache.org/download.html
2. `CMD> cd E:\apache-activemq-5.9.0\bin\win64`
3. Start server by using “activemq.bat” as shown below:`E:\apache-activemq-5.9.0\bin\win64>activemq.bat`
4. 访问Apache ActiveMQ Server admin控制台,然后创建Queues or Topics：访问`http://localhost:8161/admin/`,登录用户名/密码:admin/admin,
5. 要查看现有Queues，请单击“Queues”链接。
6. 同样，单击“Topics”可查看现有主题，“Subscribers”可查看现有订阅者，单击“Connections”可查看现有连接。
7. 创建新的Queues或Topics: 队列名称：`jms/TPQueue`,单击“create”按钮。
创建Topics: Topics名称：`jms/TPTopic`
8. 到此:我们已成功安装和设置ActiveMQ Server与主题或队列。

### Spring AMQP标签库

>Spring Framework提供了两种AMQP标记库来与以下AMQP服务器一起使用：

1. RabbitMQ AMQP标签库: Spring RabbitMQ AMQP标记库用于使用Spring AMQP API和Spring RabbitMQ API为RabbitMQ Server开发消息传递应用程序。

>Spring RabbitMQ AMQP标记库在“spring-rabbit.xsd”文件中定义。

2. ActiveMQ AMQP标签库:Spring ActiveMQ AMQP标记库用于使用Spring AMQP API和Apache ActiveMQ API为Apache ActiveMQ Server开发消息传递应用程序。

>Spring RabbitMQ AMQP标记库在“activemq-core.xsd”文件中定义。

### Spring AMQP与ActiveMQ的优点和缺点

与“Spring AMQP与RabbitMQ Server”组合相比，“Spring AMQP与ActiveMQ Server”组合的优点和缺点是什么:

>“Spring AMQP与ActiveMQ”组合具有以下优势：

1. Apache ActiveMQ Server非常好地支持XA事务。
2. 我们可以轻松地将Apache ActiveMQ MQ Broker嵌入到Java应用程序中。
3. 将“Spring AMQP与ActiveMQ Application”与Apache Camel集成非常容易。
尽管“Spring AMQP With ActiveMQ”组合有许多好处，但它有一个主要缺点：

>ActiveMQ需要更多内存来维护应用程序。

### Create JMS Queues in ActiveMQ Server

1. 使用新的ActiveMQ管理控制台创建JMS队列
2. 单击“+创建”按钮，提供详细信息并单击“创建队列”按钮
3. 单击“浏览”按钮查看消息