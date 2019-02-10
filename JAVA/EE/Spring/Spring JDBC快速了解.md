## 概述

1. JDBC是历史悠久的Java访问关系型数据库的技术，直到今天，它仍然是许多数据存取框架底层的技术，但代码比较冗余，为人所诟病。
2. Spring提供了一个JdbcTemplate对JDBC进行了封装，配合Spring Boot的自动配置功能，能比较好地消除了直接使用原生JDBC所带来的冗余代码。
3. 本讲展示使用Spring JDBC访问MySQL数据库的基本流程和编程模式。

> 示例项目: `springboot2-jdbc-demo`

### Spring Data

> Spring Data是Spring技术家族中的一个大家庭，包容多个子项目，对各种不同的数据源提供数据存取功能.

### HelloWorld流程

在创建Spring Boot项目时，添加对JDBC和要访问的关系型数据库的引用……(即勾选JDBC和MySQL)

### 小结

1. 对于比较简单的项目，使用JDBC去访问数据库是可行的，但对于真实的项目，现在很少直接使用它了，所以，对这块内容，了解即可。
3. Spring JDBC技术比较直观易学，本讲只通过一个HelloWorld流程进行介绍