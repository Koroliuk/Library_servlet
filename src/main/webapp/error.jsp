<%--
  Created by IntelliJ IDEA.
  User: koroliuk
  Date: 23.05.21
  Time: 13:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Error Page</title>
</head>
<body>
<h2>
  Error Page<br/>
  <i>Error <%= exception %></i>
</h2>
<br>
<a href="${pageContext.request.contextPath}/index.jsp">Index</a>


</body>
</html>