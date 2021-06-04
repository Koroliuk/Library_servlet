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
    <title>User home page</title>
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
        <form method="post" action="${pageContext.request.contextPath}/app/search">
            <label>
                <input type="text" name="keyWords" placeholder="Пошук">
            </label>
            <input type="submit" value="Знайти">
        </form>
    </div>
    <h1>Абонемент</h1>
    <table>
        <th>Id</th>
        <th>Book</th>
        <th>Authors</th>
        <th>Status</th>
        <th>End date/Fine</th>
        <c:forEach items="${requestScope.orderList}" var="order">
            <tr>
                <td>${order.id}</td>
                <td>${order.book.title}</td>
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
                    ${requestScope.authorsString}
                </td>
                <td>${order.orderStatus}</td>
                <%
                    LocalDate now = LocalDate.now();
                    LocalDate end = order.getEndDate();
                    boolean flag = now.isAfter(end);
                    request.setAttribute("flag", flag);
                %>
                <c:if test="${requestScope.flag == true}">
                    <%
                        int amountOfDays = Period.between(end, now).getDays();
                        float fine = (float) (amountOfDays* book.getPrice()*0.01);
                        request.setAttribute("fine", fine);
                    %>
                    <td>
                        <a href="${pageContext.request.contextPath}/app/reader/payFine?orderId=${order.id}">${fine}</a>
                    </td>
                </c:if>
                <c:if test="${requestScope.flag == false}">
                    <td>${order.endDate}</td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
