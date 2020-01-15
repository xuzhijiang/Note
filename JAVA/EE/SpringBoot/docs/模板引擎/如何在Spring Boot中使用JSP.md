# 如何在Spring Boot中使用JSP

Spring Boot己经不再推荐使用JSP，若一定要使用JSP将无法实现Spring Boot的多种特性

    Spring Boot默认使用嵌入式的Tomcat，嵌入式的Tomcat默认不支持JSP

# spring boot jsp项目中jsp模板文件应该放在哪里?

[参见](https://www.logicbig.com/tutorials/spring-framework/spring-boot/boot-serve-dynamic.html)
 
    如果打成war,JSP存放在: src/main/webapp/下
    
    如果打成jar,不要再把模板文件放在src/main/webapp下了,尽管这个目录在传统的war中是标准的.
    但是打成jar的时候,大多数构建工具会忽略这个目录,会导致应用无法找到模板.