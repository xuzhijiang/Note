## 总结

SpringBoot启动的时候，不论调用什么方法，都会构造一个SpringApplication的实例，然后调用这个实例的run方法，这样就表示启动SpringBoot。

>在run方法调用之前，也就是构造SpringApplication的时候会进行初始化的工作，初始化的时候会做以下几件事：

1. 把参数sources设置到SpringApplication属性中，这个sources可以是任何类型的参数。本文的例子中这个sources就是FirstSpringbootAppApplication的class对象.
2. 判断是否是web程序
3. 找出所有的初始化器，默认有5个，设置到initializers属性中
4. 找出所有的应用程序监听器，默认有9个，设置到listeners属性中
5. 找出运行的主类(main class)

>SpringApplication构造完成之后调用run方法，启动SpringApplication，run方法执行的时候会做以下几件事：

1. 构造一个StopWatch，观察SpringApplication的执行
2. 找出所有的SpringApplicationRunListener并封装到SpringApplicationRunListeners中，用于监听run方法的执行。监听的过程中会封装成事件并广播出去让初始化过程中产生的应用程序监听器进行监听
3. 构造Spring容器(ApplicationContext)，并返回
    
    * 创建Spring容器的判断是否是web环境，是的话构造AnnotationConfigEmbeddedWebApplicationContext，否则构造AnnotationConfigApplicationContext
    * 初始化过程中产生的初始化器在这个时候开始工作
    * Spring容器的刷新(完成bean的解析、各种processor接口的执行、条件注解的解析等等)

4. 从Spring容器中找出ApplicationRunner和CommandLineRunner接口的实现类并排序后依次执行

>示例:用来验证分析的启动逻辑，包括自定义的初始化器、监听器、ApplicationRunner和CommandLineRunner。

## SpringApplication的构造过程

>以first_springboot_app为例

>ApplicationContextInitializer，应用程序初始化器，做一些初始化的工作：

```java
public interface ApplicationContextInitializer<C extends ConfigurableApplicationContext> {
    void initialize(C applicationContext);
}
```

>ApplicationListener，应用程序事件(ApplicationEvent)监听器：

```java
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {
    void onApplicationEvent(E event);
}
```

>这里的应用程序事件(ApplicationEvent)有应用程序启动事件(ApplicationStartedEvent)，失败事件(ApplicationFailedEvent)，准备事件(ApplicationPreparedEvent)等。
应用程序事件监听器(Listener)跟监听事件(Event)是绑定的。比如ConfigServerBootstrapApplicationListener只跟ApplicationEnvironmentPreparedEvent事件绑定，LiquibaseServiceLocatorApplicationListener只跟ApplicationStartedEvent事件绑定，LoggingApplicationListener跟所有事件绑定等。

>默认情况下，SpringApplication的构造方法从spring.factories文件中找出的key为ApplicationContextInitializer的类有5个：

```properties
org.springframework.boot.context.config.DelegatingApplicationContextInitializer
org.springframework.boot.context.ContextIdApplicationContextInitializer
org.springframework.boot.context.ConfigurationWarningsApplicationContextInitializer
org.springframework.boot.context.web.ServerPortInfoApplicationContextInitializer
org.springframework.boot.autoconfigure.logging.AutoConfigurationReportLoggingInitializer
```

>key为ApplicationListener的有9个：

```properties
org.springframework.boot.context.config.ConfigFileApplicationListener
org.springframework.boot.context.config.AnsiOutputApplicationListener
org.springframework.boot.logging.LoggingApplicationListener
org.springframework.boot.logging.ClasspathLoggingApplicationListener
org.springframework.boot.autoconfigure.BackgroundPreinitializer
org.springframework.boot.context.config.DelegatingApplicationListener
org.springframework.boot.builder.ParentContextCloserApplicationListener
org.springframework.boot.context.FileEncodingApplicationListener
org.springframework.boot.liquibase.LiquibaseServiceLocatorApplicationListener
```

### ConfigurationClassPostProcessor类解析bean的过程

这里需要说明的是ConfigurationClassPostProcessor这个processor是优先级最高的被执行的processor(实现了PriorityOrdered接口)。这个ConfigurationClassPostProcessor会去BeanFactory中找出所有有@Configuration注解的bean，然后使用ConfigurationClassParser去解析这个类。ConfigurationClassParser内部有个Map<ConfigurationClass, ConfigurationClass>类型的configurationClasses属性用于保存解析的类，ConfigurationClass是一个对要解析的配置类的封装，内部存储了配置类的注解信息、被@Bean注解修饰的方法、@ImportResource注解修饰的信息、ImportBeanDefinitionRegistrar等都存储在这个封装类中。

这里ConfigurationClassPostProcessor最先被处理还有另外一个原因是如果程序中有自定义的BeanFactoryPostProcessor，那么这个PostProcessor首先得通过ConfigurationClassPostProcessor被解析出来，然后才能被Spring容器找到并执行。(ConfigurationClassPostProcessor不先执行的话，这个Processor是不会被解析的，不会被解析的话也就不会执行了)。

在我们的程序中@SpringBootApplication注解带有@Configuration注解，所以这个配置类会被ConfigurationClassParser解析。解析过程如下：

