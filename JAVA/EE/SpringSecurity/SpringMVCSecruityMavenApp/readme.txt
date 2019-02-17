我们将学习Spring Security Login Example

在这篇文章中，我们将开发Spring 4 MVC Security Web Application，通过使用In-Memory选项提供登录和注销功能。

此示例使用带有Spring Annotations的Spring Java Config，这意味着不使用web.xml和Spring XML Configuration(旧样式）。

Spring 4安全模块支持以下选项来存储和管理用户凭据：

1. In-Memory Store
2. Relations Databases(RDBMS)
3. No SQL Data Stores
4. LDAP

我们将在此示例中使用“In-Memory Store”选项.

我们将使用Spring 4.0.2.RELEASE，Spring STS 3.7 Suite IDE，带有Java 1.8的Spring TC Server 3.1和Maven构建工具来开发此示例。

### Spring安全登录示例

我们将使用Spring 4Security Features开发登录和注销逻辑。 

该应用程序的主要目的是在不使用“web.xml”的情况下开发应用程序，而无需编写单行Spring XML Bean配置。 这意味着我们将使用带有Spring Annotations的Spring Java Config功能。

我们将使用以下功能开发此应用程序：

1. Welcome Page
2. Login Page
3. Home Page3
4. Logout Feature

注意：- 如果您不了解“<failOnMissingWebXml>”标志，请阅读本文末尾的内容，以便更好地了解此元素的用法。

### 运行Spring Security MVC登录注销示例

要运行这个Spring Web应用程序，我们需要任何支持Spring 4和带有Servlet 3.1.0 Container的Java 8环境的Web容器。

1. 在Spring STS Suite中部署并运行Spring TC Server
2. 它会自动访问我们的应用程序欢迎页面URL，http：localhost:8888/SpringMVCSecurityMavenProject/

注意：-
如果我们观察这个例子， we are not using web.xml file right.由于它是一个Web应用程序，Maven搜索web.xml文件,如果在应用程序中没有找不到，则会引发一些错误。 这是为了避免Maven相关问题，我们需要在pom.xml文件中配置“<failOnMissingWebXml>”标志。

这就是Spring 4 Security Module Simple Example的全部内容。 我们将在即将发布的帖子中开发更多实时有用的示例，例如Managing Roles，Rememeber-Me Feature，WebSocket Security等等......