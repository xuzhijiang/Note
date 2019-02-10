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

