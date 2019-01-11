依赖注入 (@Autowired annotation)
JPA EntityManager (由Hibernate提供)
Transactional methods (事务方法)(AOP configured(使用AOP配置的)).

带有AOP事务的Spring ORM示例

为了配置事务方法，我们现在不使用@Transactional注释，
因为我们现在使用aspect oriented（例如：我们可以说服务包中以“get”开头的所有方法都是只读事务方法）。
 我们使用内存数据库来简化此示例，因此无需任何数据库设置。 这是一个独立的应用程序示例。
