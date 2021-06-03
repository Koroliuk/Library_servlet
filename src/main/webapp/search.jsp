<%@ page import="ua.training.model.entity.Book" %>
<%@ page import="ua.training.model.entity.Author" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
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
    <title>Search Page</title>
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
                <option value="en" ${language == 'en' ? 'selected' : ''}><fmt:message
                        key="header.language.english"/></option>
                <option value="ua" ${language == 'ua' ? 'selected' : ''}><fmt:message
                        key="header.language.ukrainian"/></option>
            </select>
        </form>
    </div>
</header>
<body>
    <h2>Catalog</h2>
    <div>
        <form method="post" action="${pageContext.request.contextPath}/app/search">
            <label>
                <input type="text" name="keyWords" placeholder="Пошук">
            </label>
            <input type="submit" value="Знайти">
        </form>
    </div>
    <c:if test="${requestScope.bookList != null}">
        <table>
            <tr>
                <th>Title</th>
                <th>Authors</th>
            </tr>
            <c:forEach items="${requestScope.bookList}" var="book" >
                <tr>
                    <td><c:out value="${book.title}"/></td>
                    <td>
                        <%
                            Book book = (Book) pageContext.getAttribute("book");
                            StringBuilder authorsString = new StringBuilder();
                            for (Author author : book.getAuthors()) {
                                authorsString.append(author.getName()).append(",").append(" ");
                            }
                            authorsString.deleteCharAt(authorsString.length() - 1);
                            authorsString.deleteCharAt(authorsString.length() - 1);
                            out.print(authorsString.toString());
                        %>
                    </td>
                    <td>
                        <button id="showLessMoreButton${book.id}" onclick="showDescription(${book.id})">Show more</button>
                    </td>
                    <c:if test="${sessionScope.role == 'READER'}">
                        <td>
                            <a href="${pageContext.request.contextPath}/app/reader/orderBook?bookId=${book.id}&userLogin=${sessionScope.userLogin}">Замовити</a>
                        </td>
                    </c:if>
                </tr>
                <tr id="additionalInfo${book.id}" hidden>
                    <td>
                        Language: <c:out value="${book.language}"/><br/>
                        Edition: <c:out value="${book.edition.name}"/><br/>
                        Date of publish: <br/> <c:out value="${book.publicationDate}"/>
                    </td>
                    <td>
                        Description:<br/><c:out value="${book.description}"/><br/>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${requestScope.bookList == null}">
        <p>Пусто</p>
    </c:if>
    <div>
        <a href="${pageContext.request.contextPath}/index.jsp"><fmt:message key="global.to.home.page"/></a>
    </div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/js/seach.js"></script>
</html>
