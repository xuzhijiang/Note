package org.kafka.core;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.kafka.examples.KafkaProperties;

import java.util.Arrays;
import java.util.Properties;

public class MsgConsumer {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);
        props.put("group.id", KafkaProperties.KAFKA_CONSUMER_GROUP_ID);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        final KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList("test"));
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                consumer.close();
            }

        });

        while (true) {
            // 从指定的Topic中消费消息，称之为订阅(subscribe)，
            // 一个consumer可以同时订阅多个Topic。
            // Conusmer消费消息的时候，使用poll(long timeout)即拉取的方式
            // poll(long timeout)在kafka2.0里面建议不用了
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d --- key = %s --- value = %s",
                        record.offset(), record.key(), record.value());
        }

    }

}
