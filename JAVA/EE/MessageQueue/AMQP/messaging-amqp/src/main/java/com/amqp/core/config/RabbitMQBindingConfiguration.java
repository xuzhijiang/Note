package com.amqp.core.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQBindingConfiguration {

    @Value("${routing.direct.one}")
    private String direct1RoutingKey;

    @Value("${routing.direct.two}")
    private String direct2RoutingKey;

    @Value("${routing.topic.orange}")
    private String topicRabbitMQRoutingKey;

    @Value("${routing.topic.color}")
    private String topicRabbitMQSpringRoutingKey;

    /**
     * 注意,如果没有给这个Binding起名字的话,默认使用的就是方法的名字作为bean的名字,
     * 这么多Binding bean的名字不可以重复
     * @param queue_a 如果有多个Queue,没有指定使用哪个bean的话,这里就是直接使用name为queue_a的bean,
     *                也就是参数的名字就是使用的bean的名字
     */
    @Bean
    public Binding bindingDirectExchangeQueueBDirect2(DirectExchange directExchange, Queue queue_a) {
        System.out.println("queueB: " + queue_a);
        System.out.println("directExchange: " + directExchange);
        return BindingBuilder.bind(queue_a).to(directExchange).with(direct2RoutingKey);
    }

    /**
     * 使用路由键（routingKey）把队列（Queue）绑定到交换器（Exchange）
     */
    @Bean
    public Binding bindingDirectExchangeQueueADirect1(DirectExchange directExchange, Queue queue_b) {
        System.out.println("queueA: " + queue_b);
        System.out.println("directExchange: " + directExchange);
        return BindingBuilder.bind(queue_b).to(directExchange).with(direct1RoutingKey);
    }

    @Bean
    public Binding bindingTopicExchangeQueueCTopicRabbitMQ(TopicExchange topicExchange, Queue queueC) {
        System.out.println("topicRabbitMQRoutingKey: " + topicRabbitMQRoutingKey);
        return BindingBuilder.bind(queueC).to(topicExchange).with(topicRabbitMQRoutingKey);
    }

    @Bean
    public Binding bindingTopicExchangeQueueDTopicRabbitMQSpring(TopicExchange topicExchange, Queue queueD) {
        System.out.println("topicRabbitMQSpringRoutingKey: " + topicRabbitMQSpringRoutingKey);
        return BindingBuilder.bind(queueD).to(topicExchange).with(topicRabbitMQSpringRoutingKey);
    }

    @Bean
    public Binding bindingFanoutExchangeQueueEFanout(FanoutExchange fanoutExchange, Queue queueE) {
        return BindingBuilder.bind(queueE).to(fanoutExchange);
    }

    @Bean
    public Binding bindingFanoutExchangeQueueFFanout(FanoutExchange fanoutExchange, Queue queueF) {
        return BindingBuilder.bind(queueF).to(fanoutExchange);
    }
}

