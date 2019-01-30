## 概述

1. JDBC是历史悠久的Java访问关系型数据库的技术，直到今天，它仍然是许多数据存取框架底层的技术，但代码比较冗余，为人所诟病。
2. Spring提供了一个JdbcTemplate对JDBC进行了封装，配合Spring Boot的自动配置功能，能比较好地消除了直接使用原生JDBC所带来的冗余代码。
3. 本讲展示使用Spring JDBC访问MySQL数据库的基本流程和编程模式。

### Spring Data

1. Spring Data是Spring技术家族中的一个大家庭，包容多个子项目，对各种不同的数据源提供数据存取功能（参看左图）。\
2. 在实际开发中，应该依据你要存取的数据源类型，从中选择合适的Spring数据存
取技术。
3. 在本讲PPT中，将介绍最简单的一个——Spring Data JDBC的使用方法

### HelloWorld流程

在创建Spring Boot项目时，添加对JDBC和要访问
的关系型数据库的引用……(即勾选JDBC和MySQL)

### 项目配置文件(pom.xml)

pom.xml中药导入的JDBC依赖， 导入的MySQL依赖，不同的数据库，应该导入不
同的依赖

>本机MySQL中有一个mydb数据库，其中有一个user表，里面放了几条测试数据……请自行创建

### 配置数据源

1. Spring Boot项目中的application.properties文件可用于放置数据源的相关参数。
2. 对于MySQL数据库来说，基于下述信息， Spring Boot己足以确定要访问的数据库类型并且加载相应的数据库驱动程序

```
spring.datasource.url=jdbc:mysql://localhost:3306/mydb?characterEncoding=utf8&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=12345678
```

>注意：不同的数据库，同一数据库的不同版本，连接url可能不一样，需要查询官方技术文档。测试数据库的连接是否成功

### 小结

1. 本讲介绍了如何在Spring Boot项目中使用JDBC访问MySQL数据库的基础知识与基本技巧。
2. 对于比较简单的项目，使用JDBC去访问数据库是可行的，但对于真实的项目，现在很少直接使用它了，所以，对这块内容，了解即可。
3. Spring JDBC技术比较直观易学，本讲只通过一个HelloWorld流程进行介绍
