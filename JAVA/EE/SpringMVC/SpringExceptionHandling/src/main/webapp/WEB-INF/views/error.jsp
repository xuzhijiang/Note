<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error Page</title>
</head>
<body>
<!-- 
	当我们的“控制器类请求处理程序方法”(controller class request handler method)
	抛出EmployeeNotFoundException时，
	使用error.jsp。 当客户端请求中的id值为1时，我们应该将此页面作为响应。
-->
<h2>Application Error, please contact support.</h2>

<h3>Debug Information:</h3>

Requested URL= ${url}<br><br>

Exception= ${exception.message}<br><br>

<strong>Exception Stack Trace</strong><br>
<c:forEach items="${exception.stackTrace}" var="ste">
	${ste}
</c:forEach>

</body>
</html>