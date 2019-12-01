# ActiveMQ

ActiveMQ是Apache出品,

# 安装

1. Download: http://activemq.apache.org/download.html
2. `cd E:\apache-activemq-5.9.0\bin\win64`
3. Start server by using “activemq.bat” as shown below:`E:\apache-activemq-5.9.0\bin\win64>activemq.bat`
4. 访问Apache ActiveMQ Server admin控制台,然后创建Queues or Topics：访问`http://localhost:8161/admin/`,登录用户名/密码:admin/admin,
5. 单击“Queues”查看现有Queues
6. 单击“Topics”查看现有主题，“Subscribers”可查看现有订阅者，单击“Connections”可查看现有连接。
7. 创建新的Queues或Topics: 队列名称：`jms/TPQueue`,单击“create”按钮。