在这个Spring MVC教程中，我们将学习如何使用Spring Tool Suite开发Spring MVC Web应用程序。 

Spring MVC框架广泛用于java Web应用程序。

与Struts Framework一样，Spring MVC也基于Java EE Servlet和JSP技术，
并实现了Model-View-Controller设计模式(MVC)。

### STS(Spring Tool Suite)

我们可以使用Eclipse或IntelliJ IDE进行Spring项目开发，但SpringSource提供Spring Tool Suite(STS），
它是一个基于Eclipse的IDE，并带有内置的VMware vFabric tc Server，它构建在Apache Tomcat容器之上
并进行了优化对于基于Spring的应用程序。

我会将使用STS用于Spring MVC教程和其他未来的教程，因为它通过提供以下功能使开发人员的生活更轻松：

1. 支持创建骨架Spring应用程序(MVC，Rest，Batch等），适合从头开始创建项目。
我们很快就会在Spring MVC教程中看到创建Spring MVC项目是多么容易。

2. 提供有用的功能，例如创建Spring配置文件，解析配置文件和类以提供有关它们的有用信息。

3. Spring应用程序的自动验证

4. 重构支持以轻松进行项目更改，更改也会反映在配置文件中。

5. 代码辅助不仅适用于类，还适用于配置文件，我非常喜欢这个功能，
因为大多数时候我们需要知道我们可以使用什么以及它的细节。

6. 通过AspectJ的集成，为面向方面编程(AOP）提供最佳支持。

只需从STS官方下载页面下载STS并安装即可: “https://spring.io/tools3/sts/all”

如果您不想使用STS并希望在现有Eclipse中使用它，那么您需要从Eclipse Marketplace安装其插件。
(在eclipse的应用市场中输入STS，然后按照匹配eclipse version的STS)

如果您不想使用SpringSource服务器，可以将应用程序部署在任何其他Java EE容器中，
例如Tomcat，JBoss等。对于本教程，我将使用STS附带的服务器，
但我已通过导出测试了该应用程序它作为WAR文件进入单独的tomcat服务器，它工作正常。


-------------------------------------------------------


使用使用Spring Tool Suite或Eclipse的STS plugin中创建Spring MVC应用程序。
(以下步骤适用于standalone STS executable或带有STS插件的Eclipse):
(以下步骤都是在spring-tool-suite-3.9.7.RELEASE-e4.10.0独立的STS.exe上运行的)

第1步：File -> New -> Spring Legacy Project

Spring MVC，Spring MVC Project，Spring MVC示例

步骤2：在New Spring Legacy Project window中，
Project name:"SpringMVCExample"，并选择Templates:"Spring MVC Project"。

步骤3：下载模板后，在下一个屏幕中，您需要提供顶级包名称。该包将用作Spring组件的基础包。

如果您的项目未编译并且您看到一些错误，请在项目上右键 -> 运行Maven/Update Project。
请务必勾选上“Force update of Snapshots/Releases” options

Spring MVC，Maven Force更新项目

整个项目看起来就像任何其他基于maven的带有Spring配置文件的Web应用程序一样。

您可以看到使用STS插件创建Spring MVC应用程序是多么容易。 代码大小非常少，
大多数配置都由Spring MVC处理，因此我们可以专注于业务逻辑。