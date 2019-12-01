package com.amqp.core.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQExchangeConfiguration {

    @Value("${exchange.direct}")
    private String directExchange;

    @Value("${exchange.topic}")
    private String topicExchange;

    @Value("${exchange.fanout}")
    private String fanoutExchange;

    /**
     * 会自动创建一个topic 类型的叫 ${exchange.direct} 的交换器
     */
    @Bean
    public DirectExchange directExchange() {
        // durable = false, autoDelete = true
        return new DirectExchange(directExchange, false, true);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(topicExchange, false, true);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(fanoutExchange, false, true);
    }
}