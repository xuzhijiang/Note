给mysql优化，相当于给mysql诊病，首先要知道哪里病了，所以可以通过shell脚本，把某个时间段的mysql信息打印出来，然后根据数据分析诊断。

```shell script
mysqladmin -uroot ext
# 不断观察服务器的状态
# 打印包含Queries的行对应的第4列的值
mysqladmin -uroot ext | awk '/Queries/{printf("%d ", $4)}'
# 打印包含Queries，Threads_connected，Threads_running的行的value
mysqladmin -uroot ext | awk '/Queries/{printf("%d ", $4)}/Threads_connected/{printf("%d ", $4)}/Threads_running/{printf("%d\n", $4)}'
# 简化上一步脚本
mysqladmin -uroot ext | awk '/Queries/{q=$4}/Threads_connected/{c=$4}/Threads_running/{r=$4}END{printf("%d %d %d\n",q,c,r)}'
```

```shell script
# 写一个脚本，获取每秒钟mysql服务器的状态
# 脚本名字tjstatus.sh
#!/bin/bash
while true
do
mysqladmin -uroot ext | awk '/Queries/{q=$4}/Threads_connected/{c=$4}/Threads_running/{r=$4}END{printf("%d %d %d\n",q,c,r)}' >> status.txt
sleep 1
done
```

```shell script
# ab apache压力测试工具
# -c 50个并发, -n 200000 请求总的次数，请求谁：http://192.168.1.201/index.php
./ab -c 50 -n 200000 http://192.168.1.201/index.php
```