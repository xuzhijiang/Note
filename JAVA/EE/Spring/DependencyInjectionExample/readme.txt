Java依赖注入设计模式允许我们删除硬编码的依赖项，并使我们的应用程序松散耦合，可扩展和可维护。 
我们可以在java中实现依赖注入，以将依赖项解析从编译时移动到运行时。

java中的依赖注入至少需要以下内容：
 
服务组件应设计为基类或接口。 最好选择定义服务契约的接口或抽象类。
消费者类应该根据服务接口来编写。
注入器类将初始化服务，然后是消费者类。

DI是Dependency Injection的缩写.

Benefits of Java Dependency Injection

1. Separation of Concerns
2. Boilerplate Code reduction in application classes because 
all work to initialize dependencies is handled by the injector component
3. Configurable components makes application easily extendable可配置组件使应用程序易于扩展
4. Unit testing is easy with mock objects使用模拟对象可以轻松进行单元测试

Disadvantages of Java Dependency Injection

1.If overused, it can lead to maintenance issues because effect 
of changes are known at runtime.如果过度使用，则可能导致维护问题，因为在运行时已知更改的影响。

2. Dependency injection in java hides the service class dependencies 
that can lead to runtime errors that would have been caught at compile time.
java中的依赖注入隐藏了可能导致在编译时捕获的运行时错误的服务类依赖项。

It’s good to know and use it when we are in control of the services.
 当我们控制服务时，了解并使用它是很好的。