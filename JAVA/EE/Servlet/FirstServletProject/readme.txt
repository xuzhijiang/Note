Java Web Application用于创建动态网站。 Java通过Servlets和JSP提供对Web应用程序的支持。 
我们可以使用静态HTML页面创建一个网站，但是当我们希望信息是动态的时，我们需要Web应用程序。

### HTTP协议

	HTTP在TCP/IP通信协议之上运行。
	
	Content Type – text, html, image, pdf etc. Also known as MIME type(也称为MIME type)
	
	MIME类型或内容类型(MIME Type or Content Type)：如果你观察HTTP MIME Type，
	它包含“Content-Type”。 它也被称为MIME类型，服务器将其发送给客户端，让他们知道它发送的数据类型。 
	它帮助客户端为用户呈现数据。 一些最常用的mime类型是text/html, 
	text/xml, application/xml etc.
	
	如果我们不在URL中提供port，则请求转到协议的默认端口, 端口号0到1023是众所周知的服务的保留端口，
	例如80表示HTTP，443表示HTTPS，21表示FTP等。

### Web Container

	由于servlet是服务器端技术，我们需要一个支持Servlet技术的Web容器.
	
	为了便于开发，我们可以使用Eclipse添加配置Tomcat，它有助于轻松部署和运行应用程序。
	
	转到Eclipse -》 Preference -》 Server Runtime Environments， 并选择tomcat服务器的版本，我的是Tomcat 8。
	
	提供apache tomcat目录位置和JRE信息以添加运行时环境
	
	现在转到Servers view，并创建一个新服务器，指向上面添加的运行时环境。
	
	注意：如果看不到Servers选项卡，则可以选择Window> Show View> Servers，以便它在Eclipse窗口中可见.
	
	如果你已经从终端启动了服务器，那么你必须从终端停止，然后从Eclipse启动它，否则它将无法正常工作

#### 从头创建一个web application

	选择File> New> Dynamic Web Project
	
	module version as 3.0,也就是使用Servlet 3.0规范创建我们的servlet。
	
	选择File> New> Servlet创建我们的第一个servlet。
	
	当单击Finish时，它会生成我们的Servlet框架代码，节省了我们的时间。

Tomcat是一个Web容器，当从Client向Web服务器发出请求时,
Web服务器将请求传递给Web容器，然后Web容器工作，找到处理请求的正确资源（servlet或JSP),
然后将这些资源JSP等提供给Web服务器，然后Web服务器将响应发送回客户端。

request from client ---> Web Server ------> Web Container ----> find resource ---> Web Server ----> client

当Web容器接受到Web服务器传来的请求，发现处理请求的资源是Servlet时，
然后Web容器创建两个对象HTTPServletRequest和HTTPServletResponse。 
然后，它根据URL找到正确的servlet，并为请求创建一个线程。然后它调用servlet service（）方法，
然后基于HTTP方法，service（）方法调用doGet（）或doPost（）方法。
Servlet方法生成动态页面并将其写入response。 servlet线程完成后，
容器会将response转换为HTTP响应并将其发送回客户端。

### Web容器完成的一些重要工作是：

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
	编译并转换为Servlet，然后容器像其他管理servlet一样管理它们。
	Every JSP in the application is compiled by container and converted to 
	Servlet and then container manages them like other servlets.

	其他任务Miscellaneous Task  - Web容器管理资源池，执行内存优化，运行垃圾收集器，提供安全配置，支持多个应用程序，
	热部署以及场景背后的其他几项任务，使我们的生活更轻松。

Java Web Applications are packaged as Web Archive (WAR--Web ARchive) 
and it has a defined structure.You can export above dynamic web project 
as WAR file and unzip it to check the hierarchy. 
It will be something like below image.
 
 					Web Archive Root Directory
 					
 	Static(optional)		META-INF				WEB-INF
 	Contains JSPs,HTMLs      MANIFEST.MF         
 	Images and static files	  
 										   classes				lib						web.xml
 										   Contains Servlet		Contains jars			Deployment descriptor
 										   


### Deployment Descriptor

web.xml file is the deployment descriptor of the web application 
and contains mapping for servlets (prior to 3.0---3.0之前), 
welcome pages, security configurations, session timeout settings etc.										  

### Servlet

如果你深刻理解了Servlet的生命周期，就可以在底层做很多事情。在Request进来的时候，
进行拦截，进行权限的判定。也可以在Response发给client的时候，进行拦截，统一检查、统一附加。
所以Servlet不仅要学，而且要学深，学透。”