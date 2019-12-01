package com.amqp.core.controller;

import com.amqp.core.domain.Payload;
import com.amqp.core.service.ProducerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/producer")
public class ProducerController {

    final Log log = LogFactory.getLog(getClass());

    @Autowired
    private ProducerService producerService;

    @Value("${routing.direct.one}")
    private String direct1RoutingKey;

    @Value("${routing.direct.two}")
    private String direct2RoutingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private Payload payload = new Payload("hello payload");

    @GetMapping("/direct1")
    public ResponseEntity sendDirect1Message() {
        producerService.sendToDirectExchange(payload, direct1RoutingKey);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/direct2")
    public ResponseEntity sendDirect2Message() {
        producerService.sendToDirectExchange(payload, direct2RoutingKey);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/topic/orange")
    public ResponseEntity sendTopicOrangeMessage() {
        producerService.sendToTopicExchange(payload, "quick.orange.rabbit");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/topic/color")
    public ResponseEntity sendTopicColorMessage() {
        producerService.sendToTopicExchange(payload, "quick.color.rabbit");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/fanout")
    public ResponseEntity sendFanoutMessage() {
        producerService.sendToFanoutExchange(payload);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
