package org.kafka.core;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.kafka.examples.KafkaProperties;

import java.util.Properties;

// 编写一个可以向kafka集群发送消息的Producer
public class MsgProducer {

    public static void main(String[] args) {
        /**
         * 这个类中唯一值得关注的可能就是Properties中的配置信息了，
         * 这些配置项有的是必须指定的(没有默认值)，有的是有默认值可以不指定的，如果指定了就覆盖默认值。
         *
         * 这里只特别介绍一个：bootstrap.servers。这个配置项的值接受kafka集群中broker的访问地址。
         * 官方的解释是：这是Kafka客户端在初始化的情况下用来连接kafka集群的，而且必须指定，
         * 不过也不需要列出集群中所有的broker地址，这个参数只是在启动的时候用一下，获取到连接之后，
         * 会自动获取到集群中所有broker的信息。
         *
         * 个人觉得这里有点奇怪，因为既然kafka是基于zookeeper的集群，那么为什么不直接连接zookeeper地址，
         * 获取到broker地址的连接信息呢？
         * 因为这样才算是彻底的将客户端与服务端解耦。也许kafka作者是有其用意的吧，不过笔者暂时未能领会为什么要这样设置。
         */
        Properties props = new Properties();
        props.put("bootstrap.servers", KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        for(int i = 0; i < 100; i++)
            producer.send(new ProducerRecord<String, String>(KafkaProperties.TOPIC, Integer.toString(i), Integer.toString(i)));
        producer.close();
    }

}
