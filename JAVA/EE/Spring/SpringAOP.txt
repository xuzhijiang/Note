Spring AOP概述

大多数企业应用程序都有一些常见的横切关注点，适用于不同类型的对象和模块。
一些常见的横切关注点是日志记录，事务管理，数据验证等。在"面向对象编程"中，
应用程序的模块化是通过类实现的，而在"面向切面编程"应用程序中，模块化由Aspects(切面)实现，
并且它们被配置为跨越不同的类。

Spring AOP从类中获取横切任务的直接依赖关系, 我们不可以通过普通"面向对象编程"模型实现这种功能。
例如，我们可以有一个单独的记录日志的类A，但功能类B必须再次调用这些方法来实现跨应用程序的日志记录。


面向方面的编程核心概念

在我们深入实现Spring AOP实现之前，我们应该了解AOP的核心概念:

1. Aspect(切面)：An aspect就是一个类，这个类实现了 跨越多个类的 企业应用程序关注点.
例如事务管理,记录日志等。Aspects可以是通过"Spring XML configuration" 配置的普通类，
也可以使用Spring AspectJ集成 使用@Aspect注解 将类定义为Aspect。

2. Join Point(连接点)：a join point是应用程序中的特定点，例如方法执行，
异常处理，更改对象变量值等。在Spring AOP中，a join points始终是方法的执行。

3. Advice(建议)：Advices是针对特定的join point采取的操作。
在编程方面，它们是在应用程序中达到具有匹配切入点的特定连接点时执行的方法。
您可以将Advices视为Struts2拦截器或Servlet过滤器。

4. Pointcut(切入点)：Pointcut是与join points连接点匹配的表达式，用于确定advice是否需要被执行。
Pointcut使用与连接点join point匹配的不同类型的表达式，Spring框架使用AspectJ pointcut表达式语言。

5. Target object(目标对象)：它们是应用Advice的对象。 Spring AOP是使用runtime proxies实现的，
因此该对象始终是代理对象。意味着在运行时创建子类，其中覆盖target method并根据其配置包含advices。

6. AOP proxy：Spring AOP实现使用JDK动态代理来创建 具有target类和advice invocations(调用)的Proxy类，
这些类称为AOP代理类。我们也可以通过将它添加为Spring AOP项目中的依赖项 来使用CGLIB代理。

7. Weaving(编织)：将aspects与other objects链接以创建adviced 代理对象的过程。
这可以在编译时，加载时或运行时完成。 Spring AOP在运行时执行weaving(编织)。

AOP Advice Type

根据advice的执行策略，它们具有以下类型:

1. Before Advice(建议之前)：这些advices在执行join point method之前运行。
我们可以使用@Before注释将advice type 标记为Before advice。

2. After (finally)Advice(之后（最后）建议)：在join point方法完成执行后执行的advice，
无论是正常还是抛出异常。我们可以使用@After注解创建after advice。

3. After Returning Advice(返回建议后)：有时我们只要在join point方法正常执行时才需要执行advice方法。
我们可以使用@AfterReturning注释将方法标记为返回advice后执行。

4. After Throwing Advice(抛出建议后)：只有当join point方法抛出异常时才会执行此advice，
我们可以使用它以回滚事务。我们使用@AfterThrowing注释来提供此类advice。

5. Arount Advice(围绕建议)：这是最重要和最有力的建议。这个建议围绕着join point方法，
我们也可以选择是否执行join point方法。我们可以编写在执行join point方法之前的advice代码
和在执行join point方法之后执行的advice代码。
around advice负责 去调用join point方法并此方法返回某些内容时返回值。
我们使用@Around批注来创建 around advice methods。

上面提到的几点可能听起来令人困惑，但是当我们看看Spring AOP的实现时，情况会更清楚。
让我们开始用AOP实现创建一个简单的Spring项目。 Spring支持使用AspectJ注释创建aspects，
为简单起见，我们将使用它。所有上述AOP注释都在org.aspectj.lang.annotation包中定义。

Spring Tool Suite提供有关aspects的有用信息，因此我建议您使用它。如果您不熟悉STS，
我建议您查看Spring MVC Tutorial，我已经解释了如何使用它。


-------------------------------------------------


Spring Advice with Custom Annotation Pointcut:(具有自定义 annotation Pointcut的Spring Advice)

如果你看上述所有 "advices pointcut expressions"，则有可能将它们应用于其他一些我们不希望应用的bean method上。
例如：有人在一个新的spring bean使用getName()方法定义，但是他并不希望advice pointcut expression
应用于他自己定义的getName()方法上，这就是我们应该尽可能缩小切入点表达范围的原因。

另一种方法是创建自定义注解, 并注解我们希望应用advice的方法。 这是使用@Loggable annotation
注解Employee setName（）方法的目的。也就是只应用于使用了@Loggable注解的setName() method.

Spring Framework @Transactional annotation 是Spring 事务管理(Transaction Management)的一种很好的例子。