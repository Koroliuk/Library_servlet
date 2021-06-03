<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="messages" />
<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Main Page</title>
</head>
<header>
    <div class="header_name">
        <p><fmt:message key="header.library.name"/></p>
    </div>
    <div class="header_buttons">
        <c:if test="${sessionScope.userLogin == null}">
            <p><a href="${pageContext.request.contextPath}/app/signup"><fmt:message key="header.signup"/></a></p>
            <p><a href="${pageContext.request.contextPath}/app/login"><fmt:message key="header.login"/></a></p>
        </c:if>
        <c:if test="${sessionScope.userLogin != null}">
            <p><a href="${pageContext.request.contextPath}/app/logout"><fmt:message key="header.logout"/></a></p>
        </c:if>
        <form>
            <select id="language" name="language" onchange="submit()">
                <option value="en" ${language == 'en' ? 'selected' : ''}><fmt:message key="header.language.english"/></option>
                <option value="ua" ${language == 'ua' ? 'selected' : ''}><fmt:message key="header.language.ukrainian"/></option>
            </select>
        </form>
    </div>
</header>
<body>
    <h2><fmt:message key="index.welcome"/></h2>
    <a href="${pageContext.request.contextPath}/app/search?page=1&keyWords=">Перейти до пошуку</a>
    <div>
        <form method="post" action="${pageContext.request.contextPath}/app/search?page=1">
            <label>
                <input type="text" name="keyWords" placeholder="Пошук">
            </label>
            <input type="submit" value="Знайти">
        </form>
    </div>
</body>
<footer>
    <div>
        <a href="https://github.com/Koroliuk/Library_servlet"><fmt:message key="footer.project.github"/></a>
        <p>@2021</p>
    </div>
    <div>
        <p>
            <fmt:message key="footer.licence"/>
            <a href="https://github.com/Koroliuk/Library_servlet/blob/main/LICENSE">
                GNU GPLv3 License
            </a>.
        </p>
        <p>
            <fmt:message key="footer.questions"/>
            <a href="https://github.com/Koroliuk/Library_servlet/issues/new">GitHub</a>.
        </p>
    </div>
</footer>
</html>
