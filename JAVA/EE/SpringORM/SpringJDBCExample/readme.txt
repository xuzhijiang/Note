Spring JDBC是本教程的主题。

数据库是大多数企业应用程序不可或缺的一部分。因此，当谈到Java EE框架时，与JDBC的良好集成非常重要。

Spring Framework提供了与JDBC API的出色集成，并提供了JdbcTemplate实用程序类，
我们可以使用它来避免来自我们的数据库操作逻辑的bolier-plate代码，
例如Opening / Closing Connection，ResultSet，PreparedStatement等。

让我们首先看一个简单的Spring JDBC示例应用程序，
然后我们将看到JdbcTemplate类如何帮助我们轻松编写模块化代码，而不必担心资源是否正确关闭。

用于开发基于Spring的应用程序的Spring Tool Suite非常有用，
因此我们将使用STS来创建Spring JDBC应用程序。我们的最终项目结构如下图所示。

从STS菜单创建一个简单的Spring Maven项目，您可以选择您喜欢的任何名称，或者将我的项目名称作为SpringJDBCExample。

-----------------------------------------------------

让我们创建一个简单的表，我们将在我们的应用程序中使用CRUD操作示例:

```sql
CREATE TABLE `Employee` (
  `id` int(11) unsigned NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```