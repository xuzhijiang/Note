# 获取服务器整体的性能状态

    对一个数据库服务器进行性能优化需要先知道服务器当前主要的性能问题出现在哪里.

    我们可以使用show [session|global] status命令来获取想要的信息,默认是显示当前连接的所有统计参数值,
    还可以直接查询information_schema数据库中的session_status表。

[详细内容可参考](https://www.cnblogs.com/chenmh/p/4941221.html)

# 写一个脚本，获取每秒钟mysql服务器的状态

```shell script
#!/bin/bash
while true
do
# 追加
mysqladmin -uroot ext -ppassword | awk '/Queries/{q=$4}/Threads_connected/{c=$4}/Threads_running/{r=$4}END{printf("%d %d %d\n",q,c,r)}' >> status.txt
sleep 1
done
```

    注意: 通过 "mysqladmin -uroot ext -ppassword" 获得的数据也可以通过show status获得.
