我们将使用Hibernate JPA  transaction management来研究Spring ORM示例。
我将向您展示一个具有以下功能的Spring独立应用程序的一个非常简单的示例:

	依赖注入 (@Autowired annotation)
	JPA EntityManager (由Hibernate提供)
	带注释(Annotated)的事务方法 (@Transactional注释)

我在Spring ORM示例中使用了内存数据库，因此不需要任何数据库设置
(但您可以在spring.xml数据源部分 将其更改为任何其他数据库）。
这是一个Spring ORM独立应用程序，可以最大限度地减少所有依赖项
(但如果你熟悉spring，则可以通过配置轻松地将其更改为Web项目）。

注意：对于基于Transactional(没有@Transactional注释）的Spring AOP的方法解析方法，
请查看本教程：Spring ORM AOP事务管理。

