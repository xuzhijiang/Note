KafkaMQ是一种高吞吐量的分布式发布订阅消息系统.

### 在应用场景方面

消息发布订阅系统,它主要用于处理活跃的流式数据,大数据量的数据处理上。

### 在架构模型方面

kafka遵从一般的MQ结构，producer，broker，consumer，以consumer为中心，消息的消费信息保存的客户端consumer上，consumer根据消费的点，从broker上批量pull数据；无消息确认机制。

### 在吞吐量

kafka具有高的吞吐量，内部采用消息的批量处理，zero-copy机制，数据的存储和获取是本地磁盘顺序批量操作，具有O(1)的复杂度，消息处理的效率很高。

### 在可用性方面

kafka的broker支持主备模式。

### 在集群负载均衡方面

kafka采用zookeeper(ZooKeeper是一个分布式的，开放源码的分布式应用程序协调服务)对集群中的broker、consumer进行管理，可以注册topic到zookeeper上；通过zookeeper的协调机制，producer保存对应topic的broker信息，可以随机或者轮询发送到broker上；并且producer可以基于语义指定分片，消息发送到broker的某分片上。
