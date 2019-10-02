package com.amqp.core.config;

import com.amqp.core.common.MessageQueueConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        // 这里创建 ConnectionFactory ,可以填写 HAProxy 负载均衡地址与端口 。
        CachingConnectionFactory factory = new CachingConnectionFactory("localhost", 5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory){
        return new RabbitTemplate(factory);
    }

    /**
     * 会自动创建一个叫 {@link MessageQueueConstants.QUEUE_NAME} 的queue
     */
    @Bean
    public Queue queue() {
        // durable = false, autoDelete = true
        return new Queue(MessageQueueConstants.QUEUE_NAME, false, false, true);
    }

    /**
     * 会自动创建一个topic 类型的叫 {@link MessageQueueConstants.EXCHANGE} 交换器
     */
    @Bean
    TopicExchange exchange() {
        // durable = false, autoDelete = true
        return new TopicExchange(MessageQueueConstants.EXCHANGE, false, true);
    }

    /**
     * 使用路由键（routingKey）把队列（Queue）绑定到交换器（Exchange）
     */
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(MessageQueueConstants.ROUTING_KEY);
    }

}
