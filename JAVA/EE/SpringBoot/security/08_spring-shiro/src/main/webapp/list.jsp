<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta content="text/html" http-equiv="Content-Type" charset="UTF-8">
    <title>list page</title>
</head>
<body>
    <h4>list</h4>

    <!-- 因为我们构建new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);的时候,
    principal传的是username,所以这里可以拿到-->
    欢迎: <shiro:principal></shiro:principal>

    <!--有admin权限显示这个-->
    <shiro:hasRole name="admin">
        <br/><a href="admin.jsp">Admin Page</a>
    </shiro:hasRole>

    <!--有user权限显示这个-->
    <shiro:hasRole name="user">
        <br/><a href="user.jsp">User Page</a>
    </shiro:hasRole>

    <br/>
    <a href="shiro/testShiroAnnotation">Test ShiroAnnotation</a>

    <br/>
    <a href="shiro/logout">logout</a>
</body>
</html>