![](pics/Logstash01.png)

![](pics/Logstash02.png)

```shell script
bin/logstash -e 'input { stdin {} } output { stdout {} }'
```

![](pics/Logstash03.png)

![](pics/Logstash04.png)

![](pics/Logstash05.png)

# 通过Logstash读取自定义日志

![](pics/读取自定义日志01.png)

![](pics/读取自定义日志02.png)

```shell script
# 创建
touch /learn/logstash/logs/app.log
```

```shell script
input {
  file {
    path => "/learn/logstash/logs/app.log"
    start_position => "beginning"
  }
}

filter {
  mutate {
    split => {"message"=>"|"}
  }
}

output {
  stdout { codec => rubydebug}
}
```

![](pics/读取自定义日志03.png)

```shell script
./bin/logstash -f xzj.conf

echo "2019-03-15 21:21:21|ERROR|读取数据错误|参数: id=1004" >> app.log
```

# 输出到es中

![](pics/读取自定义日志04.png)

```shell script
input {
  file {
    path => "/learn/logstash/logs/app.log"
    start_position => "beginning"
  }
}

filter {
  mutate {
    split => {"message"=>"|"}
  }
}

output {
  elasticsearch {
    hosts => ["192.168.32.128:9200"]
  }
}
```

![](pics/读取自定义日志05.png)

```shell script
./bin/logstash -f xzj.conf

echo "2019-03-15 21:21:21|ERROR|读取数据错误|参数: id=1004" >> app.log
```