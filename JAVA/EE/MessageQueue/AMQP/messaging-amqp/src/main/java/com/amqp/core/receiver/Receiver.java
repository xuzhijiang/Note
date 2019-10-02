package com.amqp.core.receiver;

import com.amqp.core.common.MessageQueueConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class Receiver {

    final Log log = LogFactory.getLog(getClass());

    final AtomicLong counter = new AtomicLong(0L);

    @RabbitListener(queues = MessageQueueConstants.QUEUE_NAME)
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
}
