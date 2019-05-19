package org.kafka.core.mydemo;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

public class Producer extends Thread {

    private final kafka.javaapi.producer.Producer<Integer, String> producer;
    private final String topic;
    private final Properties props = new Properties();

    public Producer(String topic) {
        props.put("serializer.class", "kafka.serializer.StringEncoder");//指定消息的序列化类型
        //broker的地址，并不要列出所有broker，列出其中一个即可
        props.put("metadata.broker.list", KafkaProperties.kafkaServerURL + ":" + KafkaProperties.kafkaServerPort);
        props.put("producer.type", "async");

        // 真实的KafkaProducer，泛型分别为消息的key和value的类型
        producer = new kafka.javaapi.producer.Producer<Integer, String>(new ProducerConfig(props));
        this.topic = topic;
    }

    public void run() {
        int messageNo = 1;
        for (int i = 0; i < 10; i++) {
            String messageStr = new String("Message_" + messageNo);
            System.out.println("producer - " + i + " - " + messageStr);
            producer.send(new KeyedMessage<Integer, String>(topic, messageStr));
            messageNo++;
        }
    }

}
