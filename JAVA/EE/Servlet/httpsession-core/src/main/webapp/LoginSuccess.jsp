<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>登录成功页面</title>
</head>
<body>
	<%
		String user = null;
		if (session.getAttribute("session-name-user") == null) {
			response.sendRedirect("login.html");
		} else {
			user = (String) session.getAttribute("session-name-user");
		}
		String userName = null;
		String sessionID = null;

		Cookie[] cookies = request.getCookies();
		if(cookies !=null){
			for(Cookie cookie : cookies){
				if (cookie.getName().equals("cookie-user")) {
					userName = cookie.getValue();
				}
				if (cookie.getName().equals("JSESSIONID")) {
					sessionID = cookie.getValue();
				}
			}
		}
	%>
	<h3>Hi <%=userName %>, Login successful. Your Session ID=<%=sessionID %></h3>
	<br> User=<%=user %><br>

	<a href="/session?param=getSession">获取session</a><br/>
	<a href="/session?param=invalidateSession">销毁session</a><br/>

	<form action="logout" method="post">
		<input type="submit" value="注销" >
	</form>
</body>
</html>