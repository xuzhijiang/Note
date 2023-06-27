<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta content="text/html" http-equiv="Content-Type" charset="UTF-8">
    <title>登录页面</title>
</head>
<body>
    <h4>登录页面</h4>
    <form method="post" action="shiro/login">
        <input type="text" name="username"/> <br/>
        <input type="password" name="password"/> <br/>
        <input type="submit" value="提交"/>  &nbsp;&nbsp;记住我<input type="checkbox" value="true" name="my-remember-me">
        <%--记住我默认设置为true--%>
        <%--form表单中的value不仅仅只有true 的状态位，还可以设置: enabled/yes/on--%>
        <%--也就是shiro对value 的判断除了true这个状态位还有: enabled/yes/on--%>
    </form>
</body>
</html>