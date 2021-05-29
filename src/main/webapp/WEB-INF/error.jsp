<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Error Page</title>
</head>
<body>
  <h2>Error Page</h2>
  <div>
    <p><i>Error <%= exception %></i></p>
  </div>
  <div>
    <p><a href="${pageContext.request.contextPath}/index.jsp">To home page</a></p>
  </div>
</body>
</html>
