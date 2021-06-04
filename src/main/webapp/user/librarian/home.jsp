<%@ page import="ua.training.model.entity.Book" %>
<%@ page import="ua.training.model.entity.Author" %>
<%@ page import="ua.training.model.entity.Order" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.Period" %>
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
    <title>Librarian cabinet</title>
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
    <h1>Кабінет користувача: <%= session.getAttribute("userLogin") %>!</h1>
    <div>
        <c:if test="${requestScope.orderList != null}">
            <table>
                <tr>
                    <th>Id</th>
                    <th>User Login</th>
                    <th>Book</th>
                    <th>Action</th>
                </tr>
                <c:forEach items="${requestScope.orderList}" var="order">
                    <tr>
                        <td><c:out value="${order.id}"/></td>
                        <td><c:out value="${order.user.login}"/></td>
                        <td>
                            <c:out value="${order.book.title}"/>
                            <button id="showLessMoreButton${order.id}" onclick="showDescription(${order.id})">Show more</button>
                        </td>
                        <td><c:if test="${order.orderStatus == 'RECEIVED'}">
                            <a href="${pageContext.request.contextPath}/app/librarian/home?id=${order.id}">Видати</a>
                            </c:if>
                            <c:if test="${order.orderStatus == 'APPROVED'}">
                                <span>Підтверджено</span>
                            </c:if>
                        </td>
                    </tr>
                    <tr id="additionalInfo${order.id}" hidden>
                        <td>
                            <%
                                Order order = (Order) pageContext.getAttribute("order");;
                                Book book = order.getBook();
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
                            <p>${order.book.language}</p>
                            <p>${order.book.edition.name}</p>
                            <p>${order.book.publicationDate}</p>
                        </td>
                        <td>
                            <p>${order.book.description}</p>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
    <div>
        <c:if test="${requestScope.userList != null}">
            <table>
                <tr>
                    <th>Login</th>
                </tr>
                <c:forEach items="${requestScope.userList}" var="user">
                    <c:if test="${user.role == 'READER'}">
                        <tr>
                            <td>
                                ${user.login}
                                <button id="showLessMoreButtonAbon${user.id}" onclick="showOrders(${user.id})">Show more</button>
                            </td>
                        </tr>
                        <table  id="userOrders${user.id}" hidden>
                        <c:forEach items="${requestScope.orderList}" var="order">
                            <c:if test="${order.user.id == user.id}">
                                <tr>
                                    <td>${order.id}</td>
                                    <td>${order.book.title}</td>
                                    <td>
                                        <%
                                            Order order = (Order) pageContext.getAttribute("order");
                                            ;
                                            Book book = order.getBook();
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
                                            ${requestScope.authorsString}
                                    </td>
                                    <td>${order.orderStatus}</td>
                                    <%
                                        LocalDate now = LocalDate.now();
                                        LocalDate end = order.getEndDate();
                                        boolean flag = now.isAfter(end);
                                        request.setAttribute("flag", flag);
                                    %>
                                    <td>
                                        <c:if test="${requestScope.flag == true}">
                                            <%
                                                int amountOfDays = Period.between(end, now).getDays();
                                                float fine = (float) (amountOfDays * book.getPrice() * 0.01);
                                                request.setAttribute("fine", fine);
                                            %>
                                        </c:if>
                                        <a href="${pageContext.request.contextPath}/app/reader/payFine?orderId=${order.id}">${fine}</a>
                                    </td>
                                    <c:if test="${requestScope.flag == false}">
                                        <td>${order.endDate}</td>
                                    </c:if>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </table>
                    </c:if>
                </c:forEach>
            </table>
        </c:if>
    </div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/user/librarian/js/home.js"></script>
</html>
