## typeHandlers 类型处理器

无论是 MyBatis 在预处理语句（PreparedStatement）中设置一个参数时，还是从结果集中取出一个值时， 都会用类型处理器将获取的值以合适的方式转换成 Java 类型。下表描述了一些默认的类型处理器。 

| 类型处理器                   | Java类型                        | JDBC类型                                                     |
| ---------------------------- | ------------------------------- | ------------------------------------------------------------ |
| BooleanTypeHandler           | `java.lang.Boolean`, `boolean`  | 数据库兼容的 `BOOLEAN`                                       |
| `ByteTypeHandler`            | `java.lang.Byte`, `byte`        | 数据库兼容的 `NUMERIC` 或 `BYTE`                             |
| `ShortTypeHandler`           | `java.lang.Short`, `short`      | 数据库兼容的 `NUMERIC` 或 `SHORT INTEGER`                    |
| `IntegerTypeHandler`         | `java.lang.Integer`, `int`      | 数据库兼容的 `NUMERIC` 或 `INTEGER`                          |
| `LongTypeHandler`            | `java.lang.Long`, `long`        | 数据库兼容的 `NUMERIC` 或 `LONG INTEGER`                     |
| `FloatTypeHandler`           | `java.lang.Float`, `float`      | 数据库兼容的 `NUMERIC` 或 `FLOAT`                            |
| `DoubleTypeHandler`          | `java.lang.Double`, `double`    | 数据库兼容的 `NUMERIC` 或 `DOUBLE`                           |
| `BigDecimalTypeHandler`      | `java.math.BigDecimal`          | 数据库兼容的 `NUMERIC` 或 `DECIMAL`                          |
| `StringTypeHandler`          | `java.lang.String`              | `CHAR`, `VARCHAR`                                            |
| `ClobReaderTypeHandler`      | `java.io.Reader`                | -                                                            |
| `ClobTypeHandler`            | `java.lang.String`              | `CLOB`, `LONGVARCHAR`                                        |
| `NStringTypeHandler`         | `java.lang.String`              | `NVARCHAR`, `NCHAR`                                          |
| `NClobTypeHandler`           | `java.lang.String`              | `NCLOB`                                                      |
| `BlobInputStreamTypeHandler` | `java.io.InputStream`           | -                                                            |
| `ByteArrayTypeHandler`       | `byte[]`                        | 数据库兼容的字节流类型                                       |
| `BlobTypeHandler`            | `byte[]`                        | `BLOB`, `LONGVARBINARY`                                      |
| `DateTypeHandler`            | `java.util.Date`                | `TIMESTAMP`                                                  |
| `DateOnlyTypeHandler`        | `java.util.Date`                | `DATE`                                                       |
| `TimeOnlyTypeHandler`        | `java.util.Date`                | `TIME`                                                       |
| `SqlTimestampTypeHandler`    | `java.sql.Timestamp`            | `TIMESTAMP`                                                  |
| `SqlDateTypeHandler`         | `java.sql.Date`                 | `DATE`                                                       |
| `SqlTimeTypeHandler`         | `java.sql.Time`                 | `TIME`                                                       |
| `ObjectTypeHandler`          | Any                             | `OTHER` 或未指定类型                                         |
| `EnumTypeHandler`            | Enumeration Type                | VARCHAR-任何兼容的字符串类型，存储枚举的名称（而不是索引）   |
| `EnumOrdinalTypeHandler`     | Enumeration Type                | 任何兼容的 `NUMERIC` 或 `DOUBLE` 类型，存储枚举的索引（而不是名称）。 |
| `InstantTypeHandler`         | `java.time.Instant`             | `TIMESTAMP`                                                  |
| `LocalDateTimeTypeHandler`   | `java.time.LocalDateTime`       | `TIMESTAMP`                                                  |
| `LocalDateTypeHandler`       | `java.time.LocalDate`           | `DATE`                                                       |
| `LocalTimeTypeHandler`       | `java.time.LocalTime`           | `TIME`                                                       |
| `OffsetDateTimeTypeHandler`  | `java.time.OffsetDateTime`      | `TIMESTAMP`                                                  |
| `OffsetTimeTypeHandler`      | `java.time.OffsetTime`          | `TIME`                                                       |
| `ZonedDateTimeTypeHandler`   | `java.time.ZonedDateTime`       | `TIMESTAMP`                                                  |
| `YearTypeHandler`            | `java.time.Year`                | `INTEGER`                                                    |
| `MonthTypeHandler`           | `java.time.Month`               | `INTEGER`                                                    |
| `YearMonthTypeHandler`       | `java.time.YearMonth`           | `VARCHAR` or `LONGVARCHAR`                                   |
| `JapaneseDateTypeHandler`    | `java.time.chrono.JapaneseDate` | `DATE`                                                       |

