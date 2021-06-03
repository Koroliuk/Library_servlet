<%@ page import="ua.training.model.entity.Author" %>
<%@ page import="ua.training.model.entity.Book" %>
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
    <title>Add book page</title>
</head>
<header>
    <div class="header_name">
        <p><fmt:message key="header.library.name"/></p>
    </div>
    <div class="header_buttons">
        <p><a href="${pageContext.request.contextPath}/app/logout"><fmt:message key="header.logout"/></a></p>
        <form>
            <select id="language" name="language" onchange="submit()">
                <option value="en" ${language == 'en' ? 'selected' : ''}><fmt:message key="header.language.english"/></option>
                <option value="ua" ${language == 'ua' ? 'selected' : ''}><fmt:message key="header.language.ukrainian"/></option>
            </select>
        </form>
    </div>
</header>
<body>
    <c:if test="${requestScope.action} == 'add'">
        <h1>Додати книгу</h1>
    </c:if>
    <c:if test="${requestScope.action} == 'edit'">
        <h1>Редагувати книгу</h1>
    </c:if>
    <div>
        <form id="signupForm" method="post" action="${pageContext.request.contextPath}/app/admin/${requestScope.action}Book?id=${param.id != null ? param.id : ""}">
            <table>
                <tr>
                    <td>
                        <label>
                            <input type="text" id="title" name="title" placeholder="Title" value="${requestScope.book.title != null ? requestScope.book.title : ""}">
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>
                            <%
                                Book book = (Book) request.getAttribute("book");
                                String authors = "";
                                if (book != null) {
                                    StringBuilder authorsString = new StringBuilder();
                                    for (Author author : book.getAuthors()) {
                                        authorsString.append(author.getName()).append(",").append(" ");
                                    }
                                    authorsString.deleteCharAt(authorsString.length() - 1);
                                    authorsString.deleteCharAt(authorsString.length() - 1);
                                    authors = authorsString.toString();
                                }
                                request.setAttribute("authorsString", authors);
                            %>
                            <input type="text" id="authors" name="authors" placeholder="Author" value="${requestScope.authorsString != null ? requestScope.authorsString : ""}">
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>
                            <textarea id="description" name="description" placeholder="Description" wrap="soft">${requestScope.book.description != null ? requestScope.book.description : ""}</textarea>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>
                            <input type="text" id="bookLanguage" name="bookLanguage" placeholder="Language" value="${requestScope.book.language != null ? requestScope.book.language : ""}">
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>
                            <input type="text" id="edition" name="edition" placeholder="Edition" value="${requestScope.book.edition.name != null ? requestScope.book.edition.name : ""}">
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>
                            <input type="date" id="publicationDate" name="publicationDate" placeholder="Date of publication" value="${requestScope.book.publicationDate != null ? requestScope.book.publicationDate : ""}">
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>
                            <input type="text" id="price" name="price" placeholder="Price" value="${requestScope.book.price != null ? requestScope.book.price : ""}">
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>
                            <input type="text" id="count" name="count" placeholder="Count" value="${requestScope.book.count != null ? requestScope.book.count : ""}">
                        </label>
                    </td>
                </tr>
            </table>
            <div>
                <input type="submit" value="Зберегти">
            </div>
        </form>
    </div>
    <div>
        <a href="${pageContext.request.contextPath}/app/admin/home"><fmt:message key="global.to.home.page"/></a>
    </div>
</body>
</html>