处理@PropertySources注解：进行一些配置信息的解析
处理@ComponentScan注解：使用ComponentScanAnnotationParser扫描basePackage下的需要解析的类(@SpringBootApplication注解也包括了@ComponentScan注解，只不过basePackages是空的，空的话会去获取当前@Configuration修饰的类所在的包)，并注册到BeanFactory中(这个时候bean并没有进行实例化，而是进行了注册。具体的实例化在finishBeanFactoryInitialization方法中执行)。对于扫描出来的类，递归解析
处理@Import注解：先递归找出所有的注解，然后再过滤出只有@Import注解的类，得到@Import注解的值。比如查找@SpringBootApplication注解的@Import注解数据的话，首先发现@SpringBootApplication不是一个@Import注解，然后递归调用修饰了@SpringBootApplication的注解，发现有个@EnableAutoConfiguration注解，再次递归发现被@Import(EnableAutoConfigurationImportSelector.class)修饰，还有@AutoConfigurationPackage注解修饰，再次递归@AutoConfigurationPackage注解，发现被@Import(AutoConfigurationPackages.Registrar.class)注解修饰，所以@SpringBootApplication注解对应的@Import注解有2个，分别是@Import(AutoConfigurationPackages.Registrar.class)和@Import(EnableAutoConfigurationImportSelector.class)。找出所有的@Import注解之后，开始处理逻辑：
遍历这些@Import注解内部的属性类集合
如果这个类是个ImportSelector接口的实现类，实例化这个ImportSelector，如果这个类也是DeferredImportSelector接口的实现类，那么加入ConfigurationClassParser的deferredImportSelectors属性中让第6步处理。否则调用ImportSelector的selectImports方法得到需要Import的类，然后对这些类递归做@Import注解的处理
如果这个类是ImportBeanDefinitionRegistrar接口的实现类，设置到配置类的importBeanDefinitionRegistrars属性中
其它情况下把这个类入队到ConfigurationClassParser的importStack(队列)属性中，然后把这个类当成是@Configuration注解修饰的类递归重头开始解析这个类
处理@ImportResource注解：获取@ImportResource注解的locations属性，得到资源文件的地址信息。然后遍历这些资源文件并把它们添加到配置类的importedResources属性中
处理@Bean注解：获取被@Bean注解修饰的方法，然后添加到配置类的beanMethods属性中
处理DeferredImportSelector：处理第3步@Import注解产生的DeferredImportSelector，进行selectImports方法的调用找出需要import的类，然后再调用第3步相同的处理逻辑处理
这里@SpringBootApplication注解被@EnableAutoConfiguration修饰，@EnableAutoConfiguration注解被@Import(EnableAutoConfigurationImportSelector.class)修饰，所以在第3步会找出这个@Import修饰的类EnableAutoConfigurationImportSelector，这个类刚好实现了DeferredImportSelector接口，接着就会在第6步被执行。第6步selectImport得到的类就是自动化配置类。

EnableAutoConfigurationImportSelector的selectImport方法会在spring.factories文件中找出key为EnableAutoConfiguration对应的值，有81个，这81个就是所谓的自动化配置类(XXXAutoConfiguration)。

ConfigurationClassParser解析完成之后，被解析出来的类会放到configurationClasses属性中。然后使用ConfigurationClassBeanDefinitionReader去解析这些类。

这个时候这些bean只是被加载到了Spring容器中。下面这段代码是ConfigurationClassBeanDefinitionReader的解析bean过程：

```java
public void loadBeanDefinitions(Set<ConfigurationClass> configurationModel) {
  TrackedConditionEvaluator trackedConditionEvaluator = new TrackedConditionEvaluator();
  for (ConfigurationClass configClass : configurationModel) {
    // 对每一个配置类，调用loadBeanDefinitionsForConfigurationClass方法
    loadBeanDefinitionsForConfigurationClass(configClass, trackedConditionEvaluator);
  }
}

private void loadBeanDefinitionsForConfigurationClass(ConfigurationClass configClass,
    TrackedConditionEvaluator trackedConditionEvaluator) {
  // 使用条件注解判断是否需要跳过这个配置类
  if (trackedConditionEvaluator.shouldSkip(configClass)) {
    // 跳过配置类的话在Spring容器中移除bean的注册
    String beanName = configClass.getBeanName();
    if (StringUtils.hasLength(beanName) && this.registry.containsBeanDefinition(beanName)) {
      this.registry.removeBeanDefinition(beanName);
    }
    this.importRegistry.removeImportingClassFor(configClass.getMetadata().getClassName());
    return;
  }

  if (configClass.isImported()) {
    // 如果自身是被@Import注释所import的，注册自己
    registerBeanDefinitionForImportedConfigurationClass(configClass);
  }
  // 注册方法中被@Bean注解修饰的bean
  for (BeanMethod beanMethod : configClass.getBeanMethods()) {
    loadBeanDefinitionsForBeanMethod(beanMethod);
  }
  // 注册@ImportResource注解注释的资源文件中的bean
  loadBeanDefinitionsFromImportedResources(configClass.getImportedResources());
  // 注册@Import注解中的ImportBeanDefinitionRegistrar接口的registerBeanDefinitions
  loadBeanDefinitionsFromRegistrars(configClass.getImportBeanDefinitionRegistrars());
}
```