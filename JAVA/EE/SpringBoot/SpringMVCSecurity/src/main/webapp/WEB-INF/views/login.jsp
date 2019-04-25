<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>

<head>
<title>Login Page</title>
</head>
<!--  
	这里有几点需要注意。 第一个是登录URL是“/ j_spring_security_check”。 这是默认的登录处理URL，就像logout-url一样。

	另一个重点是用户名和密码的表单参数名称。 它们应与spring security configurations中配置的相同。
-->
<body>
	<h3>Login with Username and Password</h3>
	<c:url var="loginUrl" value="/j_spring_security_check"></c:url>
	<form action="${loginUrl}" method="POST">
		<table>
			<tr>
				<td>User ID:</td>
				<td><input type='text' name='username' /></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='password' /></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
					value="Login" /></td>
			</tr>
		</table>
	</form>
</body>
</html>