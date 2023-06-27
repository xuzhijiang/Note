package com.rabbitmq.springboot.core;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class RabbitMqApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(RabbitMqApplication.class, args);
	}
}
