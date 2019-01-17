<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Get Employee Page</title>
</head>
<!--  
	当我们访问需要身份验证的URI时，将返回此页面。 

	这里我提供了注销选项，以便用户可以注销并终止会话。 

	注销成功后，应按照配置发送登录页面给用户。
-->
<body>
	<h1>Employee Information</h1>
	<p>
		Employee ID:${id}<br> Employee Name:${name}<br>
	</p>
	<c:if test="${pageContext.request.userPrincipal.name != null}">
	Hi ${pageContext.request.userPrincipal.name}<br>
	
	<c:url var="logoutAction" value="/j_spring_security_logout"></c:url>
	
	<form action="${logoutAction}" method="post">
		<input type="submit" value="Logout" />
	</form>
	</c:if>
</body>
</html>
