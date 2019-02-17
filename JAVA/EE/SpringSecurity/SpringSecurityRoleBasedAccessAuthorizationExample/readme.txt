我们将研究基于访问和授权的Spring安全角色的示例

但是在阅读这篇文章之前，请先阅读之前关于“Spring 4 Security MVC Login Logout Example”的文章，以获得有关Spring 4 Security的一些基本知识。

### Spring Security Role

我们将讨论如何Spring Web Application中在定义，使用和管理的“USER”，“ADMIN”等spring security roles

这个例子也是使用Spring 4 MVC Security with In-Memory Store和Spring Java Configuration Feature来开发应用程序。 这意味着我们不会使用web.xml文件，也不会编写一行Spring XML Configuration。

我们将使用“In-Memory Store”选项来存储和管理User Credentials.(用户凭据）

我们将使用Spring 4.0.2.RELEASE，Spring STS 3.7 Suite IDE，带有Java 1.8的Spring TC Server 3.1和Maven构建工具来开发此示例

### Spring安全角色示例应用程序测试

右键单击Spring STS IDE中的Project并选择“Run AS >> Run on Server”选项。
它将访问默认的Application欢迎页面

Click on “Login to JournalDEV” link.Now you are at Login Page.

First login with “USER” Role Credentials:
Username: jduser
Password: jdu@123

Now we will see Application HomePage with 3 
Menu Options: “JD User”, “JD Admin” and “Logout”.

Click on “JD User” link. As we have logged into application using 
“USER” Role Credentials, We can access this link as shown below.

Just use backword arrow in Spring STS IDE and this time click on “JD Admin” Link.

As we have logged in with “USER” Role Credentials, 
We cannot access this link. That’s why we saw this error message: 
“403 Access is denied”.

Now Logged and again login with ADMIN Role Credentials
Username: jdadmin
Password: jda@123

This time we can access “JD Admin” Link successfully as shown below.

Test “Logout” link to Logged out of the Application.
