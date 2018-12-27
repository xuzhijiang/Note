
  Request
===========>											Delegate Request
					Front Controller					=================>  Controller(Home Controller)
					(DispatcherServlet)					Delegate to Model			Handle Request
  Response				^								<=================
<===========			|		|
		passing control	|		|Render Response
								^
						View Pages(home.jsp, login.jsp)

1. DispatcherServlet是前端控制器类(front controller class)，用于接收所有请求并开始处理它们。 我们必须在web.xml文件中配置它。它的工作是将请求传递给适当的控制器类，并在视图页面呈现响应页面时发回响应。
send the response back when view pages have rendered the response page.

2. HomeController.java将是我们的spring mvc示例应用程序中的单个控制器类。
3. home.jsp，user.jsp是我们的spring mvc hello world示例应用程序中的视图页面。
4. User.java将是我们在spring mvc示例Web应用程序中唯一的模型类。	


如何创建一个Web应用程序，然后使用maven进行依赖项管理

1. 首先我们必须创建一个动态Web应用程序(create a dynamic web application):
File -> New -> Dynamic Web Project

2. Provide Project name: “spring-mvc-example” in the next popup page

3. On next page, provide the source folder as “src/main/java”. You might have to remove “src” folder from the list before adding this.在添加(Add Folder)之前，您可能必须从列表中删除“src”文件夹。

4. Next is the web module page, Context root: spring-mvc-example,
要勾选"Generate web.xml deployment descriptor"选项.

5. 单击Finish，您将在eclipse项目资源管理器中拥有一个新的Dynamic Web Project。

Converting Dynamic Web Project to Maven Project

我们想使用maven来轻松管理我们的spring mvc依赖项。 那么让我们将我们的web项目转换为maven。

6. 右键单击该项目，然后选择“Configure -> Convert to Maven Project”。

7. Next provide the pom.xml configurations.


-------------------------------------------------------------

1. 我们可以使用Eclipse导出为WAR文件选项将其直接部署到任何正在运行的tomcat服务器webapps目录。 export -> WAR file

2. 但是，您也可以使用命令行来构建项目，然后将其复制到你喜欢的servlet容器部署目录中。

