package org.mq.core.meituan.sender;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SenderConfig {

    @Bean("message")
    public Queue queue() {
        return new Queue("sam.message.response");
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("exchange.message.response");
    }

    @Bean
    public Binding bindingExchangeQueue(@Qualifier("message") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange()).with("sam.message.routeKey.response");
    }
}
