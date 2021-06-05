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
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
</head>
<header class="d-flex align-items-center justify-content-center justify-content-md-between py-3 mb-4 border-bottom">
    <span class="d-flex align-items-center col-md-3 mb-2 mb-md-0 text-dark text-decoration-none h3"><fmt:message
            key="header.library.name"/></span>
    <div class="d-flex flex-row mr-3">
        <form class="mr-2">
            <select class="custom-select" id="language" name="language" onchange="submit()">
                <option value="en" ${language == 'en' ? 'selected' : ''}><fmt:message
                        key="header.language.english"/></option>
                <option value="ua" ${language == 'ua' ? 'selected' : ''}><fmt:message
                        key="header.language.ukrainian"/></option>
            </select>
        </form>
        <a class="btn btn-dark" href="${pageContext.request.contextPath}/app/logout"><fmt:message key="header.logout"/></a>
    </div>
</header>
<body>
<div class="container text-center">
    <h2>Кабінет користувача: <%= session.getAttribute("userLogin") %></h2>
    <div>
        <span class="h3">Книги: </span><a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/app/admin/addBook">Add book</a>
    </div>
    <div class="row justify-content-center">
        <div class="col-auto">
            <c:if test="${requestScope.bookList != null}">
                <table class="table table-responsive">
                    <tr>
                        <th>Title</th>
                        <th>Authors</th>
                        <th>Language</th>
                        <th>Edition</th>
                        <th>Date of publish</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Amount</th>
                        <th>Action</th>
                    </tr>
                    <c:forEach items="${requestScope.bookList}" var="book">
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
                            <td>${book.language}</td>
                            <td>${book.edition.name}</td>
                            <td>${book.publicationDate}</td>
                            <td>${book.description}</td>
                            <td>${book.price}</td>
                            <td>${book.count}</td>
<%--                            <td>--%>
<%--                                <button id="showLessMoreButton${book.id}" onclick="showDescription(${book.id})">Show--%>
<%--                                    more--%>
<%--                                </button>--%>
<%--                            </td>--%>
                            <td><a class="btn btn-outline-warning" href="${pageContext.request.contextPath}/app/admin/editBook?id=${book.id}">Edit</a>
                                <a class="btn btn-outline-danger" href="${pageContext.request.contextPath}/app/admin/deleteBook?id=${book.id}">Delete</a>
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
    </div>
    <div>
        <span class="h3">Користувачі: </span>
        <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/app/admin/addLibrarian">Add Librarian</a>
    </div>
    <div class="row justify-content-center">
        <div class="col-auto">
            <c:if test="${requestScope.userList != null}">
                <table class="table table-responsive">
                    <tr>
                        <th>Id</th>
                        <th>Login</th>
                        <th>Role</th>
                        <th>Action</th>
                    </tr>
                    <c:forEach items="${requestScope.userList}" var="user">
                        <tr>
                            <td><c:out value="${user.id}"/></td>
                            <td><c:out value="${user.login}"/></td>
                            <td><c:out value="${user.role}"/></td>
                                <%--                        <c:if test="${user.blocked == true}">--%>
                                <%--                            <td><a href="${pageContext.request.contextPath}/app/admin/unblockUser?id=${user.id}">Розблокувати</a></td>--%>
                                <%--                        </c:if>--%>
                                <%--                        <c:if test="${user.blocked == false}">--%>
                                <%--                            <td><a href="${pageContext.request.contextPath}/app/admin/blockUser?id=${user.id}">Заблокувати</a></td>--%>
                                <%--                        </c:if>--%>
                            <c:if test="${user.role == 'LIBRARIAN'}">
                                <td>
                                    <a class="btn btn-outline-danger" href="${pageContext.request.contextPath}/app/admin/deleteLibrarian?id=${user.id}">Видалити</a>
                                </td>
                            </c:if>
                            <c:if test="${user.role != 'LIBRARIAN'}">
                                <td>
                                    <span class="font-weight-bold">-</span>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
            <c:if test="${requestScope.userList == null}">
                <p>Пусто</p>
            </c:if>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/user/admin/js/home.js"></script>
</html>
