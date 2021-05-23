<%--
  Created by IntelliJ IDEA.
  User: koroliuk
  Date: 23.05.21
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Login</h1>
    <form method="get" action="${pageContext.request.contextPath}/app/login">
        <input type="text" name="name"><br/>
        <input type="password" name="pass"><br/><br/>
        <input class="button" type="submit" value="Війти">
    </form>
    <br/>
    <a href="${pageContext.request.contextPath}/app/logout">На головну</a>
</body>
</html>
