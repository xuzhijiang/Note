package com.feiyangedu.springcloud.messaging;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * AMQP:高级消息队列协议，也是一个消息代理的规范，兼容JMS
 * ,RabbitMQ是AMQP的实现,天然具有跨平台，跨语言的特性。
 */
@SpringBootApplication
@RestController
public class MessagingApplication {

	final Log log = LogFactory.getLog(getClass());
	final AtomicLong counter = new AtomicLong(0L);

	public final static String QUEUE_NAME = "notify";
	final static String EXCHANGE = "exchange";

	/**
	 * 通过注入AmqpTemplate接口的实例来实现消息的发送(其类似于消息生产者Sender),
	 * AmqpTemplate接口定义了一套针对AMQP协议的基础操作。在Spring Boot中会根据配置来注入其具体实现
	 */
	@Autowired
	AmqpTemplate amqpTemplate;

	/**
	 * 用来配置队列、交换器、路由等高级信息
	 * @return
	 */
	@Bean
	public Queue queue() {
		// durable = false, autoDelete = true
		return new Queue(QUEUE_NAME, false, false, true);
	}

	/**
	 * 用来配置队列、交换器、路由等高级信息
	 * @return
	 */
	@Bean
	TopicExchange exchange() {
		// durable = false, autoDelete = true
		return new TopicExchange(EXCHANGE, false, true);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(QUEUE_NAME);
	}

	@RabbitListener(queues = QUEUE_NAME)// 通过@RabbitListener注解定义对notify队列的监听
	public void processQueueMessage(String content) {
		long n = counter.incrementAndGet();
		String s = "\n+--------------------------------------------------+\n" //
				+ "|MESSAGE RECEIVED FROM QUEUE                       |\n" //
				+ "+--------------------------------------------------+\n" //
				+ "|%-50s|\n" //
				+ "+--------------------------------------------------+\n" //
				+ "|TOTAL RECEIVED: %-5d                             |\n" //
				+ "+--------------------------------------------------+\n";
		log.info(String.format(s, content, n));
	}

	@GetMapping(value = "/send")
	String sendMessage(@RequestParam(name = "message", defaultValue = "") String message) {
		if (message.isEmpty()) {
			message = "Message<" + UUID.randomUUID().toString() + ">";
		}
		// 在该生产者，我们会将message发送到名为notify的队列中。
		amqpTemplate.convertAndSend(EXCHANGE, QUEUE_NAME, message);
		return "Message sent ok!";
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MessagingApplication.class, args);
	}
}
