package ua.training.controller.command;

import ua.training.model.entity.Role;
import ua.training.model.entity.User;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.regex.Pattern;

public class Login implements Command {
    private final UserService userService;

    public Login(UserService userService) {
        this.userService = userService;
    }


    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login == null || login.equals("") || password == null || password.equals("")) {
            return "/login.jsp";
        }
        String loginPattern = "^(?!.*\\.\\.)(?!.*\\.$)[^\\W][\\w.]{4,20}$";
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,30}$";
        boolean isLoginValid = Pattern.matches(loginPattern, login);
        boolean isPasswordValid = Pattern.matches(passwordPattern, password);
        if (!isLoginValid || !isPasswordValid) {
            return "/login.jsp?validError=true";
        }
        Optional<User> optionalUser = userService.getUserByLogin(login);
        if (!optionalUser.isPresent()) {
            return "/login.jsp?loginError=true";
        }
        User user = optionalUser.get();
        if (!password.equals(user.getPassword_hash())) {
            return "/login.jsp?passwordError=true";
        }
        if (CommandUtility.checkUserIsLogged(request, login)) {
            return "/error/error.jsp";
        }
        if (user.getRole().equals(Role.READER)) {
            CommandUtility.setUserRole(request, Role.READER, login);
            return "redirect:/reader/home";
        } else if (user.getRole().equals(Role.LIBRARIAN)) {
            CommandUtility.setUserRole(request, Role.LIBRARIAN, login);
            return "redirect:/librarian/home";
        } else if (user.getRole().equals(Role.ADMIN)) {
            CommandUtility.setUserRole(request, Role.ADMIN, login);
            return "redirect:/admin/home";
        } else {
            return "/login.jsp?loginError=true";
        }
    }
}
