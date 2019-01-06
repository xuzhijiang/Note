使用JSON，Jackson和Client Program的Spring Restful Web服务示例

Spring是使用最广泛的Java EE框架之一。 我们之前已经看到了如何使用Spring MVC来创建基于Java的Web应用程序。 
今天我们将学习使用Spring MVC创建Spring Restful Web Services，然后使用Rest client进行测试。
最后，我们还将研究如何使用Spring RestTemplate API调用Spring Restful Web服务。

我们将使用Spring最新版本4.0.0.RELEASE并利用Spring Jackson JSON集成在其余的调用响应中发送JSON响应。 
本教程是在Spring STS IDE中开发的，用于轻松创建Spring MVC框架代码，然后扩展为实现Restful架构。

UPDATE更新：由于有很多请求提供类似的XML示例 以及支持XML和JSON的示例，
我已经在Spring REST XML JSON示例中扩展了这个应用程序，以支持xml和JSON请求和响应。 
我强烈建议你仔细阅读一下，看看Spring框架的美感以及实现这一目标的难易程度

-------------------------------------------------------

欢迎使用Spring Restful Web Services XML和JSON示例:

 有一段时间我写了一篇关于Spring REST JSON的文章，我收到很多评论，询问如何更改程序以支持XML。 
 我收到了一些电子邮件，询问如何使应用程序同时支持XML和JSON。

我想写一篇关于Spring REST XML和JSON应用程序的文章，
我将向您展示如何轻松扩展现有应用程序以支持XML。 由于我将对现有项目进行更改，请确保首先从下面的链接下载它.

-------------------------------------------------------

1. XML Response: Make sure you pass Accept header as “application/xml”.
GET: http://localhost:8080/SpringRestExample/rest/emp/dummy
Accept: application/xml

2. JSON Response: Make sure you pass Accept header as “application/json”.
GET: http://localhost:8080/SpringRestExample/rest/emp/dummy
Accept: application/json

3. XML Request with JSON Response: Make sure Accept header is “application/json” and Content-Type header is “text/xml” as shown in below images.
POST:http://localhost:8080/SpringRestExample/rest/emp/create


这就是支持XML和JSON的Spring Restful Web服务示例。
 您可以看到扩展Spring框架是多么容易，这是Spring框架流行的主要原因之一。