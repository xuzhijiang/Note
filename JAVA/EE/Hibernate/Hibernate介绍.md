在底层，我们使用JDBC API连接到数据库并执行CRUD操作。Hibernate是建立在JDBC和JTA API之上的,Hibernate如何作为应用程序类和JDBC/JTA API之间的数据库操作的抽象层?

### Why Hibernate?

如果你看一下JDBC代码，就会有很多样板代码，并且存在资源泄漏和数据不一致的可能性，因为所有工作都需要由开发人员完成。 这是ORM工具能避免这些问题。

### Hibernate and Java Persistence API (JPA)

Hibernate提供了Java Persistence API的实现，因此我们可以将JPA注释与模型bean一起使用，而hibernate负责配置它，并且在CRUD操作中使用。

### hibernate的核心组件

1. SessionFactory (org.hibernate.SessionFactory):我们可以使用SessionFactory获取org.hibernate.Session的实例。
2. Session (org.hibernate.Session):Session是一个单线程，短期对象，表示应用程序和持久性存储之间的对话。 它包装了JDBC java.sql.Connection，并作为org.hibernate.Transaction的工厂。
3. 持久对象(Persistent objects)：持久对象是包含持久状态和业务功能的短期单线程(short-lived single threaded)对象。这些可以是普通的JavaBeans/POJO。它们只与一个org.hibernate.Session相关联。
4. 瞬态对象(Transient objects)：瞬态对象是当前未与org.hibernate.Session关联的持久类实例。它们可能已被应用程序实例化，但尚未持久存在，或者它们可能已被封闭的org.hibernate.Session实例化。
5. Transaction(org.hibernate.Transaction）：Transaction是应用程序用来指定原子工作单元的单线程，短期对象。它从底层JDBC或JTA事务中抽象出应用程序。在某些情况下，org.hibernate.Session可能跨越多个org.hibernate.Transaction。
6. ConnectionProvider(org.hibernate.connection.ConnectionProvider）：ConnectionProvider是JDBC连接的工厂。它提供了应用程序与底层javax.sql.DataSource或java.sql.DriverManager之间的抽象。它不会暴露给应用程序，但它可以由开发人员扩展。
7. TransactionFactory(org.hibernate.TransactionFactory）：org.hibernate.Transaction实例的工厂。