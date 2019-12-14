springboot2.0后,微服务架构下,tomcat基本上不再用,而是使用Undertow替换tomcat,能够增加吞吐量.


tomcat现在基本用不到了,都会用Undertow了


讲Undertow的时候使用了jmeter做高并发压力测试,这个时候Undertow基本上每秒的QPS比tomcat高出1到3000左右的.


所以现在人家会问你有没有把springboot2.1以后的版本部署在Undertow下面,

