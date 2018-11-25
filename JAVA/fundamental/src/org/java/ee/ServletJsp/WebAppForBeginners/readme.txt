Content Type – text, html, image, pdf etc. Also known as MIME type
Content Type - 文本，html，图像，pdf等。也称为MIME type

Some of the mostly used mime types are text/html, 
text/xml, application/xml etc.

端口号0到1023是众所周知的服务的保留端口，例如80表示HTTP，443表示HTTPS，21表示FTP等。

JSP ia also server side technology 它就像HTML一样，具有在我们需要
的地方添加动态内容的附加功能。
JSP很适合于表示，因为它很容易编写，因为它就像HTML一样。


我们将在以后的文章中更详细地研究Servlet和JSP，但在结束本文之前，
我们应该很好地理解Java Web应用程序的一些方面:

Web Container

Tomcat是一个Web容器，当从Client向Web服务器发出请求时,
Web服务器将请求传递给Web容器，然后Web容器工作，找到处理请求的正确资源（servlet或JSP),
然后将这些资源JSP等提供给Web服务器，然后Web服务器将响应发送回客户端。

当Web容器接受到Web服务器传来的请求，发现处理请求的资源是Servlet时，
则容器创建两个对象HTTPServletRequest和HTTPServletResponse。 
然后，它根据URL找到正确的servlet，并为请求创建一个线程。然后它调用servlet service（）方法，
然后基于HTTP方法，service（）方法调用doGet（）或doPost（）方法。
 Servlet方法生成动态页面并将其写入响应。 servlet线程完成后，容器会将响应转换为HTTP响应并将其发送回客户端。

 
Some of the important work done by web container are:
Web容器完成的一些重要工作是：

Communication Support(通信支持) - Web容器提供Web服务器与servlet和JSP之间的简单通信方式。
由于容器，我们不需要构建服务器套接字来侦听(we don't need to build a server socket to listen for
any request from web server, parse the request and generate response.
)来自Web服务器的任何请求，解析请求并生成响应。
所有这些重要且复杂的任务都是由容器完成的，我们需要关注的是我们应用程序的业务逻辑。

Lifecycle and Resource Management (生命周期和资源管理) - Container负责管理(take care of managing the
life cycle of servlet.)servlet的生命周期。 
Container负责将servlet加载到内存中，初始化servlet，调用servlet方法并销毁它们。
 Container还提供JNDI等实用程序，用于资源池和管理。

Multithreading Support (多线程支持 ) - 容器为每个servlet请求创建新线程，当每个请求处理完成时，线程dies.
因此，servlet不会针对每个请求进行初始化，从而节省了时间和内存。

JSP支持 - JSP看起来不像普通的java类，Web容器提供对JSP的支持。应用程序中的每个JSP都由容器
编译并转换为Servlet，然后容器像其他servlet一样管理它们。
Every JSP in the application is compiled by container and converted to 
Servlet and then container manages them like other servlets.

其他任务Miscellaneous Task  - Web容器管理资源池，执行内存优化，运行垃圾收集器，提供安全配置，支持多个应用程序，
热部署以及场景背后的其他几项任务，使我们的生活更轻松。

Java Web Applications are packaged as Web Archive (WAR--Web ARchive) and it has a defined structure.
 You can export above dynamic web project as WAR file and unzip it to check the 
 hierarchy. It will be something like below image.
 
 					Web Archive Root Directory
 					
 	Static(optional)		META-INF				WEB-INF
 	Contains JSPs,HTMLs      MANIFEST.MF         
 	Images and static files	  
 										   classes				lib						web.xml
 										   Contains Servlet		Contains jars			Deployment descriptor
 										   
 
Deployment Descriptor
web.xml file is the deployment descriptor of the web application and contains mapping 
for servlets (prior to 3.0---3.0之前), welcome pages, security configurations, session timeout settings etc.										  
 										   Java Classes
