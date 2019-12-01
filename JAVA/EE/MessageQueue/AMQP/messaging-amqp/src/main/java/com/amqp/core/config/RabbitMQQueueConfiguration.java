package com.amqp.core.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQQueueConfiguration {

    @Value("${queue.a}")
    private String queueA;

    @Value("${queue.b}")
    private String queueB;

    @Value("${queue.c}")
    private String queueC;

    @Value("${queue.d}")
    private String queueD;

    @Value("${queue.e}")
    private String queueE;

    @Value("${queue.f}")
    private String queueF;

    /**
     * 这个bean的名字默认就是方法的名字
     */
    @Bean
    public Queue queue_a() {
        /**
         * 会自动创建一个叫 ${queue.a} 的queue
         */
        // durable = false, autoDelete = true
        return new Queue(queueA, false, false, true);
    }

    @Bean
    public Queue queue_b() {
        return new Queue(queueB, false, false, true);
    }

    @Bean
    public Queue queueC() {
        return new Queue(queueC, false, false, true);
    }

    @Bean
    public Queue queueD() {
        return new Queue(queueD, false, false, true);
    }

    @Bean
    public Queue queueE() {
        return new Queue(queueE, false, false, true);
    }

    @Bean
    public Queue queueF() {
        return new Queue(queueF, false, false, true);
    }

}

