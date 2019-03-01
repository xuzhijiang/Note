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
}else{
	sessionID = session.getId();
	log("--------LoginSuccess sessionID: " + sessionID);
}
%>
<!--  另请注意，在LoginSuccess页面上，用户名为null，
也就是以下的userName为null,因为浏览器未在最后一个响应中发送cookie发送。 -->

<!-- 当我们运行此项目时，在浏览器中禁用cookie，下面的图像显示响应页面，
注意浏览器地址栏的URL中的jsessionid。 -->

<!-- 如果未禁用cookie，则不会在URL中看到jsessionid，因为在这种情况下Servlet会话API将使用cookie来存储session id。 -->
<h3>Hi <%=userName %>, Login successful. Your Session ID=<%=sessionID %></h3>
<br>
User=<%=user %>
<br>
<!-- need to encode all the URLs where we want session information to be passed -->
<a href="<%=response.encodeURL("CheckoutPage.jsp") %>">Checkout Page</a>
<form action="<%=response.encodeURL("LogoutServlet") %>" method="post">
<input type="submit" value="Logout" >
</form>
</body>
</html>