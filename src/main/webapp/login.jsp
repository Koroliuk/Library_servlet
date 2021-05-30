<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html lang="${language}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login Page</title>
</head>
<header>
    <div>
        <p><fmt:message key="header.library.name"/></p>
    </div>
    <div>
        <form>
            <select id="language" name="language" onchange="submit()">
                <option value="en" ${language == 'en' ? 'selected' : ''}><fmt:message key="header.language.english"/></option>
                <option value="ua" ${language == 'ua' ? 'selected' : ''}><fmt:message key="header.language.ukrainian"/></option>
            </select>
        </form>
    </div>
</header>
<body>
    <h1><fmt:message key="login.caption"/></h1>
    <form id="loginForm" method="post" action="${pageContext.request.contextPath}/app/login">
        <table>
            <c:if test="${param.validError == true}">
                <span><fmt:message key="login.login.password.validation.message"/></span>
            </c:if>
            <c:if test="${param.passwordError == true}">
                <span><fmt:message key="login.password.validation.message"/></span>
            </c:if>
            <c:if test="${param.loginError == true}">
                <span><fmt:message key="login.login.validation.message"/></span>
            </c:if>
            <span id="validationErrorMessage"></span>
            <tr>
                <td>
                    <label>
                        <input type="text" id="login" name="login" placeholder="<fmt:message key="global.login"/>">
                    </label>
                </td>
            </tr>
            <tr>
                <td>
                    <label>
                        <input type="password" id="password" name="password" placeholder="<fmt:message key="global.password"/>">
                    </label>
                </td>
            </tr>
        </table>
        <div>
            <input type="submit" value="<fmt:message key="login.button.name"/>">
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/app/signup"><fmt:message key="login.to.signup"/></a>
        </div>
    </form>
    <div>
        <a href="${pageContext.request.contextPath}/index.jsp"><fmt:message key="global.to.home.page"/></a>
    </div>
</body>
<script type="text/javascript">
    const validationErrorString = '<fmt:message key="login.login.password.validation.message"/>';
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/login.js"></script>
</html>
