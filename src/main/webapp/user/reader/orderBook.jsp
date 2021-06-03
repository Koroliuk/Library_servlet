<%@ page import="ua.training.model.entity.Book" %>
<%@ page import="ua.training.model.entity.Author" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Order book page</title>
</head>
<header>
    <div class="header_name">
        <p><fmt:message key="header.library.name"/></p>
    </div>
    <div class="header_buttons">
        <p><a href="${pageContext.request.contextPath}/app/logout"><fmt:message key="header.logout"/></a></p>
        <form>
            <select id="language" name="language" onchange="submit()">
                <option value="en" ${language == 'en' ? 'selected' : ''}><fmt:message
                        key="header.language.english"/></option>
                <option value="ua" ${language == 'ua' ? 'selected' : ''}><fmt:message
                        key="header.language.ukrainian"/></option>
            </select>
        </form>
    </div>
</header>
<body>
<h1>Замовити книгу</h1>
<div>
    <table>
        <tr>
            <td>
                <p>${requestScope.book.title}</p>
            </td>
        </tr>
        <tr>
            <td>
                <%
                    Book book = (Book) request.getAttribute("book");
                    String authors = "";
                    StringBuilder authorsString = new StringBuilder();
                    for (Author author : book.getAuthors()) {
                        authorsString.append(author.getName()).append(",").append(" ");
                    }
                    authorsString.deleteCharAt(authorsString.length() - 1);
                    authorsString.deleteCharAt(authorsString.length() - 1);
                    authors = authorsString.toString();
                    request.setAttribute("authorsString", authors);
                %>
                <p>${requestScope.authorsString}</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>${requestScope.book.description}</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>${requestScope.book.language}</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>${requestScope.book.edition.name}</p>
            </td>
        </tr>
        <tr>
            <td>
                <p>${requestScope.book.publicationDate}</p>
            </td>
        </tr>
    </table>
    <form method="post"
          action="${pageContext.request.contextPath}/app/reader/orderBook?bookId=${requestScope.book.id}&userLogin=${requestScope.user.login}">
        <label for="orderType">Тип замовлення</label>
        <select id="orderType" name="orderType" onchange="chooseType()">
            <option value="subscription">Абонемент</option>
            <option value="readingHole">Читальний зал</option>
        </select>
        <label>Start date:
            <input id="startDate" type="date" name="startDate" value="<%= LocalDate.now() %>">
        </label>
        <label>End date:
            <input id="endDate" type="date" name="endDate" value="<%= LocalDate.now() %>">
        </label>
        <input type="submit" value="Замовити">
    </form>
</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/user/reader/js/orderBook.js">
</script>
</html>
