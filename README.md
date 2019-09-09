使用Notepad++的时候，默认编码要设置为UTF-8 without BOM即可,千万
不要使用UTF-8-BOM，因为会出现所编辑文本第一个字符是？的问题.

jdk配置: 

1. JAVA_HOME: D:\Program Files\java\jdk-11.0.1
2. JAVA_HOME_32: D:\Program Files\java\jdk1.8.0_191_32bit
3. CLASSPATH: 可以不用配置
4. PATH: %JAVA_HOME%\bin;%JAVA_HOME%\jre\bin;

springcloud: https://github.com/michaelliao/springcloud

Spring、SpringMVC、MyBatis、Spring Boot:https://github.com/lenve/JavaEETest
Spring blog: https://github.com/Raysmond/SpringBlog

SSH: Struts2 + Spring + Hibernate

SSM: SprintMVC + Spring + Mybatis

https://www.zhihu.com/lives/889495940065538048
https://www.zhihu.com/lives/961612944417701888
http://jinxuliang.com/course2/CoursePortal/Details/5a9268a9a664d72f041e0a6a
http://jinxuliang.com/course2/CoursePortal/Details/54004d84137e45731c99035b

Java技术栈

JDBC基础

疯狂的Java算法——插入排序，归并排序以及并行归并排序:
https://www.cnblogs.com/zuoxiaolong/p/alg2.html

spring-boot-pay: https://gitee.com/52itstyle/spring-boot-pay/tree/master

基于spring 4.x 开发的redis集群中间件应用:redis-cache、redis-cluster-cache、session共享、消息队列等:https://github.com/timebusker?tab=overview&from=2019-02-01&to=2019-02-18

基于Spring-boot和bootstrap搭建的商城系统: https://github.com/vito16/shop

Spring Boot 学习资源:https://github.com/ityouknow/awesome-spring-boot

Halo 可能是最好的 Java 博客系统: https://github.com/halo-dev/halo

购买域名:https://www.namecheap.com/

来源: http://www.akathink.com

引用: http://objcoding.com


服务器间的Session共享。比如用户第一次请求被负载到A服务器登录，第二次发起请求时却转到了B服务器。而B服务器此时并没有该用户的Session，因此用户不得不重新登录一遍...