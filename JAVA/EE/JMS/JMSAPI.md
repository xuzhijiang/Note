### JMS API Overview: JMS 1.x and JMS 2.x

>参考:JMS1.1ClassicAPIOverview.png 和 JMS-V2.0-API-Overview.png,包含了JMSAPI的演化和发展.

>JMS API Summary:

LEGACY P2P API	LEGACY PUB-SUB API	CLASSIC API	SIMPLIFIED API
QueueConnectionFactory	TopicConnectionFactory	ConnectionFactory	ConnectionFactory
QueueConnection	TopicConnection		Connection	JMSContext
QueueSession	TopicSession		Session		JMSContext
Queue			Topic				Destination	Destination
Message			Message				Message		Message
QueueSender		TopicPublisher		MessageProducer	JMSProducer
QueueReceiver	TopicSubscriber		MessageConsumer	JMSConsumer
JMSException	JMSException		JMSException	JMSRuntimeException

### JMS API 1.1 Producer and Consumer

