# SLF4J原理

    SLF4J(Simple Logging Facade for Java)作为日志门面的简单外观/抽象.需要具体的实现.
    slf4j-api.jar 就是slf4j接口定义的jar

    我们以SLF4J+logback为例(springboot默认就是使用这个组合),以后开发的时候，日志记录方法的调用，
    不应该来直接调用日志的实现类，而是调用日志抽象层里面的方法；
    
    给系统里面导入slf4j-api.jar和具体实现jar: logback-classic和logback-core
    当然这个springboot的spring-boot-starter-logging自动帮我们导入了,我们直接用即可.

![](../pics/SLF4j的使用.png)

![](../pics/SLF4j的使用02.png)

