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
            <c:if test="${requestScope.action == 'add'}"><c:set var="doBook" value="addBook"/>
            </c:if>
            <c:if test="${requestScope.action == 'edit'}"><c:set var="doBook" value="editBook"/></c:if>
            <select class="custom-select" id="language" name="language" onChange="window.document.location.href=this.options[this.selectedIndex].value;">
                <option value="${pageContext.request.contextPath}/app/admin/${doBook}?id=${param.id}&language=en"
                ${language == 'en' ? 'selected' : ''}>
                    <fmt:message key="header.language.english"/></option>
                <option value="${pageContext.request.contextPath}/app/admin/${doBook}?id=${param.id}&language=ua"
                ${language == 'ua' ? 'selected' : ''}>
                    <fmt:message key="header.language.ukrainian"/></option>
            </select>
        </form>
        <a class="btn btn-dark" href="${pageContext.request.contextPath}/app/logout"><fmt:message
                key="header.logout"/></a>
    </div>
</header>
<body>
<div class="container text-center">
    <c:if test="${requestScope.action == 'add'}">
        <h3>Додати книгу</h3>
    </c:if>
    <c:if test="${requestScope.action == 'edit'}">
        <h3>Редагувати книгу</h3>
    </c:if>
    <form id="form" method="post"
          action="${pageContext.request.contextPath}/app/admin/${requestScope.action}Book?id=${param.id != null ? param.id : ""}">
        <div class="form-group">
            <c:if test="${param.validError == true}">
                <span class="text-danger">Перевірте правильність введених даних</span>
                <br/>
            </c:if>
            <c:if test="${param.createError == true}">
                <span class="text-danger">Така книга вже існує</span>
                <br/>
            </c:if>
            <c:if test="${param.successCreation == true}">
                <span class="text-success">Книгу успішно додано</span>
                <br/>
            </c:if>
            <div>
                <label style="width: 37%;">
                    <input class="form-control" type="text" id="titleUa" name="titleUa" placeholder="Заголовок"
                           value="${requestScope.book.title != null ? requestScope.book.title : ""}">
                </label>
                <label style="width: 37%;">
                    <input class="form-control" type="text" id="titleEn" name="titleEn" placeholder="Title"
                           value="${requestScope.book.title != null ? requestScope.book.title : ""}">
                </label>
                <br/>
                <span class="text-danger" id="titleMessage"></span>
            </div>
            <div>
                <label style="width: 37%;">
                    <%
                        Book book = (Book) request.getAttribute("book");
                        String authors = "";
                        if (book != null) {
                            StringBuilder authorsString = new StringBuilder();
                            for (Author author : book.getAuthors()) {
                                authorsString.append(author.getName()).append(",");
                            }
                            authorsString.deleteCharAt(authorsString.length() - 1);
                            authorsString.deleteCharAt(authorsString.length() - 1);
                            authors = authorsString.toString();
                        }
                        request.setAttribute("authorsString", authors);
                    %>
                    <input class="form-control" type="text" id="authorsUa" name="authorsUa" placeholder="Автори"
                           value="${requestScope.authorsString != null ? requestScope.authorsString : ""}">
                </label>
                <label style="width: 37%;">
                    <%
