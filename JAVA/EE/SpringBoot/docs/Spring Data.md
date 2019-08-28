# Java数据存取技术的历史变迁

1. JDBC: 冗余代码比较多，流程和资源比较难以控制
2. JPA与Hibernate: 高度封装，一度成为标配，但不太适应互联网时代的要求
3. MyBatis使用十分简单，而且能非常容易定制SQL，移动互联时代受到欢迎。

# Spring JdbcTemplate

1. JDBC产生了大量的冗余代码，例如打开/关闭一个数据库连接，处理sql异常等
2. 在Spring JdbcTemplate中，只需要配置到数据库的连接,然后写SQL查询语句即可，我们的其余工作由Spring执行。
3. JdbcTemplate类执行SQL查询，遍历ResultSet
4. JdbcTemplate类的实例是线程安全的。这意味着通过配置JdbcTemplate类的单个实例，我们可以将它用于几个DAO对象。
5. 使用JdbcTemplate来避免资源泄漏等常见错误并删除JDBC样板代码.

# JPA(Java Persistence API-Java持久化API)

1. JPA是持久化的标准接口,接口协议,JPA规范本质上就是一种ORM规范，注意不是ORM实现——因为JPA并未提供ORM实现，它只是制订了一些规范，提供了一些编程的API接口,但具体实现则由服务厂商来提供实现.(属于Java EE规范的组成部分)
2. JBoss应用服务器底层就以Hibernate作为JPA的实现,Hibernate除了作为ORM框架之外，它也是一种JPA实现,是持久化技术的实现.(3.2版本开始就已经对JPA实现了完全的支持)
3. spirng data jpa是spring提供的一套简化JPA开发的框架,Spring Data JPA 可以理解为 JPA 规范的再次封装抽象，底层还是使用了 Hibernate 的 JPA 技术实现.

>既然JPA作为一种规范——也就说JPA规范中提供的只是一些接口，显然接口不能直接拿来使用。虽然应用程序可以面向接口编程，但JPA底层一定需要某种JPA实现，否则JPA依然无法使用。

JPA是一套ORM规范，Hibernate实现了JPA规范！如图：

![](pics/jpa-hibernate.png)

![](pics/jpa-hibernate-springdatajpa.png)

## 为什么出现了JPA

>Sun之所以提出JPA规范，其目的是以官方的身份来统一各种ORM框架的规范，包括著名的Hibernate、TopLink等。开发者面向JPA接口编程，但底层的JPA实现可以任意切换：觉得Hibernate好的，可以选择Hibernate JPA实现；觉得TopLink好的，可以选择TopLink JPA实现.

## Spring Data JPA

Spring Data JPA是Spring Data系列的一部分。Spring Data JPA提供了一组Repository接口，通过动态代理实现数据存取功能

Spring Data JPA提供的一些很酷的功能是：

	1. 创建并且支持使用Spring和JPA创建的repositories(存储库)
	2. 支持QueryDSL和JPA查询
	3. 审核域类(Audit of domain classes)
	4. 支持批量加载，排序，动态查询
	5. 支持实体的XML映射
	6. 通过使用CrudRepository减少通用CRUD操作的代码大小

## 基于项目依赖让Spring Boot自动配置DataSource(数据源)

在添加了项目依赖Spring Boot的spring-boot-starter-data-jpa后,Spring Boot就会默认为你配置数据源，这些默认的数据源主要是内存数据库，如h2、hqldb和Derby等内存数据，具体是哪个，依赖于你在项目的Maven配置文件pom.xml中加入了对哪个数据库的依赖.

## SpringBoot配置多个DataSource

![](pics/SpringBoot配置多个DataSource-01.png)
![](pics/SpringBoot配置多个DataSource-02.png)
![](pics/SpringBoot配置多个DataSource-03.png)
![](pics/SpringBoot配置多个DataSource-04.png)
![](pics/SpringBoot配置多个DataSource-05.png)

### 小结

1. 应该来说， Spring Data JPA还是相当强大与灵活的，设计者考虑得相当周到。
2. Spring Data JPA的问题是封装得过多，可控性没那么强，就像是一个“黑盒子”，学习曲线比较陡峭，并且主要用于关系型数据库，因此，在实际开发中的应用受到限制。
3. 对于Spring Data JPA，了解本讲所介绍的内容也就差不多了。需要的时候， 再去官网查询其技术文档，了解其技术细节。

>提醒一下，其实这个技术领域包容非常多的技术特性，许多特性初学者不易理解，学习曲线比较陡峭。