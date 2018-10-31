算法: https://github.com/michaelliao/algorithms
springcloud: https://github.com/michaelliao/springcloud

Spring、SpringMVC、MyBatis、Spring Boot:https://github.com/lenve/JavaEETest
Sprint blog: https://github.com/Raysmond/SpringBlog

DAO: Data access object

SSH: Struts2 + Spring + Hibernate

SSM: SprintMVC + Spring + Mybatis

POM: Project Object Model, the fundamental unit of work in Maven.

SprintBoot必学

要选Spring Cloud全家桶必学

GRADLE

## MAVEN

Maven is written in Java and primarily used to build Java programs.

`find jar: local repo -->  private service repo(Enterprise) --> central repo`

![maven central repo all plugins](https://repo.maven.apache.org/maven2/org/apache/maven/plugins/)

![find pom.xml dependency jar format](http://mvnrepository.com/)

### 分析maven-eclipse-plugin:

```shell
mvn help:describe -Dplugin=eclipse

maven-eclipse-plugin:
Artifact Id: maven-eclipse-plugin
Version: 2.10
Goal Prefix:eclipse

This plugin has 12 goals:

eclipse:clean

eclipse:configure-workspace

mvn eclipse:eclipse
```