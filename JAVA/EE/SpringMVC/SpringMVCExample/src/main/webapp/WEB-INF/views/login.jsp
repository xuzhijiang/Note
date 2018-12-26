<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
</head>
<!-- 
	一个简单的JSP页面，供用户提供userName作为输入。
	 请注意，表单变量名称与Model User类变量名称相同。 
	 此外，表单操作是“home”，方法是“post”。 很明显HomeController的login（）方法会处理这个请求。
-->
<body>
	<form action="home" method="post">
		<input type="text" name="userName"><br>
		<input type="submit" value="Login">
	</form>
</body>
</html>