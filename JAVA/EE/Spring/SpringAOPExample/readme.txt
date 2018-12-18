具有自定义 annotation Pointcut(切入点)的Spring Advice

如果你看上述所有 "advices pointcut expressions"，则有可能将它们应用于其他不适合的bean。 例如，
有人可以使用getName（）方法定义一个新的spring bean，并且即使它不是预期的，也会开始应用它。
 这就是我们应该尽可能缩小切入点表达范围的原因。

另一种方法是创建自定义注释, 并注释我们希望应用advice的方法。 这是使用@Loggable annotation
注解Employee setName（）方法的目的。

Spring Framework @Transactional annotation 是Spring 事务管理(Transaction Management)的一种很好的例子。