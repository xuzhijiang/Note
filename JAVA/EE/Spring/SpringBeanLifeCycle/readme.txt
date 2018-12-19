今天我们将研究Spring Bean生命周期。 Spring Bean是任何Spring应用程序中最重要的部分。
Spring ApplicationContext负责初始化spring bean配置文件中定义的Spring Beans。

Spring Context还负责bean中的注入依赖，可以通过setter或构造函数方法，也可以通过spring自动装配。

有时我们想要初始化bean类中的资源，例如在任何客户端请求之前初始化时创建数据库连接或验证第三方服务。 
Spring框架提供了不同的方法，通过它我们可以在spring bean生命周期中提供后初始化和预破坏方法。
(post-initialization and pre-destroy methods)

1. 通过实现InitializingBean和DisposableBean接口 - 这两个接口都声明了一个方法，
我们可以在其中初始化/关闭bean中的资源。对于后期初始化(post-initialization)，
我们可以实现InitializingBean接口并提供afterPropertiesSet（）方法的实现。
对于pre-destroy，我们可以实现DisposableBean接口并提供destroy（）方法的实现。
这些方法是回调方法，类似于servlet侦听器实现。
这种方法易于使用，但不推荐使用，因为它会在我们的bean实现中与Spring框架建立紧密耦合。

2. 在spring bean配置文件中为bean提供init-method和destroy-method属性值。
这是推荐的方法，因为没有直接依赖于spring框架，我们可以创建自己的方法。

请注意，init-init和pre-destroy方法都应该没有参数，但它们可以抛出异常。
我们还需要从spring应用程序上下文中获取bean实例以进行这些方法调用。

Spring Bean Life Cycle – @PostConstruct, @PreDestroy Annotations

Spring框架还支持@PostConstruct和@PreDestroy注释，用于定义post-init和pre-destroy方法。 
这些注释是javax.annotation包的一部分。 但是，要使这些注释起作用，我们需要配置spring应用程序以查找注释。 
我们可以通过定义org.springframework.context.annotation.CommonAnnotationBeanPostProcessor
类型的bean或spring bean配置文件中的context：annotation-config元素来实现。

让我们编写一个简单的Spring应用程序来展示上述配置对spring bean生命周期管理的使用。
在Spring Tool Suite中创建一个Spring Maven项目.

Spring Bean Life Cycle Important Points:

1. 从控制台输出中可以清楚地看到，Spring Context首先使用no-args构造函数来初始化bean对象，
然后调用post-init方法。

2. bean初始化的顺序与bean在spring bean配置文件中定义的顺序相同。

3. 仅当使用post-init方法执行正确初始化所有spring bean时才返回上下文context。

4. 员工姓名打印为“Pankaj”，因为它是在post-init方法中初始化的。

5. 当上下文被关闭时，bean按照它们被初始化的相反顺序被销毁，即以LIFO（后进先出）顺序。

您可以取消注释代码以获取MyEmployeeService类型的bean，并确认输出将类似并遵循上面提到的所有要点。


Spring Aware Interfaces

有时我们需要在bean中使用Spring Framework对象来执行某些操作，
例如读取ServletConfig和ServletContext参数或者知道ApplicationContext加载的bean定义。
这就是为什么spring框架提供了一堆我们可以在我们的bean类中实现的*Aware接口。

org.springframework.beans.factory.Aware是所有这些Aware接口的根标记接口。
所有*Aware接口都是Aware的子接口，并声明由bean实现的单个setter方法。
然后spring上下文使用基于setter的依赖注入来在bean中注入相应的对象并使其可供我们使用
(make it available for our use.)。

Spring Aware接口类似于具有回调方法和实现观察者设计模式的servlet监听器。

一些重要的Aware接口是：

1. ApplicationContextAware  - 注入ApplicationContext对象，示例用法是获取bean定义名称的数组。
2. BeanFactoryAware  - 注入BeanFactory对象，示例用法是检查bean的范围。
3. BeanNameAware  - 知道配置文件中定义的bean名称。
4. ResourceLoaderAware  - 要注入ResourceLoader对象，示例用法是获取classpath中文件的输入流。
5. ServletContextAware  - 在MVC应用程序中注入ServletContext对象，示例用法是读取上下文参数和属性。
6. ServletConfigAware  - 在MVC应用程序中注入ServletConfig对象，示例用法是获取servlet配置参数。