## Mybatis批量保存

在MyBatis的全局设置中有设置，defaultExecutorType  配置默认的执行器

- SIMPLE 就是普通的执行器；
- REUSE 执行器会重用预处理语句（prepared statements）； 
- BATCH 执行器将重用语句并执行批量更新

但是，如果在全局设置中设置批量执行器，那么每一个mapper中的方法都会执行批量操作，所以我们一般都是在与Spring整合后在Application.xml中配置一个可以执行批量操作的sqlSession,如下

```xml
<!--创建出SqlSessionFactory对象  -->
<bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"></property>
    <!-- configLocation指定全局配置文件的位置 -->
    <property name="configLocation" value="classpath:mybatis-config.xml"></property>
    <!--mapperLocations: 指定mapper文件的位置-->
    <property name="mapperLocations" value="classpath:mybatis/mapper/*.xml"></property>
</bean>
<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
    <constructor-arg name="sqlSessionFactory" ref="sqlSessionFactoryBean"></constructor-arg>
    <constructor-arg name="executorType" value="BATCH"></constructor-arg>
</bean>
```

# MyBatis的缓存机制

## 概述

MyBatis 包含一个非常强大的`查询缓存`特性,它可以非常方便地配置和定制。缓存可以极大的提升查询效率。MyBatis系统中默认定义了两级缓存，一级缓存和二级缓存。

- 默认情况下，只有一级缓存开启（SqlSession级别的缓存，也称为本地缓存）。
- 二级缓存需要手动开启和配置，他是基于namespace级别的缓存。
- 为了提高扩展性。MyBatis定义了缓存接口Cache。我们可以通过实现Cache接口来自定义二级缓存

## 一级缓存（本地缓存）

与数据库同一次会话期间查询的数据会放在本地缓存中，以后如果需要获取相同数据，直接从缓存中拿，不再查询数据库

**一级缓存失效的四种情况**

一级缓存是sqlSession级别的缓存，也就说一个sqlSession拥有自己的一级缓存，一级缓存是一直开启的

- 不同的sqlSession对应不同的一级缓存
- 同一个sqlSession,但是查询条件不一样
- 同一个sqlSession两次查询期间执行了任何一次增删改操作
- 同一个sqlSession两次查询期间手动清空了缓存

## 二级缓存（全局缓存）

- 二级缓存基于namespace默认不开启，需要手动配置
- MyBatis提供二级缓存的接口以及实现，缓存实现要求POJO实现Serializable接口
- **二级缓存在SqlSession 关闭或提交之后才会生效**

**工作机制**

- 一个会话，查询一条数据，这个数据就会放在当前会话的一级缓存中
- 如果会话关闭，一级缓存中的数据就会保存到二级缓存中，新的查询信息，就参照二级缓存

**使用步骤**

- 全局配置文件中开启二级缓存

```xml
<setting name="cacheEnabled" value="true"/>
```

- 需要使用二级缓存的映射文件mapper.xml处使用cache配置缓存

```xml
<cach></cach>
```

- 注意：POJO需要实现Serializable接口

Mybatis提供了整合ehcache缓存，具体整合方法参考官网文档，