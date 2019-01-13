Spring JdbcTemplate是Spring JDBC包中最重要的类。

### Spring JdbcTemplate

1. JDBC产生了大量的样板代码，例如打开/关闭一个到数据库的连接，处理sql异常等。它使代码非常麻烦且难以阅读。

2. 在Spring Framework中实现JDBC  takes care of working with many low-level operations 
(需要处理许多低级操作)（打开/关闭连接，执行SQL查询等）。

3. 多亏了这一点，在Spring Framework中使用数据库时，
我们只需要从数据库定义连接参数并注册SQL查询，我们的其余工作由Spring执行。

4. Spring中的JDBC有几个类（几种方法）用于与数据库交互。
其中最常见的是使用JdbcTemplate类。这是管理所有事件和数据库连接的处理的基类。

5. JdbcTemplate类执行SQL查询，遍历ResultSet，检索被调用的值，更新指令和过程调用，
“捕获”异常，并将它们转换为org.springframwork.dao包中定义的异常。

6. JdbcTemplate类的实例是线程安全的。这意味着通过配置JdbcTemplate类的单个实例，
我们可以将它用于几个DAO对象。

7. 使用JdbcTemplate时，通常是在Spring配置文件中配置它。
之后，它在DAO类中使用bean实现。

### Spring JdbcTemplate示例

我们来看看Spring JdbcTemplate示例程序。我在这里使用Postgresql数据库，
但您也可以使用任何其他关系数据库，例如MySQL和Oracle。您所需要的只是更改数据库配置，它应该工作。

首先，我们需要一些样本数据来处理。 SQL查询下面将创建一个表并用一些数据填充它供我们使用:

```sql
create table people (
id serial not null primary key,
first_name varchar(20) not null,
last_name varchar(20) not null,
age integer not null
);

insert into people (id, first_name, last_name, age) values
(1, 'Vlad', 'Boyarskiy', 21),
(2,'Oksi', ' Bahatskaya', 30),
(3,'Vadim', ' Vadimich', 32);
```