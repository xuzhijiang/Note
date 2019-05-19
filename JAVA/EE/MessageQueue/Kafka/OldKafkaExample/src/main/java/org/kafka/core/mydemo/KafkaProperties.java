package org.kafka.core.mydemo;

// 这个类的作用实际上就是配置文件的作用。
public interface KafkaProperties
{
    final static String zkConnect = "your host:2181";//修改为你实际的zookeeper地址
    final static  String groupId = "test-consumer-group";//consumer所属的group
    final static String topic = "test";
    final static String kafkaServerURL = "your host";//修改为你kafka broker的ip
    final static int kafkaServerPort = 9092;
    final static int kafkaProducerBufferSize = 64*1024;
    final static int connectionTimeOut = 100000;
    final static int reconnectInterval = 10000;
    final static String topic2 = "topic2";
    final static String topic3 = "topic3";
    final static String clientId = "SimpleConsumerDemoClient";
}