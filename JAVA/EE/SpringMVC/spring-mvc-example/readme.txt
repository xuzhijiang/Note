
  Request
===========>											Delegate Request
					Front Controller					=================>  Controller(Home Controller)
					(DispatcherServlet)					Delegate to Model			Handle Request
  Response				^								<=================
<===========			|		|
		passing control	|		|Render Response
								^
						View Pages(home.jsp, login.jsp)




DispatcherServlet是前端控制器类，用于接收所有请求并开始处理它们。 我们必须在web.xml文件中配置它。 它的工作是将请求传递给适当的控制器类，并在视图页面呈现响应页面时发回响应。

HomeController.java将是我们的spring mvc示例应用程序中的单个控制器类。

home.jsp，user.jsp是我们的spring mvc hello world示例应用程序中的视图页面。


User.java将是我们在spring mvc示例Web应用程序中唯一的模型类。					