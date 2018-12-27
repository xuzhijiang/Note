<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>Hello world!</h1>

	<P>The time on the server is ${serverTime}.</p>
	<!-- 
		Notice that Spring MVC takes care of mapping form variables to model class variables, that’s why we have same variable name in both places.

		请注意，Spring MVC负责将表单变量(userName)映射到模型类变量(model class User)，这就是为什么我们在两个地方都有相同的变量名称。
	-->
	<form action="user" method="post">
		<input type="text" name="userName"><br> <input
			type="submit" value="Login">
	</form>
</body>
</html>