Spring Transaction Management是Spring框架中使用最广泛和最重要的特性之一。
事务管理在任何企业应用程序中都是一项微不足道的任务。
我们已经学习了如何将JDBC API用于事务管理。 Spring为事务管理提供了广泛的支持，
并帮助开发人员更多地关注业务逻辑，而不是担心任何系统故障导致的数据完整性。

### 使用Spring Transaction Management的一些好处是：

1. 支持声明式事务管理。在此Model中，Spring在事务方法上使用AOP来提供数据完整性。这是首选方法，适用于大多数情况。

2. 支持大多数事务API，如JDBC，Hibernate，JPA，JDO，JTA等。
我们需要做的就是使用正确的事务管理器实现类。
例如，用于JDBC事务管理的org.springframework.jdbc.datasource.DriverManagerDataSource
, 如果我们使用Hibernate作为ORM工具,则我们使用
org.springframework.orm.hibernate3.HibernateTransactionManager

3. 通过使用TransactionTemplate或PlatformTransactionManager实现支持程序化事务管理。

我们在事务管理器中需要的大多数功能都由Declarative事务管理支持，因此我们将这种方法用于示例项目。

### Spring事务管理JDBC示例

我们将创建一个简单的Spring JDBC项目，我们将在一个事务中更新多个表。
事务应该仅在所有JDBC语句成功执行时提交，否则应该回滚以避免数据不一致。

如果您了解JDBC事务管理，您可能会认为我们可以通过为connection 设置auto-commit为false
并根据所有语句的结果（提交或回滚事务）轻松完成。显然我们可以做到，但这会产生很多boiler-plate代码，仅用于transaction management。
此外，相同的代码将出现在我们正在寻找事务管理的所有位置，从而导致紧密耦合和不可维护的代码。

Spring声明式事务管理通过使用Aspect Oriented Programming来实现松散耦合并
避免在我们的应用程序中使用样板代码来解决这些问题。让我们看一下Spring如何通过一个简单的例子来做到这一点。

在我们进入Spring项目之前，让我们为我们的使用做一些数据库设置。

### Spring事务管理 - 数据库设置

我们将创建两个表供我们使用，并在单个事务中更新它们。

```sql
CREATE TABLE `Customer` (
  `id` int(11) unsigned NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Address` (
  `id` int(11) unsigned NOT NULL,
  `address` varchar(20) DEFAULT NULL,
  `country` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

我们可以在这里定义外键关系，从Address id列到Customer id列，但为了简单起见，我没有在此定义任何约束。

我们的数据库设置已准备好进行spring事务管理项目，
让我们在Spring Tool Suite中创建一个简单的Spring Maven项目

