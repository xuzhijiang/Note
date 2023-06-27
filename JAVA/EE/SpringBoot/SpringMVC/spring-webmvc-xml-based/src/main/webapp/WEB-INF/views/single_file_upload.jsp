<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <title>单文件上传</title>
</head>
<body>
    <h3>${msg == null ? "单文件上传" : msg}</h3>

    <form action="/singleUpload" method="post" enctype="multipart/form-data">
        用户头像: <input type="file" name="headerimg"/> <br />
        用户名: <input type="text" name="username"/> <br />
        <input type="submit"/>
    </form>
</body>
</html>
