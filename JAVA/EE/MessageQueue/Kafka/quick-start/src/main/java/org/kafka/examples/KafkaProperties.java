package org.kafka.examples;

public class KafkaProperties {

    public static final String TOPIC = "test";
    public static final String KAFKA_SERVER_URL = "your host";
    // 注意要在config/server.properties中打开:
    // advertised.listeners=PLAINTEXT://your host:9092
    // 这行代表Hostname and port the broker will advertise to producers and consumers.
    // 经纪人(broker)将向生产者和消费者宣传的主机名和端口
    public static final int KAFKA_SERVER_PORT = 9092;
    public static final String KAFKA_CONSUMER_GROUP_ID = "test-consumer-group";

//    public static final int KAFKA_PRODUCER_BUFFER_SIZE = 64 * 1024;
//    public static final int CONNECTION_TIMEOUT = 100000;
//    public static final String TOPIC2 = "topic2";
//    public static final String TOPIC3 = "topic3";
//    public static final String CLIENT_ID = "SimpleConsumerDemoClient";

    private KafkaProperties() {}
}
