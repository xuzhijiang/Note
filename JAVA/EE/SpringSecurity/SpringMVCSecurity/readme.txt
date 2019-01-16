Spring安全示例 UserDetailsService

今天我们将研究如何在Spring MVC项目中集成Spring Security以进行身份验证。

### Spring Security Example

将Spring Security与Spring MVC Framework集成非常简单，因为我们已经有了Spring Beans配置文件。 我们所需要的只是创建与spring安全认证相关的更改以使其正常工作。 今天我们将研究如何使用in-memory，UserDetailsService DAO实现和基于JDBC的身份验证在Spring MVC application中实现身份认证

首先在Spring Tool Suite中创建一个简单的Spring MVC项目，它将为我们提供基础Spring MVC应用程序来构建我们的Spring安全性示例应用程序。 