//                        Book book = (Book) request.getAttribute("book");
//                        String authors = "";
                        if (book != null) {
                            StringBuilder authorsString = new StringBuilder();
                            for (Author author : book.getAuthors()) {
                                authorsString.append(author.getName()).append(",");
                            }
                            authorsString.deleteCharAt(authorsString.length() - 1);
                            authorsString.deleteCharAt(authorsString.length() - 1);
                            authors = authorsString.toString();
                        }
                        request.setAttribute("authorsString", authors);
                    %>
                    <input class="form-control" type="text" id="authorsEn" name="authorsEn" placeholder="Authors"
                           value="${requestScope.authorsString != null ? requestScope.authorsString : ""}">
                </label>
                <br/>
                <span class="text-danger" id="authorsMessage"></span>
            </div>
            <div>
                <label style="width: 75%;">
                    <textarea class="form-control" id="descriptionUa" name="descriptionUa" placeholder="Опис"
                                              wrap="soft">${requestScope.book.description != null ? requestScope.book.description : ""}</textarea>
                </label>
                <label style="width: 75%;">
                    <textarea class="form-control" id="descriptionEn" name="descriptionEn" placeholder="Description"
                              wrap="soft">${requestScope.book.description != null ? requestScope.book.description : ""}</textarea>
                </label>
                <br/>
                <span class="text-danger" id="descriptionMessage"></span>
            </div>
            <div>
                <label style="width: 37%;">
                    <input class="form-control" type="text" id="bookLanguageUa" name="bookLanguageUa" placeholder="Мова"
                           value="${requestScope.book.language != null ? requestScope.book.language : ""}">
                </label>
                <label style="width: 37%;">
                    <input class="form-control" type="text" id="bookLanguageEn" name="bookLanguageEn" placeholder="Language"
                           value="${requestScope.book.language != null ? requestScope.book.language : ""}">
                </label>
                <br/>
                <span class="text-danger" id="bookLanguageMessage"></span>
            </div>
            <div>
                <label style="width: 37%;">
                    <input class="form-control" type="text" id="editionUa" name="editionUa" placeholder="Видання"
                           value="${requestScope.book.edition.name != null ? requestScope.book.edition.name : ""}">
                </label>
                <label style="width: 37%;">
                    <input class="form-control" type="text" id="editionEn" name="editionEn" placeholder="Edition"
                           value="${requestScope.book.edition.name != null ? requestScope.book.edition.name : ""}">
                </label>
                <br/>
                <span class="text-danger" id="editionMessage"></span>
            </div>
            <div>
                <span class="text-dark h5">Дата видання:</span>
                <br/>
                <label style="width: 75%;">
                    <input class="form-control" type="date" id="publicationDate" name="publicationDate"
                           placeholder="Date of publication"
                           value="${requestScope.book.publicationDate != null ? requestScope.book.publicationDate : ""}">
                </label>
                <br/>
                <span class="text-danger" id="publicationDateMessage"></span>
            </div>
            <div>
                <label style="width: 28%;">
                    <input class="form-control" type="text" id="price" name="price" placeholder="Price"
                           value="${requestScope.book.price != null ? requestScope.book.price : ""}">
                </label>
                <label for="currency">
                    <select style="width: 75px;" class="custom-select-sm" id="currency" name="currency" onchange="chooseType()">
                        <option value="uan">UAN</option>
                        <option value="usd">USD</option>
                    </select>
                </label>
                <label style="width: 35%;">
                    <input class="form-control" type="text" id="count" name="count" placeholder="Count"
                           value="${requestScope.book.count != null ? requestScope.book.count : ""}">
                </label>
                <span class="text-danger" id="countMessage"></span>
                <span class="text-danger" id="priceMessage"></span>
                <br/>
            </div>
        </div>
        <div>
            <input type="submit" class="btn btn-outline-info" value="Зберегти">
        </div>
    </form>
    <div>
        <a style="padding-left: 400px;" href="${pageContext.request.contextPath}/app/admin/home"><fmt:message key="global.to.home.page"/></a>
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
<script>
    Date.prototype.withoutTime = function () {
        const date = new Date(this);
        date.setHours(0, 0, 0, 0);
        return date;
    }

    const titleValidationMessage1 = 'Заголовок має містити хоча б 2 та не більше 50 символів';
    const titleValidationMessage2 = 'Заголовок не може містити хоча б один непробільний символ';
    const authorsValidatorMessage = 'Імена авторів мають розділятись комою';
    const descriptionValidationMessage = 'Опис може містити до 1000 символів';
    const bookLanguageValidationMessage = 'Поле може мітити до 30 символів';
    const editionValidationMessage = 'Назва видання має мати не більше 30 символів';
    const publicationDateValidationMessage = 'Дата видання має бути не пізніше сьогодні';
    const priceValidationMessage = 'Ціна має бути числом, яке більше нуля';
    const countValidationMessage = 'Кількість екземплярів має бути цілим числом, яке більше нуля';

    const form = document.getElementById('form');
    const title = document.getElementById('title');
    const authors = document.getElementById('authors');
    const description = document.getElementById("description");
    const bookLanguage = document.getElementById("bookLanguage");
    const edition = document.getElementById('edition');
    const publicationDate = document.getElementById('publicationDate');
    const price = document.getElementById('price');
    const count = document.getElementById('count');

    const titleMessage = document.getElementById('titleMessage');
    const authorsMessage = document.getElementById('authorsMessage');
    const descriptionMessage = document.getElementById('descriptionMessage');
    const bookLanguageMessage = document.getElementById('bookLanguageMessage');
    const editionMessage = document.getElementById('editionMessage');
    const publicationDateMessage = document.getElementById('publicationDateMessage');
    const priceMessage = document.getElementById('priceMessage');
    const countMessage = document.getElementById('countMessage');

    const titleRegExp = /^[\S][\S ]{1,49}$/;
    const authorsRegExp = /^(?<!,)(([A-z]\.(?!\.)){0,2}([A-z]{1,20})){1}((?<!,),([A-z]\.(?!\.)){0,2}([A-z]{1,20}))*((?<=,),([A-z]\.(?!\.)){0,2}([A-z]{1,20})(?!,))?$/;
    const descriptionRegExp = /^.{0,1000}$/;
    const bookLanguageRegExp = /^[A-z]{1,30}$/;
    const editionRegExp = /^.{1,30}$/;
    const priceRegExp = /^[0-9]+\.?[0-9]+$/;
    const countRegExp = /^[0-9]+$/;

    title.addEventListener("input", () => {
        const titleTest = titleRegExp.test(title.value);
        if (titleTest) {
            titleMessage.innerText = "";
        } else {
            const titleStrip = title.value.trim();
            if (titleStrip === '') {
                titleMessage.innerText = titleValidationMessage2;
            } else {
                titleMessage.innerText = titleValidationMessage1;
            }
        }
    });

    authors.addEventListener("input", () => {
        const authorsTest = authorsRegExp.test(authors.value);
        if (authorsTest) {
            authorsMessage.innerText = "";
        } else {
            authorsMessage.innerText = authorsValidatorMessage;
        }
    });

    description.addEventListener('input', () => {
        const descriptionTest = descriptionRegExp.test(description.value);
        if (descriptionTest) {
            descriptionMessage.innerText = "";
        } else {
            descriptionMessage.innerText = descriptionValidationMessage;
        }
    });

    bookLanguage.addEventListener('input', () => {
        const languageTest = bookLanguageRegExp.test(bookLanguage.value);
        if (languageTest) {
            bookLanguageMessage.innerText = "";
        } else {
            bookLanguageMessage.innerText = bookLanguageValidationMessage;
        }
    });

    edition.addEventListener('input', () => {
        const editionTest = editionRegExp.test(edition.value);
        if (editionTest) {
            editionMessage.innerText = "";
        } else {
            editionMessage.innerText = editionValidationMessage;
        }
    });

    publicationDate.addEventListener('input', () => {
        const now = new Date().withoutTime();
        const date = new Date(publicationDate.value).withoutTime();
        const dateTest = date > now;
        if (dateTest) {
            publicationDateMessage.innerText = publicationDateValidationMessage;
        } else {
            publicationDateMessage.innerText = "";
        }
    });

    price.addEventListener('input', () => {
        const priceTest = priceRegExp.test(price.value);
        if (priceTest) {
            if (price.value > 0) {
                priceMessage.innerText = "";
            } else {
                priceMessage.innerText = priceValidationMessage;
            }
        } else {
            priceMessage.innerText = priceValidationMessage;
        }
    });

    count.addEventListener('input', () => {
        const countTest = countRegExp.test(count.value);
        if (countTest) {
            if (count.value > 0) {
                countMessage.innerText = "";
            } else {
                countMessage.innerText = countValidationMessage;
            }
        } else {
            countMessage.innerText = countValidationMessage;
        }
    });

    form.addEventListener("submit", (event) => {
        const titleTest = titleRegExp.test(title.value);
        const authorsTest = authorsRegExp.test(authors.value);
        const descriptionTest = descriptionRegExp.test(description.value);
        const languageTest = bookLanguageRegExp.test(bookLanguage.value);
        const editionTest = editionRegExp.test(edition.value);
        const now = new Date().withoutTime();
        const date = new Date(publicationDate.value).withoutTime();
        const dateTest = date > now;
        const priceTest = priceRegExp.test(price.value);
        const countTest = countRegExp.test(count.value);

        if (!titleTest || !authorsTest || !descriptionTest || !languageTest
            || !editionTest || dateTest || !priceTest || !countTest) {
            event.preventDefault();
            return false;
        }
        return true;
    });
</script>
</html>

