# 传统的基于web.xml的项目中静态资源的配置

![](../pics/jsp页面不能访问静态资源解决方法01.png)

![](../pics/jsp页面不能访问静态资源解决方法02.png)

![](../pics/jsp页面不能访问静态资源解决方法03.png)

![](../pics/jsp页面不能访问静态资源解决方法04.png)

```xml
    <!-- resources标签定义了我们可以放置静态文件的位置 -->
    <!-- Handles HTTP GET requests for /resources/** by efficiently serving
        up static resources in the ${webappRoot}/resources directory -->
    <resources mapping="/resources/**" location="/resources/"/>
```           
