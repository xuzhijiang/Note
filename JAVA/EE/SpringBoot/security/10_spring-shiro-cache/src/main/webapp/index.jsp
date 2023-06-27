<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<body>
<h2>Hello World!</h2>

<hr>

    <%-- 已登录 --%>
    欢迎您,
    <shiro:user>
        <shiro:principal/>
        <div style="float:right;">
        <a href="${pageContext.request.contextPath}/user/logout">登出</a>
        </div>
    </shiro:user>
    <shiro:guest>
        <a href="${pageContext.request.contextPath}/user/login">请登录</a>
    </shiro:guest>
    <shiro:guest>
        游客~~
    </shiro:guest>
    <hr>
    <shiro:hasRole name="banzhang">
        <a href="#">班长操作</a>
    </shiro:hasRole>
    <shiro:lacksRole name="banzhang">
        <a href="#">同学操作</a>
    </shiro:lacksRole>

    <shiro:hasAnyRoles name="banzhang,student">
        <a href="#">进班学习</a>
    </shiro:hasAnyRoles>

    <hr>
    <shiro:hasPermission name="student:yq">
        <a href="#">收罚款</a>
    </shiro:hasPermission>
    <shiro:lacksPermission name="student:yq">
        <a href="#">进班学习</a>
    </shiro:lacksPermission>

<hr>
    ${sessionScope.name}
</body>
</html>
