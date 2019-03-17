package com.feiyangedu.springcloud.messaging;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * JMS（JAva消息服务）：基于JVM消息代理的规范，ActiveMQ、HornetMQ是JMS实现。
 *
 * JMS定义了Java API层面的标准，在java体系中，
 * 多个客户端 均可以使用JMS进行交互，不需要修改代码，对跨平台支持性较差
 */
@SpringBootApplication
@RestController
@EnableJms//开启Jms注解支持
public class MessagingApplication {

	final Log log = LogFactory.getLog(getClass());
	final AtomicLong counter = new AtomicLong(0L);

	final static String QUEUE_NAME = "notify";
	final static String TOPIC_NAME = "topicBroadcast";

	@Autowired
	JmsTemplate jmsTemplate;

	/**
	 * Serialize message content to JSON and using TextMessage
	 */
	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

	/**
	 * @JmsListener注解在方法上监听消息代理(Message broker)发布的消息
	 * @param notify
	 */
	@JmsListener(destination = QUEUE_NAME)// 监听目的地的名字
	public void processQueueMessage(Notify notify) {
		long n = counter.incrementAndGet();
		log.info("\n[" + n + "] MESSAGE RECEIVED FROM QUEUE:\n" + notify);
	}

	@JmsListener(destination = TOPIC_NAME)
	public void processTopicMessage1(Notify notify) {
		long n = counter.incrementAndGet();
		log.info("\n[" + n + "] topic processor 1:\n" + notify);
	}

	@JmsListener(destination = TOPIC_NAME)
	public void processTopicMessage2(Notify notify) {
		long n = counter.incrementAndGet();
		log.info("\n[" + n + "] topic processor 2:\n" + notify);
	}

	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public String sendMessage(@RequestParam(name = "message", defaultValue = "") String message) {
		if (message.isEmpty()) {
			message = UUID.randomUUID().toString();
		}
		// 第一个参数是目的地的名字，上面的JmsListener会监听
		jmsTemplate.convertAndSend(QUEUE_NAME, new Notify("System Admin", "Guest", message));
		return "Message sent ok!";
	}

	@RequestMapping(value = "/broadcast", method = RequestMethod.GET)
	public String broadcastMessage(@RequestParam(name = "message", defaultValue = "") String message) {
		if (message.isEmpty()) {
			message = UUID.randomUUID().toString();
		}
		jmsTemplate.convertAndSend(TOPIC_NAME, new Notify("System Admin", "Guest", message));
		return "Message broadcast ok!";
	}

	@Bean
	public DestinationResolver namingDestinationResolver() {
		return new DestinationResolver() {
			@Override
			public Destination resolveDestinationName(Session session, String destinationName, boolean pubSubDomain)
					throws JMSException {
				log.debug("resolve destination name: " + destinationName);
				if (destinationName.startsWith("topic")) {
					return session.createTopic(destinationName);
				}
				return session.createQueue(destinationName);
			}

		};
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MessagingApplication.class, args);
	}
}
