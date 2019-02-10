@RequestMapping注释用于将请求URI映射到处理程序方法。
我们还可以指定客户端应用程序应该使用的HTTP方法来调用rest方法(to invoke the rest method)。

@ResponseBody注释用于映射response对象中的response体。
一旦handler method返回响应对象，MappingJackson2HttpMessageConverter
就会启动(kicks in)并将其转换为JSON响应。

@RequestBody注释用于将"请求主体JSON数据(request body JSON data)"映射到Employee对象，
这也是由MappingJackson2HttpMessageConverter映射完成的。
again this is done by the MappingJackson2HttpMessageConverter mapping.

@PathVariable注释是从rest URI中提取数据并将其映射到method argument的简单方法。

Spring是使用最广泛的Java EE框架之一。 我们之前已经看到了如何使用Spring MVC来创建基于Java的Web应用程序。 
今天将学习使用Spring MVC创建Spring Restful Web Services，然后使用Rest client进行测试。
最后，我们还将研究如何使用Spring RestTemplate API调用Spring Restful Web服务。

我们将使用Spring最新版本4.0.0.RELEASE并利用Spring Jackson JSON集成在其余的调用响应中发送JSON响应。 

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

 //我正在使用WizTools RestClient来调用the rest calls，但您也可以使用Chrome的扩展Postman。