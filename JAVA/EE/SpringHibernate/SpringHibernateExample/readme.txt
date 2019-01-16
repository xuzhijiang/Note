Spring Hibernate Integration Example Tutorial (Spring 4 + Hibernate 3 and Hibernate 4)

Spring是最常用的Java Framework之一，而Hibernate是最受欢迎的ORM框架。这就是Spring Hibernate组合在许多应用程序中使用的原因。

### Spring Hibernate

今天，在本教程中，我们将使用Spring 4，它与Hibernate 3集成，然后更新the same project 使用Hibernate 4.

请注意，我注意到all spring and hibernate versions都不兼容，以下版本已经worked for me,所以我认为它们是兼容的。
如果您正在使用其他版本并得到了java.lang.NoClassDefFoundError，则表示它们不兼容。
主要是因为Hibernate类正在从一个包转移到另一个包导致的。例如，org.hibernate.engine.FilterDefinition类被移动到最新的hibernate版本中的org.hibernate.engine.spi.FilterDefinition。

* Spring Framework版本：4.0.3.RELEASE
* Hibernate Core和Hibernate EntityManager版本：3.6.9.Final和4.3.5.Final
* Spring ORM版本：4.0.3.RELEASE

### 数据库设置

我正在为我的项目使用MySQL数据库，setup.sql脚本为此示例创建必要的表。
