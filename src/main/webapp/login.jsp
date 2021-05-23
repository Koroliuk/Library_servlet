<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login Page</title>
</head>
<body>
    <h1>Login</h1>
    <form method="post" action="${pageContext.request.contextPath}/app/login">
        <div>
            <input type="text" name="login">
        </div>
        <div>
            <input type="password" name="password">
        </div>
        <div>
            <input class="button" type="submit" value="Login">
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/app/signup">Singed up</a>
        </div>
    </form>
    <br/>
    <a href="${pageContext.request.contextPath}/app/logout">На головну</a>
</body>
</html>
