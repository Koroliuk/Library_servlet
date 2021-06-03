<%@ page import="ua.training.model.entity.Author" %>
<%@ page import="ua.training.model.entity.Book" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="messages" />
<!DOCTYPE html>
<html lang="${language}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Admin cabinet</title>
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
    <h1>Hello <%= session.getAttribute("userLogin") %>!</h1>
    <div>
        <a href="${pageContext.request.contextPath}/app/admin/addBook">Add book</a>
        <a href="${pageContext.request.contextPath}/app/admin/addLibrarian">Add Librarian</a>
    </div>
    <div>
        <c:if test="${requestScope.userList != null}">
            <table>
                <tr>
                    <th>Id</th>
                    <th>Login</th>
                    <th>Role</th>
                </tr>
                <c:forEach items="${requestScope.userList}" var="user">
                    <tr>
                        <td><c:out value="${user.id}"/></td>
                        <td><c:out value="${user.login}"/></td>
                        <td><c:out value="${user.role}"/></td>
                        <c:if test="${user.blocked == true}">
                            <td><a href="${pageContext.request.contextPath}/app/admin/unblockUser?id=${user.id}">Розблокувати</a></td>
                        </c:if>
                        <c:if test="${user.blocked == false}">
                            <td><a href="${pageContext.request.contextPath}/app/admin/blockUser?id=${user.id}">Заблокувати</a></td>
                        </c:if>
                        <c:if test="${user.role == 'LIBRARIAN'}">
                            <td><a href="${pageContext.request.contextPath}/app/admin/deleteLibrarian?id=${user.id}">Видалити</a></td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${requestScope.userList == null}">
            <p>Пусто</p>
        </c:if>
    </div>
    <div>
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
                        <td><a href="${pageContext.request.contextPath}/app/admin/editBook?id=${book.id}">Edit</a></td>
                        <td><a href="${pageContext.request.contextPath}/app/admin/deleteBook?id=${book.id}">Delete</a>
                        </td>
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
    </div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/user/admin/js/home.js"></script>
</html>
