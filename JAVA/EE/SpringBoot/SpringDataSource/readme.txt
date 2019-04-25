带有Tomcat的Spring DataSource JNDI(Java Naming and Directory Interface)示例

之前我们看到了如何使用Spring JDBC集成实现数据库操作。
但是大多数时候企业应用程序都部署在一个servlet容器中，例如Tomcat，JBoss等。

### Spring DataSource

我们知道带有JNDI的DataSource是实现连接池并获得容器实现优势的首选方式。
今天我们将看看如何配置Spring Web应用程序以使用Tomcat提供的JNDI连接。

对于我的例子，我将使用MySQL数据库服务器并创建一个包含一些行的简单表。
我们将创建一个Spring Rest Web服务，该服务将返回JSON响应，其中包含表中所有数据的列表 (list of all the data in the table.)。

### Database Setup

```sql
CREATE TABLE `Employee` (
  `id` int(11) unsigned NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Employee` (`id`, `name`, `role`)
VALUES
	(1, 'Pankaj', 'CEO'),
	(2, 'David', 'Manager');
commit;
```

### Spring DataSource MVC项目

在Spring Tool Suite中创建一个Spring MVC项目，以便我们的spring应用程序框架代码准备就绪。

----------------------------------------------------------------

### Tomcat DataSource JNDI配置

现在我们已完成项目，最后一部分是在Tomcat容器中执行JNDI配置以创建JNDI资源。

<Resource name="jdbc/TestDB" 
      global="jdbc/TestDB" 
      auth="Container" 
      type="javax.sql.DataSource" 
      driverClassName="com.mysql.jdbc.Driver" 
      url="jdbc:mysql://localhost:3306/TestDB" 
      username="pankaj" 
      password="pankaj123" 
      
      maxActive="100" 
      maxIdle="20" 
      minIdle="5" 
      maxWait="10000"/>
      
在server.xml文件的GlobalNamingResources部分中添加以上配置。

<ResourceLink name="jdbc/MyLocalDB"
                	global="jdbc/TestDB"
                    auth="Container"
                    type="javax.sql.DataSource" />


我们还需要创建Resource Link以在我们的应用程序中使用JNDI配置，
这是将其添加到server context.xml文件中的最佳方式。

请注意，ResourceLink名称应与我们在应用程序中使用的JNDI上下文名称匹配。 
还要确保tomcat lib目录中存在MySQL jar，否则tomcat将无法创建MySQL数据库连接池。

----------------------------------------------------------------

运行Spring DataSource JNDI示例项目

localhost:9090/SpringDataSource/rest/emps:

我们的项目和服务器配置已完成，我们已准备好对其进行测试。 将项目导出为WAR文件并将其放在tomcat部署目录中。

Rest call(调用)的JSON响应如下图所示.

这就是Spring与servlet容器JNDI上下文集成的全部内容                        