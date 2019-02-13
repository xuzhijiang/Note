> 示例项目: QuickStart

在基础篇开篇中，我们提到过mybatis在工作过程中，依赖数据源(DataSource)，
由于mybatis自带了几个简单的DataSource实现，这里我们直接使用mybatis自带的即可，
不需要引入其他数据源的依赖。

### 基于XML的使用方式

> mybatis的xml配置文件分为2类：

1. 全局配置文件：主要是进行一些全局配置(废话)，通常命名为mybatis-config.xml。
例如前面提到mybatis在操作数据库时需要依赖DataSource获取数据库连接，
DataSource应该是全局唯一的，此时就应该在mybatis-config.xml中编写。
当然，mybatis-config.xml中还会包含一些mybatis其他全局属性的配置。
2. 映射文件：通常我们会针对一个表，就建立一个映射文件，因此映射文件可能会有多个。
映射文件的命名习惯如下："类名+Mapper.xml"。例如这里要操作的User类，
其映射文件名字就应该是UserMapper.xml，表示其是User类的映射文件。
而如果还有一个UserAccount类的话，其映射文件名称则应该为UserAccountMapper.xml。
下图表示了完整映射关系： 

数据库表				user表
映射文件 			UserMapper.xml
Jave实体				User类

可以看到数据库表和Java实体类，本身都是独立的，我们映射文件来建立数据库表字段
和和Java类属性之间的映射关系。

### typeAlias别名

typeAlias，顾名思义，是类型别名，主要用于简化xml文件的配置。

默认情况下，我们在xml映射文件中配置的<insert>、<update>、<delete>、<select>元素，parameterType或resultType属性都必须指定类的全路径。

这样的配置长且烦，容易出错。mybatis提供了一个别名系统，让我们在需要填写一个类的全路径时，可以直接用类名替代。

要达到这一点，我们需要mybatis的全局配置文件mybatis-config.xml中，配置<typeAliases>元素.

#### mybatis 内置别名系统

mybatis除了支持用户自己定义类的别名，实际上mybatis框架本身也内置并大量的使用了别名。例如前面配置<select>元素中，其parameterType属性值为"int"；在mybatis-config.xml中配置的<transactionManager type="MANAGED" />、以及  <dataSource type="POOLED">等，type属性用的都是别名。

> 基本上你可以这样认为，在xml配置中，凡是出现了type或者以type结尾的属性，其值既可以使用类的全路径，也可以使用类的别名。

> 例如你可以将int改为java.lang.Integer，将POOLED改为org.apache.ibatis.datasource.pooled.PooledDataSourceFactory。这些映射关系都是通过mybatis内置别名系统完成的。

> mybatis内置别名系统的别名映射，又可以细分为2类：

1. mybatis为jdk自带的类的别名映射关系
2. mybatis框架本身的类的别名映射关系

##### mybatis为jdk自带的类的别名映射关系

这个映射列表，在mybatis官方文档上有列出，http://www.mybatis.org/mybatis-3/zh/configuration.html#typeAliases

完整的映射列表位于mybatis的org.apache.ibatis.type.TypeAliasRegistry类的构造方法中，读者可以自行查看。提醒读者，不必强行记忆这个映射关系，用到的时候知道在哪里查就可以了。

##### mybatis框架自带的类的别名映射关系

在前面的mybatis-config.xml中，<transactionManager type="MANAGED" />、以及  <dataSource type="POOLED">等，type属性用的都是别名。这些别名是mybatis为框架本身自带的类其的别名，我们可以在org.apache.ibatis.session.Configuration这个类的构造方法中，看到mybatis为哪些框架自带的类定义了别名.

### mybatis打印sql日志

mybatis支持使用多种日志框架来打印sql，包括：slf4j、commons-logging、log4j、log4j2、jdk logging、stdout、no logging等。因此在打印日志时，我们首要确定自己使用的日志框架是什么，然后进行相应的配置。

对于刚刚开始学习mybatis的读者，可以在项目中引入log4j的依赖，然后在classpath下新增配置文件log4j.properties，即可打印出sql，内容如下：

```
# 设置root logger日志打印级别为INFO，日志输出到STDOUT这个appender中
log4j.rootLogger=DEBUG,STDOUT
# 定义stdout这个STDOUT，其实现类为ConsoleAppender.表示日志输出到控制台中，读者可以使用其他appender，如DailyRollingFileAppender
log4j.appender.STDOUT=org.apache.log4j.ConsoleAppender
log4j.appender.STDOUT.layout=org.apache.log4j.PatternLayout
log4j.appender.STDOUT.layout.ConversionPattern=%d [%t] %-5p %c %x - %m%n
```

