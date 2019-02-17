### 专业术语

* Producer: 消息生产者，负责产生消息，一般由业务系统负责产生消息。
* Consumer: 消息消费者，负责消费消息，一般是后台系统负责异步消费。
* Push Consumer: Consumer的一种，应用通常向Consumer对象注册一个Listener 接口，一旦收到消息，Consumer对象立刻回调Listener 接口方法。
* Pull Consumer: Consumer 的一种，应用通常主动调用 Consumer 的拉消息方法从 Broker拉消息，主动权由应用控制。


