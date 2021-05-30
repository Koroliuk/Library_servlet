const form = document.getElementById('signupForm');
const login = document.getElementById('login');
const password = document.getElementById('password');
const loginMessage = document.getElementById("loginMessage");
const passwordMessage = document.getElementById("passwordMessage");

const loginRegExp = /^(?!.*\.\.)(?!.*\.$)[^\W][\w.]{4,20}$/;
const passwordRegExp = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,30}$/;

login.addEventListener("input", () => {
    const loginTest = loginRegExp.test(login.value);
    if (loginTest) {
        login.className = "valid";
        loginMessage.innerText = "";
        loginMessage.className = "error";
    } else {
        login.className = "invalid";
        const loginLength = login.value.length
        if (loginLength < 5 || loginLength > 20) {
            loginMessage.innerText = loginValidateMessage1;
        } else {
            loginMessage.innerText = loginValidateMessage2;
        }
        loginMessage.className = "error active";
    }
});

password.addEventListener("input", () => {
    const passwordTest = passwordRegExp.test(password.value);
    if (passwordTest) {
        password.className = "valid";
        passwordMessage.innerText = "";
        passwordMessage.className = "error";
    } else {
        password.className = "invalid";
        const passwordLength = password.value.length
        if (passwordLength < 8 || passwordLength > 30) {
            passwordMessage.innerText = passwordValidateMessage1;
        } else {
            passwordMessage.innerText = passwordValidateMessage2;
        }
        passwordMessage.className = "error active";
    }
});

form.addEventListener("submit", (event) => {
    const loginTest = loginRegExp.test(login.value);
    const passwordTest = loginRegExp.test(password.value);
    if (!loginTest) {
        login.className = "invalid";
        event.preventDefault();
        return false;
    }
    if (!passwordTest) {
        password.className = "invalid";
        event.preventDefault();
        return false;
    }
    login.className = "valid";
    password.className = "valid";
    loginMessage.className = "error";
    passwordMessage.className = "error";
    return true;
});
