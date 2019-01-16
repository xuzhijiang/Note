<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<!-- home.jsp is returned for “/home” URI and it should not require any authentication.
 -->
<body>
	<h1>Hello world!</h1>

	<P>The time on the server is ${serverTime}.</P>
</body>
</html>
