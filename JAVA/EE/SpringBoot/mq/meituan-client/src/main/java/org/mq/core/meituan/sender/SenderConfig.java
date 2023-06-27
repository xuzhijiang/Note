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

    @Bean("mt-message")
    public Queue queue() {
        return new Queue("sam.ticket");
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("mt-exchange");
    }

    @Bean
    public Binding bindingExchangeQueue(@Qualifier("mt-message") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange()).with("sam.ticket.routeKey");
    }
}
