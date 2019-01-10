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

### 从头创建一个web application

	由于servlet是服务器端技术，我们需要一个支持Servlet技术的Web容器.
	
	为了便于开发，我们可以使用Eclipse添加配置Tomcat，它有助于轻松部署和运行应用程序。
	
	转到Eclipse -》 Preference -》 Server Runtime Environments， 并选择tomcat服务器的版本，我的是Tomcat 8。
	
	提供apache tomcat目录位置和JRE信息以添加运行时环境
	
	现在转到Servers view，并创建一个新服务器，指向上面添加的运行时环境。
	
	注意：如果看不到Servers选项卡，则可以选择Window> Show View> Servers，以便它在Eclipse窗口中可见.
	
	如果你已经从终端启动了服务器，那么你必须从终端停止，然后从Eclipse启动它，否则它将无法正常工作

	选择File> New> Dynamic Web Project
	
	module version as 3.0,也就是使用Servlet 3.0规范创建我们的servlet。
	
	选择File> New> Servlet创建我们的第一个servlet。
	
	当单击Finish时，它会生成我们的Servlet框架代码，节省了我们的时间。

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
