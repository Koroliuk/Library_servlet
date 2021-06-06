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
        <form id="form" method="post" action="${pageContext.request.contextPath}/app/admin/${requestScope.action}Book?id=${param.id != null ? param.id : ""}">
            <table>
                <tr>
                    <td>
                        <label>
                            <input type="text" id="title" name="title" placeholder="Title" value="${requestScope.book.title != null ? requestScope.book.title : ""}">
                        </label>
                        <span id="titleMessage"></span>
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
                                        authorsString.append(author.getName()).append(",");
                                    }
                                    authorsString.deleteCharAt(authorsString.length() - 1);
                                    authorsString.deleteCharAt(authorsString.length() - 1);
                                    authors = authorsString.toString();
                                }
                                request.setAttribute("authorsString", authors);
                            %>
                            <input type="text" id="authors" name="authors" placeholder="Author" value="${requestScope.authorsString != null ? requestScope.authorsString : ""}">
                        </label>
                        <span id="authorsMessage"></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>
                            <textarea id="description" name="description" placeholder="Description" wrap="soft">${requestScope.book.description != null ? requestScope.book.description : ""}</textarea>
                        </label>
                        <span id="descriptionMessage"></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>
                            <input type="text" id="bookLanguage" name="bookLanguage" placeholder="Language" value="${requestScope.book.language != null ? requestScope.book.language : ""}">
                        </label>
                        <span id="bookLanguageMessage"></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>
                            <input type="text" id="edition" name="edition" placeholder="Edition" value="${requestScope.book.edition.name != null ? requestScope.book.edition.name : ""}">
                        </label>
                        <span id="editionMessage"></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>
                            <input type="date" id="publicationDate" name="publicationDate" placeholder="Date of publication" value="${requestScope.book.publicationDate != null ? requestScope.book.publicationDate : ""}">
                        </label>
                        <span id="publicationDateMessage"></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>
                            <input type="text" id="price" name="price" placeholder="Price" value="${requestScope.book.price != null ? requestScope.book.price : ""}">
                        </label>
                        <span id="priceMessage"></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>
                            <input type="text" id="count" name="count" placeholder="Count" value="${requestScope.book.count != null ? requestScope.book.count : ""}">
                        </label>
                        <span id="countMessage"></span>
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

