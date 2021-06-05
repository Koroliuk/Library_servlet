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
    <h2>Замовити книгу</h2>
    <div class="row justify-content-center">
        <div class="col-auto">
            <table class="table table-responsive">
                <tr>
                    <td class="text-left">
                        <span>Заголовок: ${requestScope.book.title}</span>
                    </td>
                </tr>
                <tr>
                    <td class="text-left">Автори:
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
                        ${requestScope.authorsString}
                    </td>
                </tr>
                <tr>
                    <td class="text-left">
                        <span>Короткий опис:<br/> ${requestScope.book.description}</>
                    </td>
                </tr>
                <tr>
                    <td class="text-left">
                        <span>Мова: ${requestScope.book.language}</span>
                    </td>
                </tr>
                <tr>
                    <td class="text-left">
                        <span>Видання: ${requestScope.book.edition.name}</span>
                    </td>
                </tr>
                <tr>
                    <td class="text-left">
                        <span>Дата видання: ${requestScope.book.publicationDate}</span>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <form id="orderForm" method="post" action="${pageContext.request.contextPath}/app/reader/orderBook?bookId=${requestScope.book.id}&userLogin=${requestScope.user.login}">
        <div class="form-group">
            <label for="orderType">Тип замовлення</label>
            <select id="orderType" name="orderType" onchange="chooseType()">
                <option value="subscription">Абонемент</option>
                <option value="readingHole">Читальний зал</option>
            </select>
            <br/>
            <c:if test="${param.validError == true}">
                <span>Перевірте правильність вибору дат</span>
            </c:if>
            <c:if test="${param.amountError == true}">
                <span>Немає екземплярів спробуйте пізніше</span>
            </c:if>
            <span id="warning3" class="text-warning" hidden>Дата початку має буте не пізніше дати кінця</span>
            <span id="warning1" class="text-warning" hidden>Оберіть дату не раніше сьогодні</span>
            <label>Start date: <input id="startDate" type="date" name="startDate" value="<%= LocalDate.now() %>">
            </label>
            <br/>
            <span id="warning2" class="text-warning" hidden>Оберіть дату не раніше сьогодні</span>
            <label>End date: <input id="endDate" type="date" name="endDate" value="<%= LocalDate.now() %>">
            </label>
        </div>
        <input class="btn btn-outline-info" type="submit" value="Замовити">
    </form>
    <div>
        <a href="${pageContext.request.contextPath}/app/search?page=1&keyWords=">To search</a>
        <a href="${pageContext.request.contextPath}/app/reader/home">To cabinet</a>
    </div>
</div>
</body>
<footer class="navbar fixed-bottom d-flex flex-row justify-content-sm-between align-items-center bg-light text-lg-start p-3">
    <div>
        <p>
            <fmt:message key="footer.licence"/>
            <a href="https://github.com/Koroliuk/Library_servlet/blob/main/LICENSE">
                GNU GPLv3 License
            </a>.
            <br>
            <a href="https://github.com/Koroliuk/Library_servlet"><fmt:message key="footer.project.github"/></a><br/>
            <span>@2021</span>
        </p>
    </div>
    <div>
        <p>
            <fmt:message key="footer.questions"/>
            <a href="https://github.com/Koroliuk/Library_servlet/issues/new">GitHub</a>.
        </p>
    </div>
</footer>
<script type="text/javascript" src="${pageContext.request.contextPath}/user/reader/js/orderBook.js">
</script>
<script type="text/javascript">
    Date.prototype.withoutTime = function () {
        const date = new Date(this);
        date.setHours(0, 0, 0, 0);
        return date;
    }

    const form = document.getElementById('orderForm');
    const startDate = document.getElementById('startDate');
    const endDate = document.getElementById('endDate');
    const warning1 = document.getElementById('warning1');
    const warning2 = document.getElementById('warning2');
    const warning3 = document.getElementById('warning3');

    startDate.addEventListener('input', () => {
        const now = new Date().withoutTime();
        const start = Date.parse(startDate.value);
        warning1.hidden = start >= now;
    });

    endDate.addEventListener('input', () => {
        const now = new Date().withoutTime();
        const end = Date.parse(endDate.value);
        warning1.hidden = end >= now;    });

    form.addEventListener('submit', (event) => {
        const now = new Date().withoutTime();
        const start = Date.parse(startDate.value);
        const end = Date.parse(endDate.value);
        if (start < now || end < now || start > end) {
            warning3.hidden = false;
            event.preventDefault();
            return false;
        }
        warning3.hidden = true;
        return true;
    });
</script>
</html>
