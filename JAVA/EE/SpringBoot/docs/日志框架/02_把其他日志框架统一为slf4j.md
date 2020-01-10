# 把所有日志框架统一为slf4j+logback?

    比如,开发a系统的时候我使用slf4j+logback的方式,但是开发a系统的时候,我要依赖spring,hibernate,mybatis等框架
    ,但是每一个框架底层也用到了日志框架,而且各不相同
    比如spring底层就是使用JCL(Jakarta Commons Logging)来做日志记录的,
    hibernate使用jboss-logging来记录日志的,所以我们想统一日志的打印为slf4j,
    想让spring,hibernate等框架也是用slf4j+logback来打印日志,
    这样我们就可以只写logback的配置文件了,不用写其他框架的日志配置文件了,这样怎么来做了?

    日志框架不统一的问题: a（slf4j+logback）: Spring（commons-logging）、Hibernate（jboss-logging）

>如何让系统中所有的日志框架都统一成slf4j+logback?

    1、将系统中其他日志框架的实现类先都排除出去
    2、用中间包来替换原有的日志框架的实现；
    3、导入slf4j的实现(这里是导入logback的jar,logback-classic和logback-core)

![](../pics/如何把其他框架中使用的日志框架转成slf4j.png)

    统一之后,其他日志框架打印用的底层具体实现就是logback
