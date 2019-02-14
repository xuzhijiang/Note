## Spring Boot中使用JSP

### 概述

默认情况下， Spring Boot项目中如果引入了spring-boot-starter-web依赖，则会启动一个嵌入式的tomcat，并且自动配置好DispatcherServlet，从而你就直接可以编写控制器了。

这里要注意的，是默认使用视图引擎并不是JSP，而是Thymeleaf，这么做的原因，在于在使用嵌入式的tomcat作为Server时， JSP有相应的限制

新的Spring Boot项目，不建议再使用JSP。

依据Tomcat对Java Web项目文件夹结构的约定，我们需要手工在IntelliJ中创建
webapp/WEB-INF文件夹，通常情况下，多数项目都会在此文件夹下创建相应的子文件
夹，保存JSP网页模板。

本讲P介绍了如何在Spring Boot项目中使用JSP作为视图的方法。

推荐在新的Spring Boot项目中使用Thymeleaf而不是JSP。

Spring Boot项目中还可以使用Listener和Filter等经典的Java Web组件，只不过是多半使用代码而不是像早期的Spring项目那样使用XML。有关在Spring Boot中如何注册Listener等内容，请自行网上学习.