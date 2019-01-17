<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<!--
	为“/home”URI返回home.jsp，不应该要求任何身份验证。
 -->
<body>
	<h1>Hello world!</h1>

	<P>The time on the server is ${serverTime}.</P>
</body>
</html>
