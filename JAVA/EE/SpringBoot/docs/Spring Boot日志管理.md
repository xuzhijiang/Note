Spring Boot在所有内部日志中使用Commons Logging，但是默认配置(Spring Boot默认的日志框架Logback)也提供了对常用日志的支持，如：Java Util Logging，Log4J, Log4J2和Logback。每种Logger都可以通过配置使用控制台或者文件输出日志内容。

默认的日志输出如下：

2016-04-13 08:23:50.120  INFO 37397 --- [           main] org.hibernate.Version                    : HHH000412: Hibernate Core {4.3.11.Final}

1. 时间日期 — 精确到毫秒
2. 日志级别 — ERROR, WARN, INFO, DEBUG or TRACE
3. 进程ID
4. 分隔符 — --- 标识实际日志的开始
5. 线程名 — 方括号括起来(可能会截断控制台输出）
6. Logger名 — 通常使用源代码的类名
7. 日志内容

>我们可以通过两种方式切换至DEBUG级别：

1. 在运行命令后加入--debug标志，如：$ java -jar myapp.jar --debug
2. 在application.properties中配置debug=true，该属性置为true的时候，核心Logger(包含嵌入式容器、hibernate、spring）会输出更多内容，但是你自己应用的日志并不会输出为DEBUG级别。

>如果你的终端支持ANSI，设置彩色输出会让日志更具可读性。通过在application.properties中设置spring.output.ansi.enabled参数来支持,其中:

1. NEVER：禁用ANSI-colored输出(默认项）
2. DETECT：会检查终端是否支持ANSI，是的话就采用彩色输出(推荐项）
3. ALWAYS：总是使用ANSI-colored格式输出，若终端不支持的时候，会有很多干扰信息，不推荐使用

### 文件输出

Spring Boot默认配置只会输出到控制台，并不会记录到文件中，但是我们通常生产环境使用时都需要以文件方式记录。

>若要增加文件输出，需要在application.properties中配置logging.file或logging.path属性。

1. logging.file，设置文件，可以是绝对路径，也可以是相对路径。如：logging.file=my.log
2. logging.path，设置目录，会在该目录下创建spring.log文件，并写入日志内容，如：logging.path=/var/log
3. 日志文件会在10Mb大小的时候被截断，产生新的日志文件，默认级别为：ERROR、WARN、INFO

### 级别控制

在Spring Boot中只需要在application.properties中进行配置完成日志记录的级别控制。

配置格式：logging.level.*=LEVEL

* logging.level：日志级别控制前缀，*为包名或Logger名
* LEVEL：选项TRACE, DEBUG, INFO, WARN, ERROR, FATAL, OFF

举例：

1. logging.level.com.didispace=DEBUG：com.didispace包下所有class以DEBUG级别输出
2. logging.level.root=WARN：root日志以WARN级别输出

### 自定义日志配置

由于日志服务一般都在ApplicationContext创建前就初始化了，它并不是必须通过Spring的配置文件控制。因此通过系统属性和传统的Spring Boot外部配置文件依然可以很好的支持日志控制和管理。

根据不同的日志系统，你可以按如下规则组织配置文件名，就能被正确加载：

1. Logback：logback-spring.xml, logback-spring.groovy, logback.xml, logback.groovy
2. Log4j：log4j-spring.properties, log4j-spring.xml, log4j.properties, log4j.xml
3. Log4j2：log4j2-spring.xml, log4j2.xml
4. JDK (Java Util Logging)：logging.properties

Spring Boot官方推荐优先使用带有-spring的文件名作为你的日志配置(如使用logback-spring.xml，而不是logback.xml）

### 自定义输出格式

> 在Spring Boot中可以通过在application.properties配置如下参数控制输出格式：

1. logging.pattern.console：定义输出到控制台的样式(不支持JDK Logger）
2. logging.pattern.file：定义输出到文件的样式(不支持JDK Logger）

## Spring boot中使用log4j记录日志

>本文主要介绍如何在spring boot中引入log4j，以及一些基础用法,示例:Chapter4-2-2

#### 控制台输出

```
# 设定root日志的输出级别为INFO
# LOG4J配置
log4j.rootCategory=INFO, stdout

# 控制台输出(appender为控制台输出stdout)
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss,SSS} %5p %c{1}:%L - %m%n
```

#### 输出到文件

```
# LOG4J配置
log4j.rootCategory=INFO, stdout, file

# root日志输出
log4j.appender.file=org.apache.log4j.DailyRollingFileAppender
log4j.appender.file.file=logs/all.log
log4j.appender.file.DatePattern='.'yyyy-MM-dd
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss,SSS} %5p %c{1}:%L - %m%n
```

#### 分类输出

```xml
# com.didispace包下的日志配置
log4j.category.com.didispace=DEBUG, didifile

# com.didispace下的日志输出
log4j.appender.didifile=org.apache.log4j.DailyRollingFileAppender
log4j.appender.didifile.file=logs/my.log
log4j.appender.didifile.DatePattern='.'yyyy-MM-dd
log4j.appender.didifile.layout=org.apache.log4j.PatternLayout
log4j.appender.didifile.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss,SSS} %5p %c{1}:%L ---- %m%n

```

>可以对不同级别进行分类，比如对ERROR级别输出到特定的日志文件中，具体配置可以如下。

```xml
log4j.logger.error=errorfile

# error日志输出
log4j.appender.errorfile=org.apache.log4j.DailyRollingFileAppender
log4j.appender.errorfile.file=logs/error.log
log4j.appender.errorfile.DatePattern='.'yyyy-MM-dd
log4j.appender.errorfile.Threshold = ERROR
log4j.appender.errorfile.layout=org.apache.log4j.PatternLayout
log4j.appender.errorfile.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss,SSS} %5p %c{1}:%L - %m%n
```

#### 示例

log4j.properties中对com.didispace包下的日志定义是这样的，固定定义了DEBUG级别，并输出到名为didifile定义的appender中：

```xml

# LOG4J配置
log4j.category.com.didispace=DEBUG, didifile

# com.didispace下的日志输出
log4j.appender.didifile=org.apache.log4j.DailyRollingFileAppender
log4j.appender.didifile.file=logs/my.log
log4j.appender.didifile.DatePattern='.'yyyy-MM-dd
log4j.appender.didifile.layout=org.apache.log4j.PatternLayout
log4j.appender.didifile.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss,SSS} %5p %c{1}:%L ---- %m%n

```

## Spring Boot中使用AOP统一处理Web请求日志

AOP为Aspect Oriented Programming的缩写，意为：面向切面编程，通过`预编译方式`和`运行期动态代理`实现程序功能的统一维护的一种技术。AOP是Spring框架中的一个重要内容，它通过对既有程序定义一个切入点，然后`在其前后切入不同的执行内容`，比如常见的有：`打开数据库连接/关闭数据库连接`、`打开事务/关闭事务`、`记录日志`等。`基于AOP不会破坏原来程序逻辑，因此它可以很好的对业务逻辑的各个部分进行隔离`，从而使得`业务逻辑各部分之间的耦合度降低`，`提高程序的可重用性`，同时提高了开发的效率。

面主要讲两个内容，一个是如何在Spring Boot中引入Aop功能，二是如何使用Aop做切面去统一处理Web请求的日志。

因为需要对web请求做切面来记录日志，所以先引入web模块，并创建一个简单的hello请求的处理。

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

#### 引入AOP依赖

在Spring Boot中引入AOP就跟引入其他模块一样，非常简单，只需要在pom.xml中加入如下依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

在完成了引入AOP依赖包后，一般来说并不需要去做其他配置。也许在Spring中使用过注解配置方式的人会问是否需要在程序主类中增加@EnableAspectJAutoProxy来启用，实际并不需要。

可以看下面关于AOP的默认配置属性，其中spring.aop.auto属性默认是开启的，也就是说只要引入了AOP依赖后，默认已经增加了@EnableAspectJAutoProxy。

```
# AOP
spring.aop.auto=true # Add @EnableAspectJAutoProxy.
spring.aop.proxy-target-class=false # Whether subclass-based (CGLIB) proxies are to be created (true) as
 opposed to standard Java interface-based proxies (false).
```

>而当我们需要使用CGLIB来实现AOP的时候，需要配置spring.aop.proxy-target-class=true，不然默认使用的是标准Java的实现。

### 优化：AOP切面的优先级

由于通过AOP实现，程序得到了很好的解耦，但是也会带来一些问题，比如：我们可能会对Web层做多个切面，校验用户，校验头信息等等，这个时候经常会碰到切面的处理顺序问题。

所以，我们需要定义每个切面的优先级，我们需要@Order(i)注解来标识切面的优先级。i的值越小，优先级越高。假设我们还有一个切面是CheckNameAspect用来校验name必须为didi，我们为其设置@Order(10)，而上文中WebLogAspect设置为@Order(5)，所以WebLogAspect有更高的优先级，这个时候执行顺序是这样的：

在@Before中优先执行@Order(5)的内容，再执行@Order(10)的内容
在@After和@AfterReturning中优先执行@Order(10)的内容，再执行@Order(5)的内容
所以我们可以这样子总结：

* 在切入点前的操作，按order的值由小到大执行
* 在切入点后的操作，按order的值由大到小执行