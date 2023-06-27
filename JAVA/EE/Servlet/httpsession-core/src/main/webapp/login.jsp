<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<%--MyHttpSessionListener的测试--%>
<%-- jsp会用到内置的session,所以如果一个client没有和server建立会话的时候访问jsp,就会先创建session. --%>
<body>
    <form action="login" method="post">
        Username: <input type="text" name="user"><br>
        Password: <input type="password" name="pwd"><br>
        <input type="submit" value="登录">
    </form>
</body>
</html>