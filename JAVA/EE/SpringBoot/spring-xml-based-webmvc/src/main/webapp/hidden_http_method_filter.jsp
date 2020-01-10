<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Title</title>
</head>
<body>
    <form action="/restful/api/put" method="post">
        <input type="hidden" name="_method" value="put">
        <input type="submit" value="put提交">
    </form>

    <br />
    <form action="/restful/api/delete" method="post">
        <input type="hidden" name="_method" value="delete">
        <input type="submit" value="delete提交">
    </form>
</body>
</html>
