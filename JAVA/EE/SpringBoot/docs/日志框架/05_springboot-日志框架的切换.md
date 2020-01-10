# springboot切换日志框架

    可以按照slf4j的日志适配图，进行相关的切换；

![](../pics/legacy.png)

# springboot切换为slf4j+log4j

    虽然切换为slf4j+log4j没有啥意义,因为出现logback就是因为log4j写的不好,才出现logback的
    .这里只是为了演示,实际不再将logback切换为log4j

![](../pics/如何把其他框架中使用的日志框架转成slf4j02.png)

    切换为slf4j+log4j意味着所有日志框架底层都是使用log4j来进行打印.

```xml
<project>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
      <exclusions>
    <!--因为要统一为log4j打印,所以排除slf4j的实现logback-->
        <exclusion>
          <artifactId>logback-classic</artifactId>
          <groupId>ch.qos.logback</groupId>
        </exclusion>
    <!--这个jar是为了将log4j统一成slf4j+logback使用的中间适配jar包-->
    <!--我们现在就是要统一成log4j来打印日志,这个jar与我们的目的背道而驰,所以要排除了-->
        <exclusion>
          <artifactId>log4j-over-slf4j</artifactId>
          <groupId>org.slf4j</groupId>
        </exclusion>
      </exclusions>
    </dependency>
    <!--导入slf4j的实现jar: log4j-->
    <!--这个jar内部导入了slf4j-api.jar(slf4j日志门面接口jar),log4j.jar-->
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-log4j12</artifactId>
    </dependency>
</project>
```

# springboot切换为slf4j+log4j2

![](../pics/logback切换为log4j2.png)

```xml
<project>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <exclusions>
            <exclusion>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-logging</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-log4j2</artifactId>
    </dependency>
</project>
```

![](../pics/logback切换为log4j2---02.png)

    现在主流选择: spring-boot-starter-logging和spring-boot-starter-log4j2 二选一
