# conf/server.properties

```properties
port=19092
host.name=192.168.128.128

# The number of threads doing disk I/O
# 设置成多个的时候一般下面的log.dirs也设置成多个，这样一个线程处理一个目录，性能会好很多
# 注意下面的多个目录往往以逗号来分割
num.io.threads=8

# The maximum size of a request that the socket server will accept (protection against OOM)
# 这个数不能超过java的堆栈大小
socket.request.max.bytes=104857600

############################# Log Basics #############################

# A comma seperated list of directories under which to store log files
log.dirs=/tmp/kafka/kafka/kafkaLogs

# The default number of log partitions per topic. More partitions allow greater
# parallelism for consumption, but this will also result in more files across
# the brokers.
# topic分区数
num.partitions=2

# The minimum age of a log file to be eligible for deletion
# 可以删除日志文件的最小年龄: 7天
log.retention.hours=168

# kafka每条消息存放的最大大小
message.max.byte=5048576
#kafka集群保存消息的默认份数（副本数）
default.replication.factor=2
# 取消息的最大字节数，设置为5M
replica.fetch.max.bytes=5048576

# The maximum size of a log segment file. When this size is reached a new log segment will be created.
# 超过这个大小就不再追加文件，而是新启动一个文件
log.segment.bytes=1073741824

# The interval at which log segments are checked to see if they can be deleted according
# to the retention policies
# 每隔这么多毫秒查看是否有失效的log，（上面是168小时）。有的话就删除log
log.retention.check.interval.ms=300000
# 是否启用log压缩
log.cleaner.enable=false
```

>config/consumer.properties和producer.properties也需要注意
