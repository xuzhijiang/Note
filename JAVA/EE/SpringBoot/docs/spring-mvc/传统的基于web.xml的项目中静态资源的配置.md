# 传统的基于web.xml的项目中静态资源的配置

![](../pics/jsp页面不能访问静态资源解决方法01.png)

![](../pics/jsp页面不能访问静态资源解决方法02.png)

![](../pics/jsp页面不能访问静态资源解决方法03.png)

![](../pics/jsp页面不能访问静态资源解决方法04.png)

```xml
<!--resources标签定义了: 如果浏览器请求静态资源, 到哪个路径下找 -->
<mvc:resources mapping="/resources/**" location="/static/"/>
<!-- 凡是url以/resources/**开头的静态资源GET请求,都到${webappRoot}/static路径下去找相应的静态资源 -->
<!-- http://localhost:8080/resources/css/web.css   -->
```           
