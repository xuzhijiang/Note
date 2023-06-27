<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <title>多文件上传</title>
</head>
<body>
    <h3>${msg == null ? "多文件上传" : msg}</h3>

    <form action="/manyUpload" method="post" enctype="multipart/form-data">
        用户头像01: <input type="file" name="headerimg"/> <br />
        用户头像02: <input type="file" name="headerimg"/> <br />
        用户头像03: <input type="file" name="headerimg"/> <br />
        用户头像04: <input type="file" name="headerimg"/> <br />

        用户头像05: <input type="file" name="headerimg05"/> <br />

        用户名: <input type="text" name="username"/> <br />
        <input type="submit"/>
    </form>
</body>
</html>
