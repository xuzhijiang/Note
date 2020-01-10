# Spring Boot静态资源

    Spring Boot默认情况下,将放置在classpath下的以下文件夹中的文件视为静态资源文件(js css  images)，
    并将其映射为一个唯一的URL：

- classpath:/static
- classpath:/public
- classpath:/resources
- classpath:/META-INF/resources

![](../pics/Migrate-Application-Resources.png)

    一般选择放在static下

![](../pics/静态资源访问.png)

    我们可以在src/main/resources/目录下创建static，在该位置放置一个图片文件。
    启动程序后，尝试访问http://localhost:808/D.jpg。如能显示图片，配置成功。

![](../pics/springboot工程是没有webapp文件夹的.png)

# 新增静态资源路径

![](../pics/新增静态资源路径.png)

# 自定义静态资源映射

![](../pics/自定义静态资源映射.png)

![](../pics/自定义静态资源映射01.png)

![](../pics/自定义静态资源映射02.png)