在使用mybatis打印sql日志时需要注意的几点，笔者让读者彻底掌握不同版本的mybatis如何打印日志。

1. 打印sql日志的logger一定要是debug级别的(注意这里说的不是root logger')，在其他级别下不论如何配置，mybatis也不会打印sql日志
2. mybatis 3.0.6, 3.1.0, 3.2.0版本前后打印日志的配置方式是不同的，这也是我们经常在网上照搬一些配置，但是依然打印不了sql的原因(博客的搬运工实在太多)。
3. 当应用中存在多种日志框架jar包的依赖时，如果没有进行合适的配置，也是无法打印sql的。例如slf4j和commons-logging都是facade设计模式的实现，用于统一各种日志框架，底层依赖于具体的日志框架实现如log4j、logback、log4j2、jdk logging，并且需要引入相应的桥接jar依赖。
4. mybatis 版本>=3.2.0之后，<settings>元素中提供了logPrefix和logImpl配置项来帮助配置日志框架，这也是笔者建议的mybatis日志打印方式

### 不同版本的日志打印效果演示

不同版本实现的区别在于logger的名称不同，以log4j为例：

#### mybatis版本<=3.0.6

logger的名字都以java.sql为前缀。我们可以按照如下方式配置log4j.properties

```
# 设置root logger日志打印级别为INFO，日志输出到STDOUT这个appender中
log4j.rootLogger=INFO,STDOUT
# 定义stdout这个STDOUT，其实现类为ConsoleAppender.表示日志输出到控制台中，读者可以使用其他appender，如DailyRollingFileAppender
log4j.appender.STDOUT=org.apache.log4j.ConsoleAppender
log4j.appender.STDOUT.layout=org.apache.log4j.PatternLayout
log4j.appender.STDOUT.layout.ConversionPattern=%d [%t] %-5p %c %x - %m%n
#设置mybatis打印sql日志
log4j.logger.java.sql=DEBUG
#等价于以下四行配置
#log4j.logger.java.sql.Connection=DEBUG
#log4j.logger.java.sql.Statement=DEBUG
#log4j.logger.java.sql.PreparedStatement=DEBUG
#log4j.logger.java.sql.ResultSet=DEBUG
```

此时打印的日志效果如下所示：

```
2017-11-27 22:18:58,128 [main] DEBUG java.sql.Connection  - ooo Connection Opened
2017-11-27 22:18:58,240 [main] DEBUG java.sql.PreparedStatement  - ==>  Executing: select id,name,age from user where id= ?

2017-11-27 22:18:58,241 [main] DEBUG java.sql.PreparedStatement  - ==> Parameters: 1(Integer)

2017-11-27 22:18:58,270 [main] DEBUG java.sql.ResultSet  - <==    Columns: id, name, age

2017-11-27 22:18:58,270 [main] DEBUG java.sql.ResultSet  - <==        Row: 1, tianshouzhi, 26

2017-11-27 22:18:58,272 [main] DEBUG java.sql.Connection  - xxx Connection Closed
```

#### mybatis>=3.2.0

>默认logger的名字为namespace.id。其中namespace指的是mapper映射文件的namespace属性，id指的是配置的sql的id属性。如下图所示：

此时我们可以按照如下方式配置log4j.properties

```
# 设置root logger日志打印级别为INFO，日志输出到STDOUT这个appender中
log4j.rootLogger=INFO,STDOUT
# 定义stdout这个STDOUT，其实现类为ConsoleAppender.表示日志输出到控制台中，读者可以使用其他appender，如DailyRollingFileAppender
log4j.appender.STDOUT=org.apache.log4j.ConsoleAppender
log4j.appender.STDOUT.layout=org.apache.log4j.PatternLayout
log4j.appender.STDOUT.layout.ConversionPattern=%d [%t] %-5p %c %x - %m%n
#设置mybatis打印sql日志，其中com.tianshouzhi.mybatis是所有mapper映射文件namespace属性的公共前缀
log4j.logger.com.tianshouzhi.mybatis=DEBUG
```

>这里配置了一个logger，名字为com.tianshouzhi.mybatis，它是所有mapper映射文件namespace属性的公共前缀。

此时打印效果如下：

```
2017-11-27 22:58:30,816 [main] DEBUG com.tianshouzhi.mybatis.quickstart.mapper.UserMapper.testResultMap  - ooo Using Connection [com.mysql.jdbc.JDBC4Connection@5c533a2]

2017-11-27 22:58:30,819 [main] DEBUG com.tianshouzhi.mybatis.quickstart.mapper.UserMapper.testResultMap  - ==>  Preparing: select id,name,age from user where id= ?

2017-11-27 22:58:30,911 [main] DEBUG com.tianshouzhi.mybatis.quickstart.mapper.UserMapper.testResultMap  - ==> Parameters: 1(Integer)
```

>这种方式的好处是，一个sql执行过程中，经历的ConnectionLogger、StatementLogger、PreparedStatementLogger和ResultSetLogger内部在打印日志时，内部实际上引用的都是同一个底层logger实例。而通过namespace.sqlId作为logger的名称，在查看日志时，很容易串联起来一个sql从获取连接-->执行-->结果封装的整个过程。

>特别的，mybatis 3.2.0版本中，我们可以在mybatis-config.xml文件中的在settings元素中可以配置logPrefix和logImpl配置项。例如，如果我们的项目中，mapper映射文件的namespace属性值各不相同，此时我们可以为所有的logger的名字加上一个公共的前缀，如：

```xml
<settings>
          <!--logger公共前缀，value值随意，不过记得加上一个"."-->
        <setting name="logPrefix" value="mybatis."/>
</settings>
```

此时修改log4.properties配置如下：

```
# 设置root logger日志打印级别为INFO，日志输出到STDOUT这个appender中
log4j.rootLogger=INFO,STDOUT
# 定义stdout这个STDOUT，其实现类为ConsoleAppender.表示日志输出到控制台中，读者可以使用其他appender，如DailyRollingFileAppender
log4j.appender.STDOUT=org.apache.log4j.ConsoleAppender
log4j.appender.STDOUT.layout=org.apache.log4j.PatternLayout
log4j.appender.STDOUT.layout.ConversionPattern=%d [%t] %-5p %c %x - %m%n

#设置mybatis打印sql日志，注意这里的mybatis与logPrefix相匹配
log4j.logger.mybatis=DEBUG
```

>此时sql的打印效果如下：

```
2017-11-27 22:59:21,622 [main] DEBUG mybatis.com.tianshouzhi.mybatis.quickstart.mapper.UserMapper.testResultMap  - ooo Using Connection [com.mysql.jdbc.JDBC4Connection@66869e50]

2017-11-27 22:59:21,625 [main] DEBUG mybatis.com.tianshouzhi.mybatis.quickstart.mapper.UserMapper.testResultMap  - ==>  Preparing: select id,name,age from user where id= ?

2017-11-27 22:59:21,676 [main] DEBUG mybatis.com.tianshouzhi.mybatis.quickstart.mapper.UserMapper.testResultMap  - ==> Parameters: 1(Integer)
```

可以看到，所有的logger的名字前面，都有一个mybatis前缀。

#### 3.0.6<mybatis版本<3.2.0

这两个版本之间，mybatis日志实现处于过度阶段，因此同时兼容以上两种配置。需要注意的是，logImpl和logPrefix从3.2.0版本才开始支持，因此如果3.0.6<mybatis版本<3.2.0，配置这两个属性会报错。

## log4j2配置

mybatis在3.2.3之前，并不直接支持log4j2作为日志框架，必须通过slf4j或者commons-logging来做桥接。

3.2.3之后，支持直接使用log4j2，也就是可以在<settings>元素中通过配置logImpl值为LOG4J2，不过这种方式存在一个bug，直到mybatis 3.2.8版本才修复.

>为了避免这些问题，在使用log4j2作为日志框架的话，建议直接使用slf4j做桥接。

```xml
<!--slf4j依赖-->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.25</version>
</dependency>
<!--桥接-->
<dependency> <!-- 桥接：告诉Slf4j使用Log4j2 -->
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j-impl</artifactId>
    <version>2.3</version>
</dependency>
<!--log4j2依赖-->
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-api</artifactId>
    <version>2.3</version>
</dependency>
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.3</version>
</dependency>
```

log4j2.xml:

```
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
  <Appenders>
    <Console name="Console" target="SYSTEM_OUT">
      <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
    </Console>
  </Appenders>
  <Loggers>
      <!--配置mybatis打印sql日志-->
      <!-- mybatis<3.2.0  name可以设置为java.sql-->
      <!-- mybatis>3.1.0 name可以设置为java.sql 或者namespace属性值的前缀-->
      <!-- mybatis>=3.2.0  name可以设置为通过logPrefix设置的前缀-->
    <Logger name="com.tianshouzhi" level="debug" additivity="false">
      <AppenderRef ref="Console"/>
    </Logger>
    <Root level="error">
      <AppenderRef ref="Console"/>
    </Root>
  </Loggers>
</Configuration>
```

### logback配置

mybatis并不支持直接使用logback作为日志框架， 如果要使用logback的话，必须使用slf4j做桥接。pom中引入如下依赖：

```xml
<dependency>
     <!--slf4j依赖-->
     <groupId>org.slf4j</groupId>
     <artifactId>slf4j-api</artifactId>
     <version>1.7.25</version>
</dependency>
<!--logback依赖-->
<dependency>
     <groupId>ch.qos.logback</groupId>
     <artifactId>logback-core</artifactId>
     <version>1.1.7</version>
</dependency>
<dependency>
     <groupId>ch.qos.logback</groupId>
     <artifactId>logback-classic</artifactId>
     <version>1.1.7</version>
</dependency>
```

logback.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <appender name="Console" class="ch.qos.logback.core.ConsoleAppender">
        <Target>System.out</Target>
        <encoder>
            <pattern>%d-[TS] %p %t %c - %m%n</pattern>
        </encoder>
    </appender>
    <!--配置mybatis打印sql日志-->
    <!-- mybatis<3.2.0  name可以设置为java.sql-->
    <!-- mybatis>3.1.0 name可以设置为java.sql 或者namespace属性值的前缀-->
    <!-- mybatis>=3.2.0  name可以设置为通过logPrefix设置的前缀-->
    <logger name="java.sql" additivity="false">
        <level value="debug"/>
        <appender-ref ref="Console"/>
    </logger>
    <root level="info">
        <appender-ref ref="Console"/>
    </root>
</configuration>
```

### jdk logging

笔者在实际项目开发中，并没有直接使用过jdk logging，其他三种都使用过。jdk logging是jdk自带的日志框架实现，位于java.util.logging包中。读者可以自行参照前面几种配置方式来配置jdk logging。

## 项目中存在多种日志框架的情况

有的读者可能会发现，即使按照上面的配置无误了，依然无法打印出日志。这是可能是项目中存在多种日志框架的jar包依赖，导致冲突。

>默认情况下，mybatis按照如下方式检测需要使用的日志框架实现：

slf4j-->commons-logging-->log4j2-->log4j-->jdk logging(jul)-->no logging

在3.4.0 mybatis的org.apache.ibatis.logging.LogFactory类的源码中体现了上面的描述：

其中slf4j，commons-logging都是facade设计模式的实现，底层需要依赖具体的日志框架，如log4j、log4j2、logback等。并且还要引入相应的桥接jar包依赖。如果项目中有多种日志框架jar包依赖，可能会出现打印不了sql的情况，举例来说：

1. 项目中有了slf4j依赖，那么mybatis就使用slf4j来打印日志，但是slf4j需要依赖具体的日志框架实现和桥接jar包，如果缺少，则不能正常工作。如项目中有了slf4j依赖，有了log4j依赖，但是缺少slf4j-log4j12桥接jar包，即使log4.properties配置正确也是无法打印sql日志的，读者可以自行尝试。

2. slf4j在检测底层日志框架实现时，最优先使用的是logback，如果项目中同时存在了logback的依赖，和log4j的依赖，即使用户正确配置了log4j.properties文件，由于slf4j选择使用了logback来打印日志，因此也无法打印出sql，读者可以自行尝试。

>对于这种日志框架jar包依赖缺少或者冲突的情况，mybatis从3.2.0提供了一个终极解决方案，通过logImpl配置来强制指定使用哪个日志框架，使用方式如下所示：

```xml
<settings>
        <setting name="logImpl" value="LOG4J"/>
 </settings>
```

>logImpl的取值范围：SLF4J | LOG4J | LOG4J2 | JDK_LOGGING | COMMONS_LOGGING | STDOUT_LOGGING | NO_LOGGING

>其中，STDOUT_LOGGING表示打印日志到控制台。NO_LOGGING表示不打印日志。

>下面这张图列表出了slf4j在和各种底层日志框架如何整合，以及相应的需要引入的桥接jar包，这张图位于slf4j官网上：https://www.slf4j.org/manual.html

## mybatis打印SQL日志最佳实践

一般在生产环境中应用系统，日志级别调整为INFO或者WARN以避免过多的输出日志。但某些时候，需要跟踪具体问题，那么就得打开DEBUG日志。但是如果设置root logger为DEBUG级别，则需要的信息就会淹没在日志的海洋中。

此时，需要单独指定某个或者某些logger(如mybatis打印sql的logger)的日志级别为DEBUG，而root logger保持INFO或WARN不变。

细心的读者会发现，笔者在上面列出的log4j、log4j2、logback日志文件的配置时，都是将root logger配置为INFO级别，单独将mybatis打印sql的logger设置为了DEBUG级别。