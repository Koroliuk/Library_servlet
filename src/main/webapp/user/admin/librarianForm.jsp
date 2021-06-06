<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html lang="${language}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Registration page for librarian</title>
</head>
<header>
    <div class="header_name">
        <p><fmt:message key="header.library.name"/></p>
    </div>
    <div class="header_buttons">
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
<h1>Створення бібліотекаря</h1>
<div>
    <form id="form" method="post" action="${pageContext.request.contextPath}/app/admin/addLibrarian">
        <table>
            <c:if test="${param.validError == true}">
                <span><fmt:message key="signup.message.check.input.data"/></span>
            </c:if>
            <c:if test="${param.loginError == true}">
                <span><fmt:message key="signup.message.login.in.use"/></span>
            </c:if>
            <c:if test="${param.successEvent == true}">
                <span><fmt:message key="signup.success.registration"/></span>
            </c:if>
            <tr>
                <td>
                    <label>
                        <input type="text" id="login" name="login" class="login"
                               placeholder="<fmt:message key="global.login"/>">
                    </label>
                    <span id="loginMessage"></span>
                </td>
            </tr>
            <tr>
                <td>
                    <label>
                        <input type="password" id="password" name="password"
                               placeholder="<fmt:message key="global.password"/>">
                    </label>
                    <span id="passwordMessage"></span>
                </td>
            </tr>
        </table>
        <div>
            <input type="submit" value="<fmt:message key="signup.button.name"/>">
        </div>
    </form>
    <div>
        <a href="${pageContext.request.contextPath}/app/admin/home"><fmt:message key="global.to.home.page"/></a>
    </div>
</div>
</body>
<script type="text/javascript">
    const loginValidateMessage1 = '<fmt:message key="signup.login.validation.message1"/>';
    const loginValidateMessage2 = '<fmt:message key="signup.login.validation.message2"/>';
    const passwordValidateMessage1 = '<fmt:message key="signup.password.validation.message1"/>';
    const passwordValidateMessage2 = '<fmt:message key="signup.password.validation.message2"/>';

    const form = document.getElementById('form');
    const login = document.getElementById('login');
    const password = document.getElementById('password');
    const loginMessage = document.getElementById("loginMessage");
    const passwordMessage = document.getElementById("passwordMessage");

    const loginRegExp = /^(?!.*\.\.)(?!.*\.$)[^\W][\w.]{4,20}$/;
    const passwordRegExp = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,30}$/;

    login.addEventListener("input", () => {
        const loginTest = loginRegExp.test(login.value);
        if (loginTest) {
            loginMessage.innerText = "";
        } else {
            const loginLength = login.value.length
            if (loginLength < 5 || loginLength > 20) {
                loginMessage.innerText = loginValidateMessage1;
            } else {
                loginMessage.innerText = loginValidateMessage2;
            }
        }
    });

    password.addEventListener("input", () => {
        const passwordTest = passwordRegExp.test(password.value);
        if (passwordTest) {
            passwordMessage.innerText = "";
        } else {
            const passwordLength = password.value.length
            if (passwordLength < 8 || passwordLength > 30) {
                passwordMessage.innerText = passwordValidateMessage1;
            } else {
                passwordMessage.innerText = passwordValidateMessage2;
            }
        }
    });

    form.addEventListener("submit", (event) => {
        const loginTest = loginRegExp.test(login.value);
        const passwordTest = loginRegExp.test(password.value);
        if (!loginTest) {
            event.preventDefault();
            return false;
        }
        if (!passwordTest) {
            event.preventDefault();
            return false;
        }
        return true;
    });
</script>
</html>

