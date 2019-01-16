我们将讨论Spring框架“安全性”模块基础知识

现在，开发一个安全应用程序对于避免故障，窃取或黑客攻击我们的机密数据非常重要的方面。
我们可以使用Spring Security Module开发安全应用程序来限制对我们的应用程序的访问。

### Spring 4 Security

最初，Spring Framework使用单独的第三方框架来支持Spring Applications安全性：Acegi Security。但是第三方的框架开发安全应用程序并不容易，并且有一些缺点:

#### Spring Acegi Security的缺点

* 很多XML配置
* 学习曲线太多了
* 不支持注释

为了避免所有这些问题，Spring Team（Pivotal Team）将“Acegi Security”框架集成到Spring Framework中作为“Spring Security”模块。

Spring 4 Framework具有以下模块，可为基于Spring的应用程序提供安全性：

1. Spring 4 Security
2. Spring Security SAML
3. Spring Security OAuth
4. Spring Security Kerberos
5. Spring Cloud Security

在Spring Framework中，“Spring Security”模块是其他Spring Security模块的基础模块。

我们将在本文中讨论“Spring Security”模块的一些基础知识(some basics)

### 什么是Spring Security？

Spring Security是Spring Framework’s Security模块之一。它是一个Java SE/Java EE安全框架，为Web应用程序或企业应用程序提供身份验证，授权，SSO和其他安全功能。

Spring Security官方网站：http：//projects.spring.io/spring-security/

Spring Security Documentation网站：http：//docs.spring.io/spring-security/site/docs/

最新稳定的Spring Security Module版本是“4.0.2.RELEASE”

#### Spring 4安全Features功能

Spring 3.x Security Framework提供以下功能：

1. 身份验证和授权(Authentication and Authorization.)
2. 支持BASIC，摘要和基于表单的身份验证(Supports BASIC,Digest and Form-Based Authentication.)
3. 支持LDAP身份验证。
4. 支持OpenID身份验证。
5. 支持SSO（(Single Sign-On)单点登录）Implementation。
6. 支持跨站请求伪造（CSRF-Cross-Site Request Forgery）Implementation。
7. 通过HTTP Cookie支持“记住我”功能。
8. 支持ACL的实现
9. 支持“Channel Security通道安全”，这意味着在HTTP和HTTPS之间自动切换。
10. 持I18N（国际化）。
11. 支持JAAS（Java Authentication and Authorization Service-Java身份验证和授权服务）。
12. 使用Spring WebFlow Framework支持流程授权(Flow Authorization)。
13. 使用Spring Web Services支持WS-Security。
14. 支持XML配置和注释。非常少或最小的XML配置。

Spring 4.x安全框架支持以下新功能：

1. 支持WebSocket安全性。
2. 支持Spring数据集成。
3. CSRF令牌参数解析器(CSRF Token Argument Resolver.)

### Spring 4安全级别

Spring Security支持以下两个授权级别:

1. 方法级别授权(Method Level Authorization)
2. URL级别授权(URL Level Authorization)

注意: Spring Security通过使用AOP（面向方面​​编程）来支持““Method Level Security- 方法级安全性”，这意味着通过Aspects。 Spring Security通过使用Servlet过滤器支持“URL Level Security- URL级别安全性”。

### Spring 4安全优势

Spring 4安全框架提供以下优点：

1. 开源安全框架
2. 灵活，易于开发和单元测试应用程序
3. 声明性安全编程
4. 易于扩展
6. 易于维护
6. 充分利用Spring DI（依赖注入）和AOP。
7. 我们可以开发松散耦合的应用程序。

### Spring 4安全子模块

Spring 4安全模块又分为11个子模块。它有以下子模块：

Spring Security Core Module
Spring Security Configuration Module
Spring Security Web Module
Spring Security Tag Library Module
Spring Security AspectJ Module
Spring Security ACL Module
Spring Security LDAP Module
Spring Security OpenID Module
Spring Security CAS Module
Spring Security Cryptography Module
Spring Security Remoting Module

在Spring Framework的安全子模块中， Spring Security Core Sub-Module是所有安全子模块的基础模块。

为了支持这11个Spring Security模块，Spring框架有以下jar：

spring-security-core-4.0.2.RELEASE.jar
spring-security-config-4.0.2.RELEASE.jar
spring-security-web-4.0.2.RELEASE.jar
spring-security-taglibs-4.0.2.RELEASE.jar
spring-security-aspects-4.0.2.RELEASE.jar
spring-security-acl-4.0.2.RELEASE.jar
spring-security-ldap-4.0.2.RELEASE.jar
spring-security-openid-4.0.2.RELEASE.jar
spring-security-cas-4.0.2.RELEASE.jar
spring-security-crypto-4.0.2.RELEASE.jar
spring-security-remoting-4.0.2.RELEASE.jar

几乎所有Spring Security JAR都有类似的Maven或Gradle依赖项，如下所示：

Spring Security Maven

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>[Spring Security Module Name Here]</artifactId>
        <version>4.0.2.RELEASE</version>
    </dependency>
</dependencies>
```

Spring Security Gradle

```json
dependencies {
compile 'org.springframework.security:[Spring Security Module Name Here]:4.0.2.RELEASE'
}
```

pom.xml

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-core</artifactId>
        <version>4.0.2.RELEASE</version>
    </dependency>
</dependencies>
```

build.gradle

```json
dependencies {
    compile 'org.springframework.security:spring-security-core:4.0.2.RELEASE'
}
```

我们将使用Spring STS Suite IDE，Maven或Gradle Build Tool和Java 7/8来开发我们的应用程序。
