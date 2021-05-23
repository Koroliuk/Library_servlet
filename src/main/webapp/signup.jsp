<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Registration Page</title>
</head>
<body>
    <h1>Registration</h1>
    <div>
        <form method="post" action="${pageContext.request.contextPath}/app/signup">
            <div>
                <input type="text" name="login">
            </div>
            <div>
                <input type="password" name="password">
            </div>
            <div>
                <input class="button" type="submit" value="Sign Up">
            </div>
            <div>
                <a href="${pageContext.request.contextPath}/app/login">Already singed up</a>
            </div>
        </form>
    </div>
    <div>
        <a href="${pageContext.request.contextPath}/index.jsp">To home page</a>
    </div>
</body>
</html>
