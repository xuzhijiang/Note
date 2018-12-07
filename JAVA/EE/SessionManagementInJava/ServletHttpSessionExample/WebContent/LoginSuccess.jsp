<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Login Success Page</title>
</head>
<body>
<%
// 当使用JSP资源时，容器会自动为其创建会话，
// 因此我们无法检查会话是否为null以确保用户是否已通过登录页面，因此我们使用会话属性来验证请求。
//allow access only if session exists
String user = null;
if (session.getAttribute("user") == null) {
	response.sendRedirect("login.html");
} else {
	user = (String) session.getAttribute("user");
}
String userName = null;
String sessionID = null;

Cookie[] cookies = request.getCookies();
if(cookies !=null){
	for(Cookie cookie : cookies){
		if(cookie.getName().equals("user")) userName = cookie.getValue();
		if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
	}
}
// 请注意，我在日志中打印JSESSIONID cookie值，您可以检查服务器日志，
// 它将在LoginSuccess.jsp中打印与Session Id相同的值
%>
<h3>Hi <%=userName %>, Login successful. Your Session ID=<%=sessionID %></h3>
<br>
User=<%=user %>
<br>
<a href="CheckoutPage.jsp">Checkout Page</a>
<form action="LogoutServlet" method="post">
<input type="submit" value="Logout" >
</form>
</body>
</html>